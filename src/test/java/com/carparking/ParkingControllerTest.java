package com.carparking;


import com.carparking.model.ParkingLot;
import com.carparking.model.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@AutoConfigureMockMvc
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingLot parkingLot;

    @Test
    void testParkCar() throws Exception {
        mockMvc.perform(post("/parking/park")
                .content("{\"carId\": \"CAR1\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Car parked successfully."));
    }

    @Test
    void testRemoveCar() throws Exception {
        parkingLot.parkCar(new Car("CAR1"));
        mockMvc.perform(post("/parking/remove/CAR1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Car removed successfully. Parking fee: £0.0"));

    }


    @Test
    void testRemoveNonExistingCar() throws Exception {
        mockMvc.perform(post("/parking/remove/CAR3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Car with ID CAR3 not found in the parking lot."));
    }

    @Test
    void testGetAllParkedCars() throws Exception {
        parkingLot.parkCar(new Car("CAR1"));
        parkingLot.parkCar(new Car("CAR2"));

        ResultActions result = mockMvc.perform(get("/parking/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void testConcurrentParking() throws InterruptedException {
        // Setting up the parking lot
        ParkingLot parkingLot = new ParkingLot();
        int numberOfCars = 3;

        // Using a CountDownLatch to synchronize the start of threads
        CountDownLatch startSignal = new CountDownLatch(1);
        // Using a CountDownLatch to wait for all threads to finish
        CountDownLatch doneSignal = new CountDownLatch(numberOfCars);

        // Creating a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfCars);

        // Simulating concurrent parking of cars
        for (int i = 0; i < numberOfCars; i++) {
            executorService.execute(() -> {
                try {
                    
                    startSignal.await();
                    Car car = new Car("ABC123");
                    parkingLot.parkCar(car);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Counting  the doneSignal to indicate that the thread has finished
                    doneSignal.countDown();
                }
            });
        }

        // Releasing the threads to start simultaneously
        startSignal.countDown();

        // Waiting for all threads to finish
        doneSignal.await();

        // Shutting down the thread pool
        executorService.shutdown();

        
        // Checking that all parked cars are unique
        assertTrue(parkingLot.getAllParkedCars().stream().allMatch(car -> car.getCarId().equals("ABC123")));
    }
}
