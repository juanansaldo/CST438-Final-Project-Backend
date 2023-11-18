//package com.cst438.domain;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface MovieRepository extends CrudRepository<Movie, Long> {
//
//    @Query("SELECT m FROM Movie m WHERE m.movieId = :movieId")
//    Movie findByMovieId(@Param("movieId") Long movieId);
//    
//    @Query("SELECT m FROM Movie m WHERE m.title = :title")
//    List<Movie> findByTitle(@Param("title") String title);
//
//    @Query("SELECT m FROM Movie m WHERE m.genre = :genre")
//    List<Movie> findByGenre(@Param("genre") String genre);
//
//    @Query("SELECT m FROM Movie m")
//    List<Movie> findAll();
//}
