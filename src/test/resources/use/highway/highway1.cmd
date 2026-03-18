



-- Truck
!create t1:Truck
!set t1.plate := '01'
!set t1.type := 4
!set t1.weight := 1000.0
!set t1.payloadCapacity := 1000.0




-- OBU
!create obu1 : OBU
!set obu1.batteryLevel := 56




-- EquippedWith
!insert (t1,obu1) into EquippedWith




-- Road
!create r1:Road
!set r1.maxSpeedLimit := 90
!set r1.minSpeedLimit := 60




-- RoadSideDevice
!create rd11:RoadSideDevice
!set rd11.name := 'Road_1 Entry'
!set rd11.type := 'Entry'
!set rd11.toll := 0.0
!set rd11.isClosed := false

!create rd12:RoadSideDevice
!set rd12.name := 'Road_1 Gantry_1'
!set rd12.type := 'Gantry'
!set rd12.toll := 10.5
!set rd12.isClosed := false

!create rd13:RoadSideDevice
!set rd13.name := 'Road_1 Gantry_2'
!set rd13.type := 'Gantry'
!set rd13.toll := 15.5
!set rd13.isClosed := false

!create rd14:RoadSideDevice
!set rd14.name := 'Road_1 Exit'
!set rd14.type := 'Exit'
!set rd14.toll := 12.0
!set rd14.isClosed := false




-- HasDevices
!insert (r1,rd11) into HasDevices
!insert (r1,rd12) into HasDevices
!insert (r1,rd13) into HasDevices
!insert (r1,rd14) into HasDevices




-- ===========================================================================

-- PassRecord
!create pr11 : PassRecord
!set pr11.plate := '01'
!set pr11.passWeight := 1533.2
!set pr11.speed := 23.3


!create pr12 : PassRecord
!set pr12.plate := '01'
!set pr12.passWeight := 1533.2
!set pr12.speed := 88.1


!create pr13 : PassRecord
!set pr13.plate := '01'
!set pr13.passWeight := 1533.2
!set pr13.speed := 100.0


!create pr14 : PassRecord
!set pr14.plate := '01'
!set pr14.passWeight := 1533.2
!set pr14.speed := 66.7


-- HasRecords
!insert (obu1,pr11) into HasRecords
!insert (obu1,pr12) into HasRecords
!insert (obu1,pr13) into HasRecords
!insert (obu1,pr14) into HasRecords


-- Trip
!create trip1:Trip
!set trip1.totalToll := 38.0
!set trip1.status := 1
!set trip1.entranceWeight := 1533.2


-- TripContains
!insert (trip1,pr11) into TripContains
!insert (trip1,pr12) into TripContains
!insert (trip1,pr13) into TripContains
!insert (trip1,pr14) into TripContains


-- Generates
!insert (rd11,pr11) into Generates
!insert (rd12,pr12) into Generates
!insert (rd13,pr13) into Generates
!insert (rd14,pr14) into Generates

