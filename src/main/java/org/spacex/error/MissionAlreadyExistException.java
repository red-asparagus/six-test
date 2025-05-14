package org.spacex.error;

public class MissionAlreadyExistException extends RuntimeException {


	public MissionAlreadyExistException(final String name) {
		super("Mission with name '" + name + "' already exists");
	}
}
