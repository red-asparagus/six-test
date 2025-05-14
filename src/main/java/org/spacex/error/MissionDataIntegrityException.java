package org.spacex.error;

public class MissionDataIntegrityException extends RuntimeException {

	public MissionDataIntegrityException(final String name) {
		super("Attempt of adding multiple rockets to mission '" + name +"' had failed");
	}
}
