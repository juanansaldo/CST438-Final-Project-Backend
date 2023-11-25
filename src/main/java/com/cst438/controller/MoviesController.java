package com.cst438.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.UsersMoviesToWatch;
import com.cst438.domain.UsersMoviesToWatchRepository;
import com.cst438.domain.User;
import com.cst438.dto.MovieDTO;
import com.fasterxml.jackson.databind.type.TypeFactory;

@RestController
@CrossOrigin
public class MoviesController {

    private final String TMDB_API_KEY = "5d609659f5ed80bc910016225703fbcd";
    private final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private final String TMDB_API_MOVIE_ENDPOINT = "/movie";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public MoviesController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/movies")
    public MovieDTO[] getAllMovies(@RequestParam(defaultValue = "1") int page) {
        String tmdbApiUrl = TMDB_API_BASE_URL + TMDB_API_MOVIE_ENDPOINT +
                "/popular?api_key=" + TMDB_API_KEY + "&page=" + page;

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                tmdbApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseMap = responseEntity.getBody();

        if (responseMap != null && responseMap.containsKey("results")) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) responseMap.get("results");
            MovieDTO[] movies = new MovieDTO[results.size()];

            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> result = results.get(i);
                MovieDTO movieDTO = new MovieDTO(
                        (int) result.get("id"),
                        (String) result.get("original_title"),
                        (String) result.get("overview"),
                        LocalDate.parse((String) result.get("release_date")),
                        (double) result.get("vote_average"),
                        (List<Integer>) result.get("genre_ids"),
                        MovieDTO.generateFullPosterPath((String) result.get("poster_path"))
                );
                movies[i] = movieDTO;
            }

            return movies;
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected response format from TMDb API");
        }
    }
    @GetMapping("/moviesSearched")
    public MovieDTO[] getMoviesByKeyword(@RequestParam String keyword, @RequestParam(defaultValue = "1") int page) {
        String tmdbApiUrl = TMDB_API_BASE_URL + "/search/movie" +
                "?api_key=" + TMDB_API_KEY +
                "&query=" + keyword +
                "&page=" + page;

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                tmdbApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseMap = responseEntity.getBody();

        if (responseMap != null && responseMap.containsKey("results")) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) responseMap.get("results");
            MovieDTO[] movies = new MovieDTO[results.size()];

            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> result = results.get(i);

                // Add these lines to handle release_date
                String releaseDateStr = (String) result.get("release_date");
                LocalDate releaseDate = null;

                if (releaseDateStr != null && !releaseDateStr.isEmpty()) {
                    releaseDate = LocalDate.parse(releaseDateStr);
                }

                MovieDTO movieDTO = new MovieDTO(
                        (int) result.get("id"),
                        (String) result.get("title"),
                        (String) result.get("overview"),
                        releaseDate, // Use the parsed releaseDate or leave it as null if parsing failed
                        (double) result.get("vote_average"),
                        (List<Integer>) result.get("genre_ids"),
                        MovieDTO.generateFullPosterPath((String) result.get("poster_path"))
                );
                movies[i] = movieDTO;
            }

            return movies;
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected response format from TMDb API");
        }
    }
    
}

