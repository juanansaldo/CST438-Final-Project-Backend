package com.cst438.dto;

import java.time.LocalDate;
import java.util.List;

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
}
