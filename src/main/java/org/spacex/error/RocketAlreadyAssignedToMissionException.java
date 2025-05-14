package org.spacex.error;

public class RocketAlreadyAssignedToMissionException extends RuntimeException {

	public RocketAlreadyAssignedToMissionException(final String mname, final String rname) {
		super("Rocket '" + rname + "' already assigned to mission with name '" + mname + "'");
	}
}
