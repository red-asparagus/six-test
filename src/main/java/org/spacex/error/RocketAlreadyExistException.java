package org.spacex.error;

public class RocketAlreadyExistException extends RuntimeException {


	public RocketAlreadyExistException(final String name) {
		super("Rocket with name '" + name + "' already exists");
	}
}
