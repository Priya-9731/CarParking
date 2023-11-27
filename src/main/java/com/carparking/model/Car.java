package com.carparking.model;

import java.time.LocalDateTime;


	public class Car {
	    private String carId;
	    private LocalDateTime entryTime;
	    private LocalDateTime exitTime;

	    public Car(String carId) {
	        this.carId = carId;
	    }

	    public Car(String carId, LocalDateTime entryTime, LocalDateTime exitTime) {
	        this.carId = carId;
	        this.entryTime = entryTime;
	        this.exitTime = exitTime;
	    }

		public String getCarId() {
			return carId;
		}

		public void setCarId(String carId) {
			this.carId = carId;
		}

		public LocalDateTime getEntryTime() {
			return entryTime;
		}

		public void setEntryTime(LocalDateTime entryTime) {
			this.entryTime = entryTime;
		}

		public LocalDateTime getExitTime() {
			return exitTime;
		}

		public void setExitTime(LocalDateTime exitTime) {
			this.exitTime = exitTime;
		}

}
