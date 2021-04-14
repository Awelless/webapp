package com.epam.web.entity;

public class Room implements Entity {

    private Long id;
    private int numberOfBeds;
    private int cost;
    private int rating;

    public Room(Long id, int numberOfBeds, int cost, int rating) {
        this.id = id;
        this.numberOfBeds = numberOfBeds;
        this.cost = cost;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
