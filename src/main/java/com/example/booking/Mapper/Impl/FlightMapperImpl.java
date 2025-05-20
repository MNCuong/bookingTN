package com.example.booking.Mapper.Impl;

import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Entity.Flight;
import com.example.booking.Mapper.FlightMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlightMapperImpl implements FlightMapper {

    @Override
    public FlightResponse toFlightResponse(Flight flight) {
        if ( flight == null ) {
            return null;
        }

//        return FlightResponse.builder()
//                .id(flight.getId())
//                .airline(flight.getAirlines())
//                .arrivalAirport(flight.getArrival())
//                .arrivalTime(flight.getArrival().getScheduled())
//                .departureAirport(flight.getDeparture())
//                .departureTime(flight.getDeparture().getScheduled())
//                .flightDetails(flight.getFlightDetails())
//                .aircraft(flight.getAircraft())
//                .priceBusiness(flight.getPriceBusiness())
//                .priceEconomy(flight.getPriceEconomy())
//                .build();
        return null;
    }

    @Override
    public List<FlightResponse> toFlightResponseList(List<Flight> flights) {
        if ( flights == null ) {
            return null;
        }

        List<FlightResponse> list = new ArrayList<FlightResponse>( flights.size() );
        for ( Flight flight : flights ) {

            list.add( toFlightResponse( flight ) );
        }

        return list;
    }
}
