package uk.co.huntersix.spring.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 *  @ResponseStatus provides you different status code and error message in the response.
 *  You can also use ResponseStatusException in your RestController class, as a programmatic alternative.
 *  Usage of ResponseStatusException ( in RestController Class ):
 *  try{
 *  ...
 *  }catch(PersonAlreadyExistsException pex){
 *  	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Person already exists", pex)
 *  }
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Person already exists")
public class PersonAlreadyExistsException extends RuntimeException {
    /**
	 * Create business logic exception to help the application users or
	 * the developers understand what the exact problem is.
	 * Throw this exception when person already exists while creating a new person
	 * This custom exception is an unchecked exception. So it extended to RuntimeException.
	 */
	private static final long serialVersionUID = 4794333554250624792L;

	/**
	 * 
	 */

	public PersonAlreadyExistsException() {
        super("Person already exists");
    }
}
