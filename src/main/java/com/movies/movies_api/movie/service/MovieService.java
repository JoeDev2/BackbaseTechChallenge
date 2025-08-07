package com.movies.movies_api.movie.service;

import com.movies.movies_api.exception.MovieNotFoundException;
import com.movies.movies_api.movie.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getTop10BoxOfficeMovies();

    boolean hasMovieWonBestPicture(Long id);

    void giveUserRating(Long id, Double rating) throws MovieNotFoundException;

}