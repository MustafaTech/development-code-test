package uk.co.huntersix.spring.rest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.huntersix.spring.rest.exceptions.PersonAlreadyExistsException;
import uk.co.huntersix.spring.rest.exceptions.PersonNotFoundException;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

/*
 * 
 *  PersonControllerTest class is used for testing PersonController class through JUnit.
 *  
 *  shouldReturnPersonFromService 						:  Retrieve a Person record from all people list
 *  shouldThrowExceptionPersonNotFoundService 			:  Throw an exception which is PersonNotFoundService exception,
 *  											   			if the requested person does not exist in the list
 *  shouldCreateNewPerson								:  Put a new Person record into all people list
 *  shouldThrowExceptionCreateNewPersonWhenPersonExists :  Throw an exception which is PersonAlreadyExistsException exception,
 *  														if the person exists.	
 *  shouldReturnMultipleMatchesFromAllPeopleList		:  Retrieve multiple matches by person Last Name from all people list
 *  shouldReturnNoMatchFromAllPeopleList				:  No match by person Last Name from all people list
 *  shouldReturnOneMatchFromAllPeopleList				:  One match by person Last Name from all people list
 *  
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(new Person("Mary", "Smith"));
        this.mockMvc.perform(get("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Mary"))
            .andExpect(jsonPath("lastName").value("Smith"));
    }
    
    @Test (expected = PersonNotFoundException.class)
    public void shouldThrowExceptionPersonNotFoundService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenThrow(PersonNotFoundException.class);
        this.mockMvc.perform(get("/person/yyy/zzz"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(status().reason("Person not found"));
    }
    
    @Test
    public void shouldCreateNewPerson() throws Exception {
        String jsonMessageBody = new ObjectMapper().writeValueAsString(new Person("Gareth", "Sweetland"));

        this.mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(jsonMessageBody))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(jsonMessageBody));
        
        verify(personDataService, times(1)).addPerson(any());
        
    }
    
    @Test (expected = PersonAlreadyExistsException.class)
    public void shouldThrowExceptionCreateNewPersonWhenPersonExists() throws Exception {
        String jsonMessageBody = new ObjectMapper().writeValueAsString(new Person("Gareth", "Sweetland"));
        doThrow(PersonAlreadyExistsException.class).when(personDataService).addPerson(any());
        this.mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(jsonMessageBody))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason("Person already exists"));
    }    
    
    
    @Test
    public void shouldReturnMultipleMatchesFromAllPeopleList() throws Exception {
        when(personDataService.findByLastName(any())).thenReturn(Arrays.asList(
            new Person("Mark", "Brown"),
            new Person("Mary", "Brown")
        ));
        this.mockMvc.perform(get("/person/brown"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].firstName").value("Mary"))
            .andExpect(jsonPath("$[0].lastName").value("Brown"))
            .andExpect(jsonPath("$[1].firstName").value("Mark"))
            .andExpect(jsonPath("$[1].lastName").value("Brown"));
    }

    @Test
    public void shouldReturnNoMatchFromAllPeopleList() throws Exception {
        when(personDataService.findByLastName(any())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/person/smith"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldReturnOneMatchFromAllPeopleList() throws Exception {
        when(personDataService.findByLastName(any())).thenReturn(Collections.singletonList(new Person("Collin", "Brown")));

        this.mockMvc.perform(get("/person/brown"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].firstName").value("Collin"))
            .andExpect(jsonPath("$[0].lastName").value("Brown"));
        
    }
    
    
    
}