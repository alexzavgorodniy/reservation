package com.lxzvgrdn.reservations.web.application.service;

import com.lxzvgrdn.reservations.domain.RoomReservation;
import com.lxzvgrdn.reservations.service.ReservationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ReservationServiceController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping(value = "/reservations/{date}")
    public List<RoomReservation> getAllReservationForDate(@PathVariable(value = "date") String dateString) {
        return reservationService.getRoomReservationsForDate(dateString);
    }
}