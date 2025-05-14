package org.spacex.model;

import java.util.Objects;

public class SpaceMission {

	private MissionStatus status;
	private String name;

	public SpaceMission(MissionStatus status, String name) {
		this.status = status;
		this.name = name;
	}

	public MissionStatus getStatus() {
		return status;
	}

	public void setStatus(MissionStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public boolean isEnded() {
		return status == MissionStatus.ENDED;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SpaceMission that = (SpaceMission) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

}
