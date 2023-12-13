package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.transaction.annotation.Transactional;

import com.cst438.dto.MovieDTO;
import com.cst438.dto.UsersMoviesToWatchDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JunitTestMovies {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getMovies() throws Exception {
        MockHttpServletResponse response = mvc.perform(
        		MockMvcRequestBuilders
        				.get("/movies/")
        				.contentType(MediaType.APPLICATION_JSON)
        				.accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the get request was successful
        assertEquals(200, response.getStatus());
        
        // Create a list of ActorDTOs
        String content = response.getContentAsString();
        
        List<MovieDTO> MovieList = objectMapper.readValue(
            content, new TypeReference<List<MovieDTO>>() {}
        );

        // Verify that the actors DTO list is not null
        assertNotNull(MovieList, "Movies list is null.");
        // Verify that the actors DTO list is not empty
        assertTrue(!MovieList.isEmpty(), "Movies list is empty.");
    }	

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getMovieByID() throws Exception {
    	MockHttpServletResponse response = mvc.perform(
        		MockMvcRequestBuilders
        				.get("/movies/872585")
        				.contentType(MediaType.APPLICATION_JSON)
        				.accept(MediaType.APPLICATION_JSON))
    			  	  .andReturn().getResponse();

    	// Verify that the get request was successful
    	assertEquals(200, response.getStatus());
    	
        // Create an ActorDTO
    	MovieDTO movie = objectMapper.readValue(response.getContentAsString(),
            MovieDTO.class
        );

        // Verify that the actor DTO is not null
    	assertNotNull(movie, "Movie is null.");
    }
    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void addToWatchList() throws Exception {
        UsersMoviesToWatchDTO movieDTO = new UsersMoviesToWatchDTO("Test Movie", "This is a test movie",
                LocalDate.parse("2023-01-01"), "test-poster.jpg");

        MockHttpServletResponse response = mvc
                .perform(MockMvcRequestBuilders.post("/addToWatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDTO)).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that the post request was successful
        assertEquals(200, response.getStatus());

        // Verify the response content
        int userId = objectMapper.readValue(response.getContentAsString(), Integer.class);
        assertTrue(userId > 0, "Invalid user ID returned.");
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void getUserMoviesToWatch() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/userMoviesToWatch")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Verify that the get request was successful
        assertEquals(200, response.getStatus());

        // Create a list of UsersMoviesToWatchDTOs
        String content = response.getContentAsString();

        List<UsersMoviesToWatchDTO> moviesList = objectMapper.readValue(content,
                new TypeReference<List<UsersMoviesToWatchDTO>>() {
                });

        // Verify that the movies DTO list is not null
        assertNotNull(moviesList, "Movies list is null.");
    }


    

}
