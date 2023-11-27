package com.carparking.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class ParkingLot {
    private static final int MAX_CAPACITY = 100;
    private static final double HOURLY_RATE = 2.0;

   
    private final Map<String, Car> parkedCars = new ConcurrentHashMap<>();

    public boolean isFull() {
        return parkedCars.size() >= MAX_CAPACITY;
    }

    public void parkCar(Car car) {
        if (!isFull()) {
            car.setEntryTime(LocalDateTime.now());
            parkedCars.put(car.getCarId(), car);
            System.out.println("Car parked: " + car.getCarId());
        } else {
            System.out.println("Parking lot is full. Cannot park car: " + car.getCarId());
        }
    }

  
    
    public boolean containsCar(String carId) {
        return parkedCars.containsKey(carId);
    }
    
    public Car removeCar(String carId) {
        return parkedCars.remove(carId);
    }
    
    

    public double calculateParkingFee(Car car) {
        LocalDateTime entryTime = car.getEntryTime();
        LocalDateTime exitTime = LocalDateTime.now();
        Duration duration = Duration.between(entryTime, exitTime);
        long hoursParked = (duration.toMinutes() + 59) / 60; 
        return hoursParked * HOURLY_RATE;
    }
    
    public List<Car> getAllParkedCars() {
        return new ArrayList<>(parkedCars.values());
    }
}
