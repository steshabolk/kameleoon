package com.project.kameleoon.controller;

import com.project.kameleoon.dto.QuoteDto;
import com.project.kameleoon.dto.ScoreDto;
import com.project.kameleoon.dto.VoteDto;
import com.project.kameleoon.dto.request.QuoteRequest;
import com.project.kameleoon.service.QuoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping
    public ResponseEntity<?> createQuote(@RequestParam(value = "userId", required = true) Long userId,
                                         @RequestBody @Valid QuoteRequest quoteRequest) {
        QuoteDto quote = quoteService.createQuote(userId, quoteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(quote);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuote(@PathVariable("id") Long id) {
        QuoteDto quote = quoteService.getQuote(id);
        return ResponseEntity.status(HttpStatus.OK).body(quote);
    }

    @GetMapping
    public ResponseEntity<?> getQuotesByUser(@RequestParam(value = "userId", required = true) Long userId) {
        List<QuoteDto> quotes = quoteService.getQuotesByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(quotes);
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomQuote() {
        QuoteDto quote = quoteService.getRandomQuote();
        return ResponseEntity.status(HttpStatus.OK).body(quote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editQuote(@PathVariable("id") Long id,
                                       @RequestBody @Valid QuoteRequest quoteRequest) {
        QuoteDto quote = quoteService.editQuote(id, quoteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(quote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuote(@PathVariable("id") Long id) {
        quoteService.deleteQuote(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<?> upvoteQuote(@PathVariable("id") Long id,
                                         @RequestParam(value = "userId", required = true) Long userId) {
        QuoteDto quote = quoteService.upvoteQuote(id, userId);
        return ResponseEntity.status(HttpStatus.OK).body(quote);
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<?> downvoteQuote(@PathVariable("id") Long id,
                                           @RequestParam(value = "userId", required = true) Long userId) {
        QuoteDto quote = quoteService.downvoteQuote(id, userId);
        return ResponseEntity.status(HttpStatus.OK).body(quote);
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopRatedQuotes() {
        List<QuoteDto> quotes = quoteService.getTopRatedQuotes();
        return ResponseEntity.status(HttpStatus.OK).body(quotes);
    }

    @GetMapping("/flop")
    public ResponseEntity<?> getFlopRatedQuotes() {
        List<QuoteDto> quotes = quoteService.getFlopRatedQuotes();
        return ResponseEntity.status(HttpStatus.OK).body(quotes);
    }

    @GetMapping("/{id}/scores")
    public ResponseEntity<?> getScoreHistory(@PathVariable("id") Long id) {
        List<ScoreDto> scores = quoteService.getScoreHistory(id);
        return ResponseEntity.status(HttpStatus.OK).body(scores);
    }

    @GetMapping("/votes")
    public ResponseEntity<?> getVotedQuotesByUser(@RequestParam(value = "userId", required = true) Long userId) {
        List<VoteDto> votes = quoteService.getVotedQuotesByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(votes);
    }
}
