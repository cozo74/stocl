


class PassRecord 
attributes
  plate : String
  passWeight : Real
  speed : Real
  toll : Real
end

class Trip 
attributes
  totalToll : Real
  status : Integer
end





-- Truck
!create t1:Truck
!set t1.plate := '01'
!set t1.type := 4
!set t1.weight := 1000.0
!set t1.payloadCapacity := 1000.0

!create t2:Truck
!set t2.plate := '02'
!set t2.type := 5
!set t2.weight := 2000.0
!set t2.payloadCapacity := 2000.0

!create t3:Truck
!set t3.plate := '03'
!set t3.type := 6
!set t3.weight := 3000.0
!set t3.payloadCapacity := 3000.0



-- OBU
!create obu1 : OBU
!set obu1.batteryLevel := 56

!create obu2 : OBU
!set obu2.batteryLevel := 88

!create obu3 : OBU
!set obu3.batteryLevel := 23



-- EquippedWith
!insert (t1,obu1) into EquippedWith
!insert (t2,obu2) into EquippedWith
!insert (t3,obu3) into EquippedWith



-- Road
!create r1:Road
!set r1.maxSpeedLimit := 120
!set r1.minSpeedLimit := 60

!create r2:Road
!set r2.maxSpeedLimit := 100
!set r2.minSpeedLimit := 60

!create r3:Road
!set r3.maxSpeedLimit := 80
!set r3.minSpeedLimit := 40


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


!create rd21:RoadSideDevice
!set rd21.name := 'Road_2 Entry'
!set rd21.type := 'Entry'
!set rd21.toll := 0.0
!set rd21.isClosed := false

!create rd22:RoadSideDevice
!set rd22.name := 'Road_2 Gantry_1'
!set rd22.type := 'Gantry'
!set rd22.toll := 8.5
!set rd22.isClosed := false

!create rd23:RoadSideDevice
!set rd23.name := 'Road_2 Exit'
!set rd23.type := 'Exit'
!set rd23.toll := 15.0
!set rd23.isClosed := false



!create rd31:RoadSideDevice
!set rd31.name := 'Road_3 Entry'
!set rd31.type := 'Entry'
!set rd31.toll := 0.0
!set rd31.isClosed := false

!create rd32:RoadSideDevice
!set rd32.name := 'Road_3 Gantry_1'
!set rd32.type := 'Gantry'
!set rd32.toll := 15.5
!set rd32.isClosed := false

!create rd33:RoadSideDevice
!set rd33.name := 'Road_3 Gantry_2'
!set rd33.type := 'Gantry'
!set rd33.toll := 25.5
!set rd33.isClosed := false

!create rd34:RoadSideDevice
!set rd34.name := 'Road_3 Exit'
!set rd34.type := 'Exit'
!set rd34.toll := 8.5
!set rd34.isClosed := false



-- HasDevices
!insert (r1,rd11) into HasDevices
!insert (r1,rd12) into HasDevices
!insert (r1,rd13) into HasDevices
!insert (r1,rd14) into HasDevices

!insert (r2,rd21) into HasDevices
!insert (r2,rd22) into HasDevices
!insert (r2,rd23) into HasDevices
!insert (r2,rd24) into HasDevices

!insert (r3,rd31) into HasDevices
!insert (r3,rd32) into HasDevices
!insert (r3,rd33) into HasDevices
!insert (r3,rd34) into HasDevices


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



-- ===========================================================================



-- PassRecord
!create pr21 : PassRecord
!set pr21.plate := '02'
!set pr21.passWeight := 3334.0
!set pr21.speed := 17.5


!create pr22 : PassRecord
!set pr22.plate := '02'
!set pr22.passWeight := 3334.0
!set pr22.speed := 86.1


!create pr23 : PassRecord
!set pr23.plate := '02'
!set pr23.passWeight := 3334.0
!set pr23.speed := 37.6




-- HasRecords
!insert (obu2,pr21) into HasRecords
!insert (obu2,pr22) into HasRecords
!insert (obu2,pr23) into HasRecords


-- Trip
!create trip2:Trip
!set trip2.totalToll := 23.5
!set trip2.status := 1
!set trip2.entranceWeight := 3334.0

-- TripContains
!insert (trip2,pr21) into TripContains
!insert (trip2,pr22) into TripContains
!insert (trip2,pr23) into TripContains


-- Generates
!insert (rd21,pr21) into Generates
!insert (rd22,pr22) into Generates
!insert (rd23,pr23) into Generates



-- ===========================================================================




-- PassRecord
!create pr31 : PassRecord
!set pr31.plate := '03'
!set pr31.passWeight := 4718.5
!set pr31.speed := 22.5


!create pr32 : PassRecord
!set pr32.plate := '03'
!set pr32.passWeight := 4718.5
!set pr32.speed := 77.1


!create pr33 : PassRecord
!set pr33.plate := '03'
!set pr33.passWeight := 5718.5
!set pr33.speed := 79.6



!create pr34 : PassRecord
!set pr34.plate := '03'
!set pr34.passWeight := 5718.5
!set pr34.speed := 33.3


-- HasRecords
!insert (obu3,pr31) into HasRecords
!insert (obu3,pr32) into HasRecords
!insert (obu3,pr33) into HasRecords
!insert (obu3,pr34) into HasRecords


-- Trip
!create trip3:Trip
!set trip3.totalToll := 49.5
!set trip3.status := 1
!set trip3.entranceWeight := 4718.5

-- TripContains
!insert (trip3,pr31) into TripContains
!insert (trip3,pr32) into TripContains
!insert (trip3,pr33) into TripContains
!insert (trip3,pr34) into TripContains


-- Generates
!insert (rd31,pr31) into Generates
!insert (rd32,pr32) into Generates
!insert (rd33,pr33) into Generates
!insert (rd34,pr34) into Generates
