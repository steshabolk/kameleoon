package com.project.kameleoon.util;

import com.project.kameleoon.dto.QuoteDto;
import com.project.kameleoon.dto.ScoreDto;
import com.project.kameleoon.dto.UserSimpleDto;
import com.project.kameleoon.dto.request.RegisterRequest;
import com.project.kameleoon.entity.QuoteEntity;
import com.project.kameleoon.entity.ScoreEntity;
import com.project.kameleoon.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DtoMapper {

    private final ModelMapper modelMapper;

    public UserEntity convertToUser(RegisterRequest registerRequest) {
        return modelMapper.map(registerRequest, UserEntity.class);
    }

    public UserSimpleDto convertToUserSimpleDto(UserEntity user) {
        return modelMapper.map(user, UserSimpleDto.class);
    }

    public QuoteDto convertToQuoteDto(QuoteEntity quote, Integer score) {
        return QuoteDto.builder()
                .id(quote.getId())
                .user(new UserSimpleDto(quote.getUser().getId(), quote.getUser().getName()))
                .quote(quote.getQuote())
                .score(score)
                .build();
    }

    public ScoreDto convertToScoreDto(ScoreEntity score) {
        return modelMapper.map(score, ScoreDto.class);
    }
}
