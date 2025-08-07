package com.movies.movies_api.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;
    private Integer releaseYear;
    private String category;
    private String nominee;

    private Boolean won;

    private String runtime;
    private String genre;
    private String director;

    private BigDecimal boxOffice;

    private Double imdbRating;

}