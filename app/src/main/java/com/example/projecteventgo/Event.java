package com.example.projecteventgo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Event implements Serializable {
    private int id;
    private String name;
    private String artist;
    private String city;
    private String venue;
    private String start_date;
    private String end_date;
    private String pic;
    private String section_pic;
    private List<Section> section;

    public Event(){

    }

    public Event(String name, String artist, String city, String venue, String start_date, String end_date, String pic, String section_pic, ArrayList<Section> section){
        this.name = name;
        this.venue = venue;
        this.city = city;
        this.artist =artist;
        this.start_date = start_date;
        this.end_date = end_date;
        this.pic = pic;
        this.section_pic = section_pic;
        this.section = section;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSection_pic() {
        return section_pic;
    }

    public void setSection_pic(String section_pic) {
        this.section_pic = section_pic;
    }

    public List<Section> getSection() {
        return section;
    }

    public void setSection(List<Section> section) {
        this.section = section;
    }
}
