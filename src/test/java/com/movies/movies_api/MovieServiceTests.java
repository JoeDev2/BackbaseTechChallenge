package com.movies.movies_api;

import com.movies.movies_api.exception.MovieNotFoundException;
import com.movies.movies_api.movie.model.Movie;
import com.movies.movies_api.movie.repository.MovieRepository;
import com.movies.movies_api.movie.service.MovieServiceImpl;
import com.movies.movies_api.userrating.UserRating;
import com.movies.movies_api.userrating.UserRatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTests {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRatingRepository userRatingRepository;

    @Test
    void testGetTop10BoxOfficeMovies() {
        Long MOVIE_ID1 = 1L;
        Double RATING1 = 1.0;
        BigDecimal boxOffice1 = new BigDecimal("1000");
        Movie mock1 = new Movie(1L, "Titanic", 2000, boxOffice1);
        UserRating userRating1 = new UserRating(1L, mock1, RATING1);

        Long MOVIE_ID2 = 2L;
        Double RATING2 = 2.0;
        BigDecimal boxOffice2 = new BigDecimal("2000");
        Movie mock2 = new Movie(MOVIE_ID2, "Avatar", 2000, boxOffice2);
        UserRating userRating2 = new UserRating(2L, mock2, RATING2);


        List<UserRating> userRatings = new ArrayList<>();
        userRatings.add(userRating1);
        userRatings.add(userRating2);

        when(userRatingRepository.findTop10OrderByRatingDesc(any())).thenReturn(userRatings);

        List<Movie> movies = movieService.getTop10BoxOfficeMovies();

        assertFalse(movies.isEmpty());
        assertEquals(movies.get(0), mock2);
        assertEquals(movies.get(1), mock1);
    }

    @Test
    void testHasMovieWonBestPicture() {
        Long MOVIE_ID = 1L;
        Double RATING = 1.0;
        String MOVIE_NAME = "Titanic";
        BigDecimal boxOffice = new BigDecimal("1000");

        Movie mock = new Movie(MOVIE_ID, MOVIE_NAME, 2000, boxOffice);

        mock.setWon(true);

        when(movieRepository.findById(MOVIE_ID)).thenReturn(Optional.of(mock));

        boolean hasWonBestPicture = movieService.hasMovieWonBestPicture(MOVIE_ID);

        assert(hasWonBestPicture);

    }

    @Test
    void testGiveUserRating() throws MovieNotFoundException {
        Long MOVIE_ID = 1L;
        Double RATING = 1.0;
        String MOVIE_NAME = "Titanic";
        BigDecimal boxOffice = new BigDecimal("1000");

        Movie mock = new Movie(MOVIE_ID, MOVIE_NAME, 2000, boxOffice);

        when(movieRepository.findById(MOVIE_ID)).thenReturn(Optional.of(mock));

        UserRating mockUserRating = new UserRating();

        when(userRatingRepository.findByMovieId(MOVIE_ID)).thenReturn(Optional.of(mockUserRating));

        movieService.giveUserRating(MOVIE_ID, RATING);

    }
}