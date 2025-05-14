package org.spacex.model;

import java.util.Objects;

public class SpaceRocket {

	private RocketStatus status;
	private String name;

	public SpaceRocket(String name, RocketStatus status) {
		this.status = status;
		this.name = name;
	}

	public RocketStatus getStatus() {
		return status;
	}

	public void setStatus(RocketStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SpaceRocket that = (SpaceRocket) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
