package com.movies.movies_api.movie.service;


import com.movies.movies_api.exception.MovieNotFoundException;
import com.movies.movies_api.exception.OmdbApiException;
import com.movies.movies_api.movie.dto.MovieApiDto;

public interface MovieApiService {

    MovieApiDto makeGetRequest(String url) throws MovieNotFoundException, OmdbApiException;

    MovieApiDto getMovieInfoByTitle(String title) throws MovieNotFoundException, OmdbApiException;

}