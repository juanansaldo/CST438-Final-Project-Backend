//package com.cst438.domain;
//
//import java.time.LocalDate;
//import javax.persistence.*;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity
//@Table(name="movies")
//public class Movie {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int movieId;
//
//    private String title;
//    private String genre;
//    private LocalDate releaseDate;
//    private String description;
//
//    private String rated;
//    private String poster;
//    @JsonIgnore
//    private boolean adult;
//
//
//    public Movie() {
//    	super();
//    }
//    
//    public Movie(int movieId, String title, String genre, LocalDate releaseDate, String description, String rated,
//			String poster) {
//		super();
//		this.movieId = movieId;
//		this.title = title;
//		this.genre = genre;
//		this.releaseDate = releaseDate;
//		this.description = description;
//		this.rated = rated;
//		this.poster = poster;
//	}
//
//
//    public int getMovieId() {
//        return movieId;
//    }
//
//    public void setMovieId(int movieId) {
//        this.movieId = movieId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }
//
//    public LocalDate getReleaseDate() {
//        return releaseDate;
//    }
//
//    public void setReleaseDate(LocalDate releaseDate) {
//        this.releaseDate = releaseDate;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getRated() {
//        return rated;
//    }
//
//    public void setRated(String rated) {
//        this.rated = rated;
//    }
//
//    public String getPoster() {
//        return poster;
//    }
//
//    public void setPoster(String poster) {
//        this.poster = poster;
//    }
//
//    @Override
//    public String toString() {
//        return "Movie [movieId=" + movieId + ", title=" + title + ", genre=" + genre + ", releaseDate=" + releaseDate +
//                ", description=" + description + ", rated=" + rated + ", poster=" + poster + "]";
//    }
//}
