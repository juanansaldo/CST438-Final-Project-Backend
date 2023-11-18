package com.cst438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class UsersMoviesToWatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entryId;

    private int userId;
    private String movieTitle;
    private String movieOverview;
    private LocalDate releaseDate;
    private String posterPath;
    
	public UsersMoviesToWatch() {
		super();
	}
	public UsersMoviesToWatch(int entryId, int userId, String movieTitle, String movieOverview, LocalDate releaseDate,
			String posterPath) {
		super();
		this.entryId = entryId;
		this.userId = userId;
		this.movieTitle = movieTitle;
		this.movieOverview = movieOverview;
		this.releaseDate = releaseDate;
		this.posterPath = posterPath;
	}
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public String getMovieOverview() {
		return movieOverview;
	}
	public void setMovieOverview(String movieOverview) {
		this.movieOverview = movieOverview;
	}
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getPosterPath() {
		return posterPath;
	}
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
}