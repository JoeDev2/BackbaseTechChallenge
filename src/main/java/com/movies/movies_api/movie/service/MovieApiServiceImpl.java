package com.movies.movies_api.movie.service;

import com.movies.movies_api.exception.MovieNotFoundException;
import com.movies.movies_api.exception.OmdbApiException;
import com.movies.movies_api.movie.dto.MovieApiDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieApiServiceImpl implements MovieApiService {

    private final static String MOVIES_URL = "http://www.omdbapi.com/";
    private final static String TITLE_PARAM = "?t=";
    private final static String API_KEY_PARAM = "&apikey=";

    //@Value
    private final static String API_KEY = "868092f1";

    private final RestTemplate movieApiTemplate;

    public MovieApiServiceImpl(RestTemplate movieApiTemplate) {
        this.movieApiTemplate = movieApiTemplate;
    }

    @Override
    public MovieApiDto makeGetRequest(String url) throws MovieNotFoundException, OmdbApiException {
        try {
            MovieApiDto apiResponse = movieApiTemplate.getForObject(url, MovieApiDto.class, "");

            if (apiResponse == null) {
                throw new MovieNotFoundException();
            }

            return apiResponse;
        } catch (MovieNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new OmdbApiException();
        }
    }

    public MovieApiDto getMovieInfoByTitle(String title) throws MovieNotFoundException, OmdbApiException {
        if (title == null) {
            throw new IllegalArgumentException("Title is null, can't search for movie by title when title is null.");
        }
        return makeGetRequest(MOVIES_URL + TITLE_PARAM +  title + API_KEY_PARAM + API_KEY);
    }

}