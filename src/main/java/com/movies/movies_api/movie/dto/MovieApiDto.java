package com.movies.movies_api.movie.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieApiDto {

    private String runtime;

    private String genre;

    private String director;

    private String boxOffice;

    private String imdbRating;

    private String response;
}