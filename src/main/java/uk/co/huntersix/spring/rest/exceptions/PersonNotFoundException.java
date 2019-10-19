package uk.co.huntersix.spring.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 *  @ResponseStatus provides you different status code and error message in the response.
 *  You can also use ResponseStatusException in your RestController class, as a programmatic alternative.
 *  Usage of ResponseStatusException ( in RestController Class ):
 *  try{
 *  ...
 *  }catch(PersonNotFoundException pex){
 *  	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Person not found", pex)
 *  }
*/
@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Person not found")
public class PersonNotFoundException extends RuntimeException {
    /**
	 * Create business logic exception to help the application users or
	 * the developers understand what the exact problem is.
	 * Throw this exception when person not found in all people list.
	 * This custom exception is an unchecked exception. So it extended to RuntimeException. 
	 */

	private static final long serialVersionUID = 1L;

	public PersonNotFoundException() {
        super("Person not found");
    }
}