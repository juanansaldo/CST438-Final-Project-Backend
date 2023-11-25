package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Date;
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

import com.cst438.domain.Actor;
import com.cst438.dto.ActorDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JunitTestActors {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getActors() throws Exception {
        MockHttpServletResponse response = mvc.perform(
        		MockMvcRequestBuilders
        				.get("/actors/")
        				.contentType(MediaType.APPLICATION_JSON)
        				.accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the get request was successful
        assertEquals(200, response.getStatus());
        
        // Create a list of ActorDTOs
        String content = response.getContentAsString();
        
        List<ActorDTO> actorList = objectMapper.readValue(
            content, new TypeReference<List<ActorDTO>>() {}
        );

        // Verify that the actors DTO list is not null
        assertNotNull(actorList, "Actor's list is null.");
        // Verify that the actors DTO list is not empty
        assertTrue(!actorList.isEmpty(), "Actor's list is empty.");
    }	

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getActorByID() throws Exception {
    	MockHttpServletResponse response = mvc.perform(
        		MockMvcRequestBuilders
        				.get("/actors/1")
        				.contentType(MediaType.APPLICATION_JSON)
        				.accept(MediaType.APPLICATION_JSON))
    			  	  .andReturn().getResponse();

    	// Verify that the get request was successful
    	assertEquals(200, response.getStatus());
    	
        // Create an ActorDTO
    	ActorDTO actor = objectMapper.readValue(response.getContentAsString(),
            ActorDTO.class
        );

        // Verify that the actor DTO is not null
    	assertNotNull(actor, "Actor is null.");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createActor() throws Exception {
        Actor newActor = new Actor();
        LocalDate dob = LocalDate.of(1998, 3, 17);
        
        newActor.setName("Juan Ansaldo");
        newActor.setDOB(dob);
        newActor.setPortrait("test.jpg");
        newActor.setAbout("Computer Science Student at CSU Monterey Bay.");

        String actorJson = objectMapper.writeValueAsString(newActor);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/actors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actorJson)
                        .accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the post request was successful
        assertEquals(200, response.getStatus());

        // Check if the data that was passed was in the correct format
        String content = response.getContentAsString();
        assertFalse(content.isEmpty(), "Response content is empty");
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void createActorAsUser() throws Exception {
        Actor newActor = new Actor();
        LocalDate dob = LocalDate.of(1998, 3, 17);
        
        newActor.setName("Juan Ansaldo");
        newActor.setDOB(dob);
        newActor.setPortrait("test.jpg");
        newActor.setAbout("Computer Science Student at CSU Monterey Bay.");

        String actorJson = objectMapper.writeValueAsString(newActor);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/actors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actorJson)
                        .accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the post request failed
        assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateActor() throws Exception {
        Actor updateActor = new Actor();
        updateActor.setName("Bradley Pitt");

        String actorJson = objectMapper.writeValueAsString(updateActor);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .put("/actors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actorJson)
                        .accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the put request was successful
        assertEquals(200, response.getStatus());
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"User"})
    public void updateActorAsUser() throws Exception {
        Actor updateActor = new Actor();
        updateActor.setName("Bradley Pitt");

        String actorJson = objectMapper.writeValueAsString(updateActor);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .put("/actors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actorJson)
                        .accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the put request failed
        assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteActor() throws Exception {
    	// Verify that the actor exists
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/actors/1")
                        .accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the get request was successful
        assertEquals(200, response.getStatus());

        // Delete the actor
        response = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/actors/1")
                        .accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the delete request was successful
        assertEquals(200, response.getStatus());

        // Verify that the actor no longer exists
        response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/actors/1")
                        .accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the get request fails
        assertEquals(404, response.getStatus());
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void deleteActorAsUser() throws Exception {
    	// Verify that the actor exists
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/actors/1")
                        .accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the get request was successful
        assertEquals(200, response.getStatus());

        // Delete the actor
        response = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/actors/1")
                        .accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the delete request failed
        assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateNonExistingActor() throws Exception {
        Actor updateActor = new Actor();
        
        updateActor.setName("Fabian Santano");
        
        String actorJson = objectMapper.writeValueAsString(updateActor);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.put("/actors/9999")
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.content(actorJson)
                    	.accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the put request fails
        assertEquals(404, response.getStatus());
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void updateNonExistingActorAsUser() throws Exception {
        Actor updateActor = new Actor();
        
        updateActor.setName("Fabian Santano");
        
        String actorJson = objectMapper.writeValueAsString(updateActor);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.put("/actors/9999")
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.content(actorJson)
                    	.accept(MediaType.APPLICATION_JSON))
                	  .andReturn().getResponse();

        // Verify that the put request failed
        assertEquals(400, response.getStatus());
    }
}
