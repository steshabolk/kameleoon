package com.project.kameleoon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuoteDto {

    private Long id;
    private UserSimpleDto user;
    private String quote;
    private Integer score;
}
