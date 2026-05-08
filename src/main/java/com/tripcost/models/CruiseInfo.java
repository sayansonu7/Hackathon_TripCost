package com.tripcost.models;

import java.util.List;

public class CruiseInfo {

    private final String shipName;
    private final String passengers;
    private final String crew;
    private final String launched;
    private final List<String> languages;

    public CruiseInfo(String shipName, String passengers, String crew, String launched, List<String> languages) {
        this.shipName = shipName;
        this.passengers = passengers;
        this.crew = crew;
        this.launched = launched;
        this.languages = languages;
    }

    public String getShipName() {
        return shipName;
    }

    public String getPassengers() {
        return passengers;
    }

    public String getCrew() {
        return crew;
    }

    public String getLaunched() {
        return launched;
    }

    public List<String> getLanguages() {
        return languages;
    }
}