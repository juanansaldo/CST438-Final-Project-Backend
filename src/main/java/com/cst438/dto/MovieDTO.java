package com.cst438.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieDTO(
        int id,
        String original_title,
        String overview,
        LocalDate release_date,
        double vote_average,
        List<Integer> genre_ids ,
        String fullPosterPath  // Add static field for full poster path
) {
    public static String generateFullPosterPath(String posterPath) {
        if (posterPath != null) {
            return "https://image.tmdb.org/t/p/w200" + posterPath;
        } else {
            return null;
        }
    }
    // Explicit getters
    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public LocalDate getReleaseDate() {
        return release_date;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public List<Integer> getGenreIds() {
        return genre_ids;
    }

    public String getFullPosterPath() {
        return fullPosterPath;
    }
    public static MovieDTO getMovieDetailsById(String movieId) {
        // Simulate the behavior using Mockito (you may need to adjust this based on your actual setup)
        RestTemplate restTemplate = new RestTemplate();
        String tmdbApiUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=YOUR_API_KEY";
        
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                tmdbApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseMap = responseEntity.getBody();

        // Extract movie details from the responseMap and create a MovieDTO
        if (responseMap != null && responseMap.containsKey("id")) {
            return new MovieDTO(
                    (int) responseMap.get("id"),
                    (String) responseMap.get("original_title"),
                    (String) responseMap.get("overview"),
                    LocalDate.parse((String) responseMap.get("release_date")),
                    (double) responseMap.get("vote_average"),
                    (List<Integer>) responseMap.get("genre_ids"),
                    MovieDTO.generateFullPosterPath((String) responseMap.get("poster_path"))
            );
        } else {
            // Handle the case when the API response is not as expected
            return null;
        }
    }


  
}
