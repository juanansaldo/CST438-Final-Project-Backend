package com.cst438.controller;

import com.cst438.domain.User;
import com.cst438.domain.UserRepository;
import com.cst438.domain.UsersMoviesToWatch;
import com.cst438.domain.UsersMoviesToWatchRepository;
import com.cst438.dto.MovieDTO;
import com.cst438.dto.UsersMoviesToWatchDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UsersMoviesToWatchController {

    @Autowired
    private UsersMoviesToWatchRepository usersMoviesToWatchRepository;
    @Autowired
    private UserRepository userRepository;
    private final String TMDB_API_KEY = "5d609659f5ed80bc910016225703fbcd";
    private final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private final String TMDB_API_MOVIE_ENDPOINT = "/movie";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Autowired
    public UsersMoviesToWatchController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    @PostMapping("/addToWatch")
    public int addToWatchList(Principal p, @RequestBody UsersMoviesToWatchDTO movieDTO) {
    	
    	if (movieDTO.getMovieTitle() == null || movieDTO.getPosterPath() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are null values.");
        }
    	
        // Get the username from Principal
        String username = p.getName();

        // Retrieve the user from the repository using the username
        User user = userRepository.findByUsername(username);

        // Get the user ID
        int userId = user.getId();
        
        List<UsersMoviesToWatch> userMovies =  usersMoviesToWatchRepository.findByUserId(userId);
        
        for (UsersMoviesToWatch existingMovie : userMovies) {
            if (existingMovie.getPosterPath().equals(movieDTO.getPosterPath())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This movie has already been added to your watch list.");
            }
        }
        
        // Check if the user has already added 10 movies
        if (userMovies.size() >= 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only add up to 10 movies.");
        }

        // Create and save the UsersMoviesToWatch entity. Return generated id to the client.
        UsersMoviesToWatch toWatch = new UsersMoviesToWatch();
        toWatch.setUserId(userId);
        toWatch.setMovieTitle(movieDTO.movieTitle());
        toWatch.setMovieOverview(movieDTO.movieOverview());
        toWatch.setReleaseDate(movieDTO.releaseDate());
        toWatch.setPosterPath(movieDTO.posterPath());
        
        System.out.println("title: " + movieDTO.getMovieTitle()
        		+ "\noverview: " + movieDTO.getMovieOverview() 
        		+ "\ndate: " + movieDTO.getReleaseDate()
        		+ "\nposter: " + movieDTO.getPosterPath());
       

        // Save the UsersMoviesToWatch item to the database
        usersMoviesToWatchRepository.save(toWatch);

        return toWatch.getUserId();
    }
    
    @GetMapping("/userMoviesToWatch")
    public List<UsersMoviesToWatchDTO> getUserMoviesToWatch(Principal p) {
        // Get the username from Principal
        String username = p.getName();

        // Retrieve the user from the repository using the username
        User user = userRepository.findByUsername(username);

        // Get the user ID
        int userId = user.getId();

        // Retrieve the user's movies from the repository
        List<UsersMoviesToWatch> userMovies = usersMoviesToWatchRepository.findByUserId(userId);

        // Convert entity objects to DTOs if needed
        List<UsersMoviesToWatchDTO> userMoviesDTO = userMovies.stream()
                .map(m -> new UsersMoviesToWatchDTO(
                        m.getMovieTitle(),
                        m.getMovieOverview(),
                        m.getReleaseDate(),
                        m.getPosterPath()
                ))
                .collect(Collectors.toList());

        return userMoviesDTO;
    }
    
    @DeleteMapping("/deleteFromWatch/{movieTitle}")
    public void deleteMovieFromWatchList(@PathVariable("movieTitle") String movieTitle, Principal principal) {
        // Get the username from Principal
        String username = principal.getName();

        // Retrieve the user from the repository using the username
        User user = userRepository.findByUsername(username);

        // Get the user ID
        int userId = user.getId();

        // Find the movie in the user's watch list
        UsersMoviesToWatch movieToDelete = usersMoviesToWatchRepository.findByUserIdAndMovieTitle(userId, movieTitle);

        if (movieToDelete != null) {
            // Delete the movie from the watch list
            usersMoviesToWatchRepository.delete(movieToDelete);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The movie was not found in the watch list.");
        }
    }
    @PostMapping("/addToWatchList/{movieId}")
    public int addToWatchList(Principal p, @PathVariable("movieId") String movieId) {
        // Get the username from Principal
        String username = p.getName();

        // Retrieve the user from the repository using the username
        User user = userRepository.findByUsername(username);

        // Get the user ID
        int userId = user.getId();

        // Check if the movie is already in the user's watch list
        List<UsersMoviesToWatch> userMovies = usersMoviesToWatchRepository.findByUserId(userId);
        for (UsersMoviesToWatch existingMovie : userMovies) {
            if (existingMovie.getMovieTitle().equals(movieId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This movie is already in your watch list.");
            }
        }

        // Check if the user has already added 10 movies
        if (userMovies.size() >= 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only add up to 10 movies.");
        }

        // Fetch detailed information about the movie from the Movie Database API
        MovieDTO movieDTO = MovieDTO.getMovieDetailsById(movieId);

        // Create and save the UsersMoviesToWatch entity. Return generated id to the client.
        UsersMoviesToWatch toWatch = new UsersMoviesToWatch();
        toWatch.setUserId(userId);
        toWatch.setMovieTitle(movieDTO.getOriginalTitle());
        toWatch.setMovieOverview(movieDTO.getOverview());
        toWatch.setReleaseDate(movieDTO.getReleaseDate());
        toWatch.setPosterPath(movieDTO.getFullPosterPath());

        // Save the UsersMoviesToWatch item to the database
        usersMoviesToWatchRepository.save(toWatch);

        return toWatch.getUserId();
    }
    private MovieDTO getMovieDetailsById(String movieId) {
        // Fetch movie details from the Movie Database API using the provided movie ID
        String tmdbApiUrl = TMDB_API_BASE_URL + TMDB_API_MOVIE_ENDPOINT + "/" + movieId +
                "?api_key=" + TMDB_API_KEY;

        ResponseEntity<MovieDTO> responseEntity = restTemplate.exchange(
                tmdbApiUrl,
                HttpMethod.GET,
                null,
                MovieDTO.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch movie details from TMDb API");
        }
    }

}
