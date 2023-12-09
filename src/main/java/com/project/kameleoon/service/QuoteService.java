package com.project.kameleoon.service;

import com.project.kameleoon.dao.QuoteDao;
import com.project.kameleoon.dao.VoteDao;
import com.project.kameleoon.dto.QuoteDto;
import com.project.kameleoon.dto.ScoreDto;
import com.project.kameleoon.dto.VoteDto;
import com.project.kameleoon.dto.request.QuoteRequest;
import com.project.kameleoon.entity.QuoteEntity;
import com.project.kameleoon.entity.ScoreEntity;
import com.project.kameleoon.entity.UserEntity;
import com.project.kameleoon.entity.VoteEntity;
import com.project.kameleoon.exception.ApiError;
import com.project.kameleoon.repository.QuoteRepository;
import com.project.kameleoon.repository.ScoreRepository;
import com.project.kameleoon.repository.UserRepository;
import com.project.kameleoon.repository.VoteRepository;
import com.project.kameleoon.util.DtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;
    private final VoteRepository voteRepository;
    private final QuoteDao quoteDao;
    private final VoteDao voteDao;
    private final DtoMapper dtoMapper;
    private final Random random = new Random();

    public QuoteService(QuoteRepository quoteRepository, UserRepository userRepository, ScoreRepository scoreRepository,
                        VoteRepository voteRepository, QuoteDao quoteDao, VoteDao voteDao, DtoMapper dtoMapper) {
        this.quoteRepository = quoteRepository;
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
        this.voteRepository = voteRepository;
        this.quoteDao = quoteDao;
        this.voteDao = voteDao;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    public QuoteDto createQuote(Long userId, QuoteRequest quoteRequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> ApiError.USER_NOT_FOUND.toException(userId));
        LocalDateTime time = LocalDateTime.now();
        QuoteEntity quote = quoteRepository.save(
                QuoteEntity.builder().user(user).quote(quoteRequest.getQuote()).createdAt(time).updatedAt(time).build());
        ScoreEntity score = scoreRepository.save(ScoreEntity.builder().quote(quote).score(0).updatedAt(time).build());
        log.debug("save new Quote{id={}, user={id={}}}", quote.getId(), quote.getUser().getId());
        return dtoMapper.convertToQuoteDto(quote, score.getScore());
    }

    @Transactional(readOnly = true)
    public QuoteDto getQuote(Long id) {
        QuoteEntity quote = quoteRepository.findWithScoresById(id)
                .orElseThrow(() -> ApiError.QUOTE_NOT_FOUND.toException(id));
        return dtoMapper.convertToQuoteDto(quote, quote.getScore());
    }

    @Transactional(readOnly = true)
    public List<QuoteDto> getQuotesByUser(Long userId) {
        List<QuoteEntity> quotes = quoteRepository.findAllWithScoresByUserId(userId);
        return quotes.stream().map(quote -> dtoMapper.convertToQuoteDto(quote, quote.getScore())).toList();
    }

    @Transactional(readOnly = true)
    public QuoteDto getRandomQuote() {
        return quoteDao.getRandomQuote();
    }

    @Transactional
    public QuoteDto editQuote(Long id, QuoteRequest quoteRequest) {
        QuoteEntity quote = quoteRepository.findWithScoresById(id)
                .orElseThrow(() -> ApiError.QUOTE_NOT_FOUND.toException(id));
        quote.setQuote(quoteRequest.getQuote());
        quote.setUpdatedAt(LocalDateTime.now());
        QuoteEntity saved = quoteRepository.save(quote);
        log.debug("edit Quote{id={}}", saved.getId());
        return dtoMapper.convertToQuoteDto(saved, saved.getScore());
    }

    @Transactional
    public void deleteQuote(Long id) {
        quoteRepository.deleteById(id);
        log.debug("delete Quote{id={}}", id);
    }

    @Transactional
    public QuoteDto upvoteQuote(Long id, Long userId) {
        return vote(id, 1, userId);
    }

    @Transactional
    public QuoteDto downvoteQuote(Long id, Long userId) {
        return vote(id, -1, userId);
    }

    @Transactional(readOnly = true)
    public List<QuoteDto> getTopRatedQuotes() {
        return quoteDao.getTop10Quotes();
    }

    @Transactional(readOnly = true)
    public List<QuoteDto> getFlopRatedQuotes() {
        return quoteDao.getFlop10Quotes();
    }

    public List<ScoreDto> getScoreHistory(Long id) {
        QuoteEntity quote = quoteRepository.findWithScoresById(id)
                .orElseThrow(() -> ApiError.QUOTE_NOT_FOUND.toException(id));
        List<ScoreEntity> scores = quote.getScores();
        scores.sort(Comparator.comparing(ScoreEntity::getUpdatedAt));
        return scores.subList(Math.max(0, scores.size() - 50), scores.size())
                .stream().map(dtoMapper::convertToScoreDto).toList();
    }

    public List<VoteDto> getVotedQuotesByUser(Long userId) {
        return voteDao.getVotesByUserId(userId);
    }

    private QuoteDto vote(Long id, int vote, Long userId) {
        QuoteEntity quote = quoteRepository.findWithScoresById(id)
                .orElseThrow(() -> ApiError.QUOTE_NOT_FOUND.toException(id));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> ApiError.USER_NOT_FOUND.toException(userId));
        Integer currentScore = quote.getScore() + vote;
        LocalDateTime time = LocalDateTime.now();
        ScoreEntity score = scoreRepository.save(
                ScoreEntity.builder().quote(quote).score(currentScore).updatedAt(time).build());
        voteRepository.save(VoteEntity.builder().quote(quote).user(user).createdAt(time).vote(vote).build());
        log.debug("User{id={}} voted {} for the Quote{id={}}", user.getId(), vote, quote.getId());
        return dtoMapper.convertToQuoteDto(quote, score.getScore());
    }
}
