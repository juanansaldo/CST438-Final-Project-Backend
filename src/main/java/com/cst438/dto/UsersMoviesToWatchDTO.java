package com.cst438.dto;

import java.time.LocalDate;

public record UsersMoviesToWatchDTO(
        String movieTitle,
        String movieOverview,
        LocalDate releaseDate,
        String posterPath
) {

    // Getter for movieTitle
    public String getMovieTitle() {
        return movieTitle;
    }

    // Getter for movieOverview
    public String getMovieOverview() {
        return movieOverview;
    }

    // Getter for releaseDate
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    // Getter for posterPath
    public String getPosterPath() {
        return posterPath;
    }
}
