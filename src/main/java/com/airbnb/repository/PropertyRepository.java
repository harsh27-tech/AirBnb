package com.airbnb.repository;

import com.airbnb.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    // One way to join 2 table

//    @Query("select p from Property p JOIN City c on p.city=c.id where c.name=:cityName")
//    List<Property> searchProperty(
//            @Param("cityName") String cityName
//    );

    //Second way to join 2 table

//    @Query("select p from Property p JOIN p.city c where c.name=:cityName")
//    List<Property> searchProperty(
//            @Param("cityName") String cityName
//    );

    //If we wann join 3 table

    @Query("select p from Property p JOIN p.city c JOIN p.country co where c.name=:name or co.name=:name")
    List<Property> searchProperty(
            @Param("name") String name
    );
}