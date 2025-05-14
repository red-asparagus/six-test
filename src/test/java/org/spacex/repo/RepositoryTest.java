package org.spacex.repo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spacex.error.MissionAlreadyExistException;
import org.spacex.error.MissionNotExistException;
import org.spacex.error.RocketNotExistException;
import org.spacex.model.MissionStatus;
import org.spacex.model.RocketStatus;
import org.spacex.model.SpaceMission;
import org.spacex.model.SpaceRocket;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RepositoryTest {

	private final Repository repository = new Repository();

	@BeforeEach
	void before() {
		repository.clear();
	}

	@Test
	void shouldNotAddSameRocketTwice() {

		SpaceRocket rocket1 = new SpaceRocket("test", RocketStatus.ON_GROUND);

		repository.addRocket(rocket1);

		assertThrows(MissionAlreadyExistException.class, () -> repository.addRocket(rocket1));
	}

	@Test
	void shouldNotAddSameMissionTwice() {

		SpaceMission mission1 = new SpaceMission(MissionStatus.SCHEDULED, "test");

		repository.addMission(mission1);

		assertThrows(MissionAlreadyExistException.class, () -> repository.addMission(mission1));
	}

	@Test
	void shouldNotAssignRocketToNonExistingMission() {

		SpaceMission mission1 = new SpaceMission(MissionStatus.SCHEDULED, "test");
		SpaceRocket rocket1 = new SpaceRocket("test", RocketStatus.ON_GROUND);

		repository.addRocket(rocket1);

		assertThrows(MissionNotExistException.class, () -> repository.assignRocketToMission(mission1, rocket1));
	}

	@Test
	void shouldNotAssignNonExistingRocketToMission() {

		SpaceMission mission1 = new SpaceMission(MissionStatus.SCHEDULED, "test");
		SpaceRocket rocket1 = new SpaceRocket("test", RocketStatus.ON_GROUND);

		repository.addMission(mission1);

		assertThrows(RocketNotExistException.class, () -> repository.assignRocketToMission(mission1, rocket1));
	}

	@Test
	void shouldNotAssignNonExistingRocketToNonExistingMission() {

		SpaceMission mission1 = new SpaceMission(MissionStatus.SCHEDULED, "test");
		SpaceRocket rocket1 = new SpaceRocket("test", RocketStatus.ON_GROUND);

		assertThrows(MissionNotExistException.class, () -> repository.assignRocketToMission(mission1, rocket1));
	}

	@Test
	void shouldAssignRocketToRocket() {

		SpaceMission mission1 = new SpaceMission(MissionStatus.SCHEDULED, "test");
		SpaceRocket rocket1 = new SpaceRocket("test", RocketStatus.ON_GROUND);

		repository.addMission(mission1);
		repository.addRocket(rocket1);

		repository.assignRocketToMission(mission1, rocket1);

		repository.containRocket(rocket1);
		repository.containMission(mission1);
	}



	@Test
	void summary() {

		SpaceMission mission1 = new SpaceMission(MissionStatus.SCHEDULED, "Mars");
		repository.addMission(mission1);

		SpaceMission mission2 = new SpaceMission(MissionStatus.PENDING, "Luna1");
		SpaceRocket rocket1 = new SpaceRocket("Dragon 1", RocketStatus.ON_GROUND);
		SpaceRocket rocket2 = new SpaceRocket("Dragon 2", RocketStatus.ON_GROUND);

		repository.addMission(mission2);
		repository.addRocket(rocket1);
		repository.addRocket(rocket2);
		repository.assignRocketToMission(mission2, rocket1);
		repository.assignRocketToMission(mission2, rocket2);
		repository.changeMissionStatus(mission2, MissionStatus.PENDING);

		SpaceMission mission3 = new SpaceMission(MissionStatus.IN_PROGRESS, "Double Landing");
		repository.addMission(mission3);
		repository.changeMissionStatus(mission3, MissionStatus.ENDED);

		SpaceMission mission4 = new SpaceMission(MissionStatus.IN_PROGRESS, "Transit");
		SpaceRocket rocket3 = new SpaceRocket("Red Dragon", RocketStatus.ON_GROUND);
		SpaceRocket rocket4 = new SpaceRocket("Dragon XL", RocketStatus.IN_SPACE);
		SpaceRocket rocket5 = new SpaceRocket("Falcon Heavy", RocketStatus.IN_SPACE);

		repository.addMission(mission4);
		repository.addRocket(rocket3);
		repository.addRocket(rocket4);
		repository.addRocket(rocket5);
		repository.assignRocketToMission(mission4, rocket3);
		repository.assignRocketToMission(mission4, rocket4);
		repository.assignRocketToMission(mission4, rocket5);
		repository.changeMissionStatus(mission4, MissionStatus.IN_PROGRESS);
		repository.changeRocketStatus(rocket4, RocketStatus.IN_SPACE);
		repository.changeRocketStatus(rocket5, RocketStatus.IN_SPACE);

		SpaceMission mission5 = new SpaceMission(MissionStatus.SCHEDULED, "Luna2");
		repository.addMission(mission5);


		SpaceMission mission6 = new SpaceMission(MissionStatus.ENDED, "Vertical Landing");
		repository.addMission(mission6);

		repository.printSummary();
	}

}