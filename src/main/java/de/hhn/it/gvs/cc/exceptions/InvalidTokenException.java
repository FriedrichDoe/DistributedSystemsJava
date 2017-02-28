package de.hhn.it.gvs.cc.exceptions;

/**
 * This exception is thrown when the token is invalid.
 * 
 * @version 2016-09-29
 *
 */
public class InvalidTokenException extends Exception {
	public InvalidTokenException(String message) {
		super(message);
	}
}