package uk.co.huntersix.spring.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;
/*
 * person			:  	Retrieve a person if exists.
 * 
 * createNewPerson 	:	Create a new person, if it doesn't exist.
 * 
 * allPeople		:	Retrieve all people by last name 
 * 
 */
@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/person/{lastName}/{firstName}")
    public Person person(@PathVariable(value="lastName") String lastName,
                         @PathVariable(value="firstName") String firstName) {
        return personDataService.findPerson(lastName, firstName);
    }
    
    @PostMapping("/person")
    public Person createNewPerson(@RequestBody Person person) {
    	personDataService.addPerson(person);
        return person;
    }
    
    @GetMapping("/person/{lastName}")
    public List<Person> allPeople(@PathVariable(value = "lastName") String lastName) {
        return personDataService.findByLastName(lastName);
    }
}