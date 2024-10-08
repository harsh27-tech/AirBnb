package com.airbnb.repository;

import com.airbnb.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {

    @Query("SELECT DISTINCT b " +
            "FROM StopOrder o1 " +
            "JOIN o1.bus b " +
            "JOIN o1.stops st1 " +
            "JOIN StopOrder o2 ON b = o2.bus " +
            "JOIN o2.stops st2 " +
            "WHERE st1.stopName = :fromLocation " +
            "AND st2.stopName = :toLocation " +
            //"AND o1.orderNumber < o2.orderNumber " +
            "AND o1.departureDate = :departureDate " +
            "AND (o2.departureDate IS NULL OR o2.departureDate = :departureDate)")
    List<Bus> findBusesFromTo(@Param("fromLocation") String fromLocation,
                                           @Param("toLocation") String toLocation,
                                           @Param("departureDate") LocalDate departureDate);


//    @Query("""
//            SELECT DISTINCT b
//            FROM Bus b
//            JOIN StopOrder s1 ON b.id=s1.bus.id
//            JOIN Stops s1 On S1.stops.id=s1.id
//            JOIN StopOrder s2 On b.id=s2.bus.id
//            JOIN Stops s2 ON s2.stops.id=s2.id
//            WHERE s1.stopName=:fromStopName
//            AND s2.stopName=:toStopName
//            AND s1.departureDate=:departureDate
//            """)
//    List<Bus> findBusesFromTo(@RequestParam ("fromLocation") String fromLocation,
//                                           @RequestParam("toLocation") String toLocation,
//                                           @RequestParam("departureDate") LocalDate departureDate);

}