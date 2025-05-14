package org.spacex.model;

public enum MissionStatus {

	SCHEDULED("Scheduled"),  	// initial status, where no rockets are assigned
	PENDING("Pending"), 		//  at least one rocket is assigned and one or more assigned rockets are in repair
	IN_PROGRESS("In progress"), 	//at least one rocket is assigned and none of them is in repair
	ENDED("Ended");

	private String readableName;

	MissionStatus(String readableName) {
		this.readableName = readableName;
	}

	public String getReadableName() {
		return readableName;
	}
}
