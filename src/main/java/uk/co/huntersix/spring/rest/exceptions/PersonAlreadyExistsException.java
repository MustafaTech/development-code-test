package uk.co.huntersix.spring.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Person already exists")
public class PersonAlreadyExistsException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4794333554250624792L;

	/**
	 * 
	 */

	public PersonAlreadyExistsException() {
        super("Person already exists");
    }
}
