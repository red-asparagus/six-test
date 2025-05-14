package org.spacex.error;

public class MissionEndedException extends RuntimeException {


	public MissionEndedException(final String name) {
		super("Mission with name '" + name + "' ended. No rocket addition available");
	}
}