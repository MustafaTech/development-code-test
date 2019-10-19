package uk.co.huntersix.spring.rest.referencedata;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import uk.co.huntersix.spring.rest.exceptions.PersonAlreadyExistsException;
import uk.co.huntersix.spring.rest.exceptions.PersonNotFoundException;
import uk.co.huntersix.spring.rest.model.Person;

@Service
public class PersonDataService {
	public static final List<Person> PERSON_DATA = Arrays.asList(
																	new Person("Mary", "Smith"),
																	new Person("Brian", "Archer"), 
																	new Person("Collin", "Brown")
																);
    /**
     * Find a person with the given last name and first name.
     *
     * @param lastName of the Person and firstName of the Person, case-insensitive
     * @return a Person with the given lastName and firstName, if exists.
     */
	public Person findPerson(String lastName, String firstName) {
		return PERSON_DATA.stream()
				.filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
				.findFirst()
				.orElseThrow(PersonNotFoundException::new);
	}

    /**
     * Create a person with Person which contains the last name and the first name of the person, if it doesn't exist.
     *
     * @param Person 
     * @return void	.
     */
	public void addPerson(Person person) {
		boolean doesPersonExist = PERSON_DATA.stream()
				.anyMatch(p -> p.getFirstName().equalsIgnoreCase(person.getFirstName())
						&& p.getLastName().equalsIgnoreCase(person.getLastName()));
		if (doesPersonExist) {
			throw new PersonAlreadyExistsException();
		}
		PERSON_DATA.add(person);
	}

    /**
     * Finds all people with the given last name of a person.
     *
     * @param lastName the last name of Person, case-insensitive
     * @return a list of Person.
     */
	public List<Person> findByLastName(String lastName) {
		return PERSON_DATA.stream()
				.filter(p -> p.getLastName().equalsIgnoreCase(lastName))
				.collect(Collectors.toList());
	}

}
