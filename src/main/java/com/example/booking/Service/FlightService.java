package com.example.booking.Service;


public interface FlightService {
    //    Flight findById(Long id);
//    void save(Flight flight);
//    FlightResponse addFlight(FlightRequest flightRequest);
//    List<FlightResponse> getFlights(String lat, String lon);

    String searchFlights(String depIata, String arrIata);

    Object convertToJson(String jsonString);

//    int getAvailableSeats(String flightCode);

}
