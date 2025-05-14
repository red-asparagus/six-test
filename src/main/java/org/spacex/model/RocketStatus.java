package org.spacex.model;

public enum RocketStatus {
	ON_GROUND("On ground"), 	// initial status, where the rocket is not assigned to any mission
	IN_SPACE("On ground"), 	// the rocket was assigned to the mission
	IN_REPAIR("In repair");	// the rocket is due to repair, it implies “Pending” status of the mission

	private String readableName;

	RocketStatus(String readableName) {
		this.readableName = readableName;
	}

	public String getReadableName() {
		return readableName;
	}
}
