package com.airbnb.controller;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.Room;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomRepository;
import com.airbnb.service.PDFService;
import com.airbnb.service.SMSService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vi/booking")
public class BookingController {

    private RoomRepository roomRepository;
    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;
    private PDFService pdfService;
    private SMSService smsService;

    public BookingController(RoomRepository roomRepository, PropertyRepository propertyRepository, BookingRepository bookingRepository, PDFService pdfService, SMSService smsService) {
        this.roomRepository = roomRepository;
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.pdfService = pdfService;
        this.smsService = smsService;
    }

    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(
            @RequestParam long propertyId,
            @RequestParam String roomType,
            @RequestBody Booking booking,
            @AuthenticationPrincipal AppUser user
            ){
        Property property = propertyRepository.findById(propertyId).get();
        List<Room> rooms=new ArrayList<>();
        List<LocalDate> datesBetween = getDatesBetween(booking.getCheckInDate(), booking.getCheckOutDate());
      //
      for(LocalDate date:datesBetween) {
          Room room = roomRepository.findByPropertyIdAndTypeAndDate(propertyId, roomType, date);
          if (room.getCount() == 0) {
              //rooms.removeAll(rooms);
              return new ResponseEntity<>("No rooms available", HttpStatus.INTERNAL_SERVER_ERROR);
          }
          rooms.add(room);
      }
      //Booking
        float total=0;
        for (Room room: rooms){
            total=total+room.getPrice();
        }
       booking.setTotal_price(total);
        booking.setProperty(property);
        booking.setAppUser(user);
        booking.setTypeOfRoom(roomType);
        Booking savedBooking = bookingRepository.save(booking);

        //if condition
        if(savedBooking!=null){
            for(Room room:rooms){
                int availableRooms=room.getCount();
                room.setCount(availableRooms-1);
                roomRepository.save(room);
            }
        }
        //Generate pdf document
        pdfService.createPdf(savedBooking);

        //SMS Generation
        smsService.sendSms("+91"+booking.getMobile(),"Your booking is confirmed. Your booking id is : "+booking.getId());

        return new ResponseEntity<>(savedBooking,HttpStatus.CREATED);
    }
    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<LocalDate>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return dates;
    }

}
//
//          else {
//              float nightlyPrice= room.getPrice();
//              float totalPrice=nightlyPrice*booking.getTotal_price();
//              booking.setTotal_price(totalPrice);
//              booking.setProperty(property);
//              booking.setAppUser(user);
//              booking.setTypeOfRoom(roomType);
//
//              Booking savedBooking = bookingRepository.save(booking);
//              if(savedBooking!=null){
//                  int val = room.getCount();
//                  room.setCount(val-1);
//                  roomRepository.save(room);
//              }
//              return new ResponseEntity<>("Room Booked", HttpStatus.CREATED);
//          }
//      }




