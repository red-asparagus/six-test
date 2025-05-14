package org.spacex.error;

public class MissionNotExistException extends RuntimeException {

	public MissionNotExistException(final String name) {
		super("Mission with name '" + name + "' not exists");
	}
}
