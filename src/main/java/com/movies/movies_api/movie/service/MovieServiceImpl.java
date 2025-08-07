package com.movies.movies_api.movie.service;

import com.movies.movies_api.exception.MovieNotFoundException;
import com.movies.movies_api.movie.repository.MovieRepository;
import com.movies.movies_api.movie.model.Movie;
import com.movies.movies_api.userrating.UserRating;
import com.movies.movies_api.userrating.UserRatingRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final UserRatingRepository userRatingRepository;

    public MovieServiceImpl(MovieRepository movieRepository, UserRatingRepository userRatingRepository) {
        this.movieRepository = movieRepository;
        this.userRatingRepository = userRatingRepository;
    }

    /**
     * @return
     */
    @Override
    public List<Movie> getTop10BoxOfficeMovies() {
        Pageable top10Page = PageRequest.of(0, 10);
        List<UserRating> userRatings = userRatingRepository.findTop10OrderByRatingDesc(top10Page);
        List<Movie> movies = new ArrayList<>();
        for (UserRating rating : userRatings) {
            if (rating != null) {
                movies.add(rating.getMovie());
            }
        }

        movies.sort(Comparator.comparing(Movie::getBoxOffice, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        return movies;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean hasMovieWonBestPicture(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);

        return movie.isPresent() && movie.get().getWon();
    }


    /**
     * @param id
     * @param rating
     */
    @Override
    public void giveUserRating(Long id, Double rating) throws MovieNotFoundException {
        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            Optional<UserRating> existingUserRating = userRatingRepository.findByMovieId(movie.get().getId());

            if (existingUserRating.isPresent()) {
                existingUserRating.get().setRating(rating);
                userRatingRepository.save(existingUserRating.get());

            } else {

                UserRating userRating = new UserRating(movie.get(), rating);
                userRatingRepository.save(userRating);
            }
        } else {
            throw new MovieNotFoundException();
        }
    }

}