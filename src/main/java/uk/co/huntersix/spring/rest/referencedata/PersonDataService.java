package uk.co.huntersix.spring.rest.referencedata;

import java.util.ArrayList;
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
	private final List<Person> allPeople = new ArrayList<>(PERSON_DATA);

	public Person findPerson(String lastName, String firstName) {
		return allPeople.stream()
				.filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
				.findFirst()
				.orElseThrow(PersonNotFoundException::new);
	}

	public void addPerson(Person person) {
		boolean doesPersonExits = allPeople.stream()
				.anyMatch(p -> p.getFirstName().equalsIgnoreCase(person.getFirstName())
						&& p.getLastName().equalsIgnoreCase(person.getLastName()));
		if (doesPersonExits) {
			throw new PersonAlreadyExistsException();
		}
		allPeople.add(person);
	}

	public List<Person> findByLastName(String lastName) {
		return allPeople.stream()
				.filter(p -> p.getLastName().equalsIgnoreCase(lastName))
				.collect(Collectors.toList());
	}

}
