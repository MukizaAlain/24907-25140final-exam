package com.sandra.car_rentals.DTO;

public class ActivityDTO {
    private String type;
    private String message;
    private String time;

    // Default constructor
    public ActivityDTO() {
    }

    // Parameterized constructor
    public ActivityDTO(String type, String message, String time) {
        this.type = type;
        this.message = message;
        this.time = time;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}