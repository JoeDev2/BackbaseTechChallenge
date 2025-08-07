package com.movies.movies_api.movie;

import com.movies.movies_api.exception.MovieNotFoundException;
import com.movies.movies_api.exception.OmdbApiException;
import com.movies.movies_api.movie.dto.MovieApiDto;
import com.movies.movies_api.movie.model.Movie;
import com.movies.movies_api.movie.repository.MovieRepository;
import com.movies.movies_api.movie.service.MovieApiServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.math.BigDecimal;
import java.util.List;


@Component
public class MovieInitialiser implements ApplicationRunner {

    private final MovieRepository movieRepository;
    private final MovieApiServiceImpl movieApiService;

    @Value("${app.academy-awards-path}")
    private Resource resourceFile;

    public MovieInitialiser(MovieRepository movieRepository, MovieApiServiceImpl movieApiService) {
         this.movieRepository = movieRepository;
         this.movieApiService = movieApiService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (movieRepository.count() <= 0) {
            initialiseMoviesData();
        }
    }

    private void initialiseMoviesData() throws IOException, CsvException {
        getMovieDataFromCsv();
    }

    private void enrichWithAPIData(Movie movie) throws MovieNotFoundException, OmdbApiException {
        MovieApiDto movieApiDto = movieApiService.getMovieInfoByTitle(movie.getTitle());

        updateMovieWithData(movie, movieApiDto);

        Movie savedMovie = movieRepository.save(movie);
    }

    private BigDecimal parseBoxOfficeForMovie(MovieApiDto movieApiDto) {

        if (movieApiDto.getBoxOffice() != null && !movieApiDto.getBoxOffice().isEmpty()) {
            return new BigDecimal(movieApiDto.getBoxOffice());
        } else {
            return new BigDecimal(0);
        }
    }

    private void updateMovieWithData(Movie movie, MovieApiDto movieApiDto) {
        movie.setRuntime(movieApiDto.getRuntime());
        movie.setGenre(movieApiDto.getGenre());
        movie.setDirector(movieApiDto.getDirector());
        movie.setBoxOffice(parseBoxOfficeForMovie(movieApiDto));
        movie.setImdbRating(parseImdbForMovie(movieApiDto));

    }

    private Double parseImdbForMovie(MovieApiDto movieApiDto) {

        if (movieApiDto.getImdbRating() != null && !movieApiDto.getImdbRating().isEmpty() && !movieApiDto.getImdbRating().equals("N/A")) {
            return Double.parseDouble(movieApiDto.getImdbRating());
        } else {
            return 0.0;
        }
    }

    private void getMovieDataFromCsv() throws IOException, CsvException {

        Reader reader = new InputStreamReader(resourceFile.getInputStream());

        try (CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .build()) {


            List<String[]> rows = csvReader.readAll();

            for (String[] row : rows) {
                if (row[1].equals("Best Picture")) {
                    try {
                        Movie movie = parseMovieFromRow(row);
                        Movie savedMovie = movieRepository.save(movie);

                        try {
                            enrichWithAPIData(savedMovie);

                        }  catch (OmdbApiException e) {
                            System.out.println("Exception during api request");

                        } catch (MovieNotFoundException e) {
                            System.out.println("Movie not found for title");

                        } catch (Exception e) {
                            System.out.println("Enriching with API data failed");
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Processing row failed");
                    }

                }
            }


        }
    }

    private Movie parseMovieFromRow(String[] row) {
        String releaseYearString = row[0].substring(0, Math.min(4, row[0].length()));
        Integer releaseYear = Integer.parseInt(releaseYearString);

        String category = row[1];
        String nominee = row[2];

        Boolean won = row[4].equalsIgnoreCase("YES");

        Movie movie = new Movie(releaseYear, category, nominee, won);
        movie.setTitle(nominee);

        return movie;
    }

}