package com.example.projecteventgo;

import java.io.Serializable;

public class Section implements Serializable {
    private String name;
    private int price;
    private String description;
    private String seat_type;
    private int availableTickets;
    private int selectedTickets;


    public Section(String name, int price, String description, String seat_type, int availableTickets){
        this.name = name;
        this.price = price;
        this.description = description;
        this.seat_type = seat_type;
        this.availableTickets =availableTickets;
        this.selectedTickets = 0;
    }

    public Section(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public int getSelectedTickets() {
        return selectedTickets;
    }

    public void setSelectedTickets(int selectedTickets) {
        if (selectedTickets <= availableTickets) {
            this.selectedTickets = selectedTickets;
        } else {
            throw new IllegalArgumentException("Jumlah tiket yang dipilih tidak boleh lebih dari tiket yang tersedia.");
        }
    }

    public int getTotalPrice(){
        return price * selectedTickets;

    }
}
