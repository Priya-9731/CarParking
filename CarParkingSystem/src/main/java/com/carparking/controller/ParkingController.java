package com.carparking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carparking.model.ParkingLot;
import com.carparking.model.Car;


@RestController
@RequestMapping("/parking") // API to Park the car
public class ParkingController {
    private final ParkingLot parkingLot = new ParkingLot();

    @PostMapping("/park")
    public ResponseEntity<String> parkCar(@RequestBody Car car) {
        if (!parkingLot.isFull()) {
            parkingLot.parkCar(car);
            return ResponseEntity.ok("Car parked successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking lot is full.");
        }
    }

    @PostMapping("/remove/{carId}") // API call to remove car and calculate the cost 
    public ResponseEntity<String> removeCar(@PathVariable String carId) {
        if (parkingLot.containsCar(carId)) {
            Car car = parkingLot.removeCar(carId);
            double parkingFee = parkingLot.calculateParkingFee(car);
            return ResponseEntity.ok("Car removed successfully. Parking fee: £" + parkingFee);
        } else {
            return ResponseEntity.badRequest().body("Car with ID " + carId + " not found in the parking lot.");
        }
    }
    
    @GetMapping("/all") // API call to view all car parked 
    public List<Car> getAllParkedCars() {
        return parkingLot.getAllParkedCars();
    }
}
