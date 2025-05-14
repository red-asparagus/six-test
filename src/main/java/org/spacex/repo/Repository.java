package org.spacex.repo;

import org.spacex.error.MissionAlreadyExistException;
import org.spacex.error.MissionDataIntegrityException;
import org.spacex.error.MissionEndedException;
import org.spacex.error.MissionNotExistException;
import org.spacex.error.RocketAlreadyAssignedToMissionException;
import org.spacex.error.RocketNotExistException;
import org.spacex.model.MissionStatus;
import org.spacex.model.RocketStatus;
import org.spacex.model.SpaceMission;
import org.spacex.model.SpaceRocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;


import static java.util.Collections.emptyList;


public class Repository {

	// one mission to many rockets mapping
	private HashMap<SpaceMission, List<SpaceRocket>> missionToRocketsMap = new HashMap<>();

	// one rocket to one mission mapping
	private HashMap<SpaceRocket, SpaceMission> rocketToMissionMap = new HashMap<>();

	private List<SpaceMission> missions = new ArrayList<>();
	private List<SpaceRocket> rockets = new ArrayList<>();

	// add new rocket
	public void addRocket(final SpaceRocket newRocket) {
		if(rockets.contains(newRocket)) {
			throw new MissionAlreadyExistException(newRocket.getName());
		}

		newRocket.setStatus(RocketStatus.ON_GROUND);
		rockets.add(newRocket);

	}

	// assign rocket to mission

	public void assignRocketToMission(final SpaceMission mission, final SpaceRocket rocket) {

		if(!missions.contains(mission)) {
			throw new MissionNotExistException(mission.getName());
		}

		if(!rockets.contains(rocket)) {
			throw new RocketNotExistException(rocket.getName());
		}

		if(mission.isEnded()) {
			throw new MissionEndedException(mission.getName());
		}

		retractRocketFromMission(rocket);

		// assign rocket to new mission
		missionToRocketsMap.compute(mission, (mission1, spaceRockets) -> {
			if(spaceRockets.contains(rocket)) {
				throw new RocketAlreadyAssignedToMissionException(mission1.getName(), rocket.getName());
			}

			List<SpaceRocket> newList = new ArrayList<>(spaceRockets);
			newList.add(rocket);

			return newList;
		});

		rocketToMissionMap.computeIfPresent(rocket, (rocket1, mission12) -> mission12);

	}

	private void retractRocketFromMission(final SpaceRocket rocket) {

		// retract rocket from old mission, if rocket is assigned
		if(rocketToMissionMap.containsKey(rocket)) {
			final var foundMission = rocketToMissionMap.get(rocket);
			final var rocketsInMission = missionToRocketsMap.get(foundMission);
			rocketsInMission.remove(rocket);
			missionToRocketsMap.put(foundMission, rocketsInMission);

			rocketToMissionMap.remove(rocket);
		}
	}

	// change rocket status
	public void changeRocketStatus(final SpaceRocket rocket, final RocketStatus newStatus) {
		Optional<SpaceRocket> foundRocket = rockets.stream().filter(x -> x.equals(rocket)).findFirst();

		if(foundRocket.isEmpty()) {
			throw new RocketNotExistException(rocket.getName());
		} else {
			SpaceRocket rocket1 = foundRocket.get();
			rocket1.setStatus(newStatus);
		}
	}

	// add new mission
	public void addMission(final SpaceMission newMission) {

		if(missions.contains(newMission)) {
			throw new MissionAlreadyExistException(newMission.getName());
		}

		newMission.setStatus(MissionStatus.SCHEDULED);
		missions.add(newMission);

		missionToRocketsMap.put(newMission, emptyList());
	}

	// assign rocket to mission

	public void assignRocketsToMission(final SpaceMission mission, final List<SpaceRocket> rockets) {
		Set<SpaceRocket> set = new HashSet<>(rockets);

		if(rockets.size() != set.size()) {
			throw new RuntimeException("Non unique rockets in input list");
		}

		final var rocketsInMission = missionToRocketsMap.get(mission);

		if(Collections.disjoint(rocketsInMission, rockets)) {
			throw new MissionDataIntegrityException(mission.getName());
		}

		rockets.forEach(rock -> assignRocketToMission(mission, rock));
	}


	// change mission status
	public void changeMissionStatus(final SpaceMission mission, final MissionStatus newStatus) {
		Optional<SpaceMission> foundMission = missions.stream().filter(x -> x.equals(mission)).findFirst();

		if(foundMission.isEmpty()) {
			throw new MissionNotExistException(mission.getName());
		} else {
			SpaceMission mission1 = foundMission.get();
			mission1.setStatus(newStatus);
		}
	}


	public void printSummary() {
		final Set<Map.Entry<SpaceMission, List<SpaceRocket>>> entrySet = missionToRocketsMap.entrySet();

		Comparator<Map.Entry<SpaceMission, List<SpaceRocket>>> comparator = Comparator
			.comparing((Function<Map.Entry<SpaceMission, List<SpaceRocket>>, Integer>) listEntry -> listEntry.getValue().size(), Comparator.reverseOrder())
			.thenComparing(listEntry -> listEntry.getKey().getName());

		final var sortedEntries = entrySet.stream().sorted(comparator);

		sortedEntries.forEach(listEntry -> {

			final var mapMission = listEntry.getKey();
			final var mapRockets = listEntry.getValue();

			System.out.println(mapMission.getName() + " - " + mapMission.getStatus().getReadableName() + " - Dragons: " + mapRockets.size());

			mapRockets.forEach(roc -> System.out.println(" >> " + roc.getName() + " - " + roc.getStatus().getReadableName()));
		});
	}


	// for tests

	boolean containRocket(final SpaceRocket rocket) {
		return rockets.contains(rocket);
	}

	boolean containMission(final SpaceMission mission) {
		return missions.contains(mission);
	}

	void clear() {
		missionToRocketsMap.clear();
		rocketToMissionMap.clear();

		missions.clear();
		rockets.clear();
	}

}
