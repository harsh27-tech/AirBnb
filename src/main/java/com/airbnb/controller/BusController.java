package com.airbnb.controller;

import com.airbnb.entity.Bus;
import com.airbnb.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/buses")
public class BusController {

    @Autowired
    private BusRepository busRepository;

    @GetMapping
    public List<Bus> getBusesBetweenStops(
            @RequestParam String fromLocation,
            @RequestParam String toLocation,
           @RequestParam String date
    ) {
        String[] travelDate = date.split("-");
        return busRepository.findBusesFromTo(fromLocation, toLocation, LocalDate.of(Integer.parseInt(travelDate[0]),Integer.parseInt(travelDate[1]),Integer.parseInt(travelDate[2])));
    }
}
