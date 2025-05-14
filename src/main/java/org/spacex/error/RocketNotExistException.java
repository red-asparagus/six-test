package org.spacex.error;

public class RocketNotExistException extends RuntimeException {

	public RocketNotExistException(final String name) {
		super("Rocket with name '" + name + "' not exists");
	}
}
