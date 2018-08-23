package com.parthhingorani.flyasia.Flights;

//model class for flights
public class Flight {

    public String flightID, airlineURL, airlineName, duration, departureTime, arrivalTime, halt, cost, source, destination, date;

    public Flight(String flightID, String airlineURL, String airlineName, String duration,
                  String departureTime, String arrivalTime, String halt, String cost,
                  String source, String destination, String date)    {
        this.flightID = flightID;
        this.airlineURL = airlineURL;
        this.airlineName = airlineName;
        this.duration = duration;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.halt = halt;
        this.cost = cost;
        this.source = source;
        this.destination = destination;
        this.date = date;
    }
}
