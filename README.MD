

## Assumptions:

### missions

every mission have 2 fields:
status - status of mission, which is defined in specification
name - name of mission, which is not mentioned in specification, but examples assume that missions has a name

every mission has a unique in universe name

### rockets

every rocket have 2 fields:

status - status of mission, which is defined in specification
name - name of rocket, which is not mentioned in specification, but examples assume that rocket has a name

every mission has a unique in universe name
rocket could be assigned to another mission, removing it from old mission

we need two may relation between rockets and missions, 
that's we create 2 mappings: rocket to mission (one-to-one) and mission to rockets(one-to_many)


### misc

method assignRocketsToMission() check if all rockets in list could be added, then assign them all to mission, or throw exception if not possible