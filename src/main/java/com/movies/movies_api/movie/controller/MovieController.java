package com.movies.movies_api.movie.controller;

import com.movies.movies_api.exception.MovieNotFoundException;
import com.movies.movies_api.movie.dto.MovieDto;
import com.movies.movies_api.movie.model.Movie;
import com.movies.movies_api.movie.service.MovieServiceImpl;
import com.movies.movies_api.userrating.UserRatingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
@Validated
public class MovieController {

    private final MovieServiceImpl movieService;

    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}/won-best-picture")
    public ResponseEntity<Boolean> hasMovieWonBestPicture(@PathVariable @Min(1) Long id) {
        try {

            return ResponseEntity.ok(movieService.hasMovieWonBestPicture(id));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/top-10/box-office")
    public ResponseEntity<List<MovieDto>> getTop10BoxOfficeMovies() {
        try {
            List<Movie> top10BoxOfficeMovies = movieService.getTop10BoxOfficeMovies();
            List<MovieDto> top10BoxOfficeMovieDtos = convertToDtoList(top10BoxOfficeMovies);

            return ResponseEntity.ok(top10BoxOfficeMovieDtos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{id}/rating")
    public ResponseEntity<Void> giveUserRating(@PathVariable @Min(1) Long id, @RequestBody @Valid UserRatingDto userRatingDto) {
        try {
            movieService.giveUserRating(id, userRatingDto.getRating());
        } catch (MovieNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }

    private List<MovieDto> convertToDtoList(List<Movie> top10BoxOfficeMovies) {
        List<MovieDto> movieDtos = new ArrayList<>();

        for (Movie movie : top10BoxOfficeMovies) {
            movieDtos.add(convertToDto(movie));
        }

        return movieDtos;
    }

    private MovieDto convertToDto(Movie movie) {
        MovieDto movieDto = new MovieDto();

        movieDto.setId(movie.getId());
        movieDto.setCategory(movie.getCategory());
        movieDto.setGenre(movie.getGenre());
        movieDto.setDirector(movie.getDirector());
        movieDto.setNominee(movie.getNominee());
        movieDto.setBoxOffice(movie.getBoxOffice());
        movieDto.setImdbRating(movie.getImdbRating());
        movieDto.setWon(movie.getWon());
        movieDto.setRuntime(movie.getRuntime());
        movieDto.setReleaseYear(movie.getReleaseYear());

        return movieDto;
    }
}