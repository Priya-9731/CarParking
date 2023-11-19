Project Name - Car Parking  System

Description:
The Car Parking  System is a  SpringBoot application  designed to efficiently manage the parking of cars in a parking lot. The system provides functionalities for parking cars, removing cars, calculating parking fees, and viewing details of all parked cars.

ER Diagram:

Refer Word Document attached.

Justifications:

Car Entity:

Assumes that each car has a unique identifier (carId).

ParkingLot Entity:

Assumes that the parking lot is more about functionality and doesn't store specific data. It could represent the concept of a parking lot without detailed attributes.

Parking Relationship:

Represents the act of parking a car in a parking lot.
Includes the entry time and exit time as attributes associated with the parking relationship.

Assumptions:

The focus of this ER diagram is on the basic functionality related to parking cars

This ER diagram assumes a one-to-many relationship between a car and parking, meaning a car can be parked multiple times, but each parking entry corresponds to a single car.

JUNIT Testcase 

Additionally I have written Junit test cases using mockito - all the test cases are added in ParkingControllerTest class
