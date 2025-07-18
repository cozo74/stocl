# STOCL
This is the code for "STOCL: Translating OCL Invariants to SQL Queries through Relational Algebra for Object Constraint Checking".


# Examples



Input OCL invariants:


```ocl

Model shenzhen :
context Car
inv : self.speed <= 120
inv : self.speed <= 120 * 1.1
inv :  self.direction >= 0 and self.direction <= 360 
inv : Car.allInstances()->select(c | not c.speed < 120 )->size()=0


```


Generated SQL queries:


```sql

USE shenzhen;
SELECT car_id,speed FROM Car WHERE speed>120;
SELECT car_id,speed FROM Car WHERE speed>120*1.1;
(SELECT car_id,direction FROM Car)  EXCEPT  (SELECT car_id,direction FROM Car WHERE direction>=0 AND direction<=360);
SELECT * FROM ( SELECT COUNT(car_id) AS car_id_count FROM ( SELECT car_id FROM Car WHERE speed>=120 ) AS sq2 ) AS sq3 WHERE car_id_count<>0;
```

