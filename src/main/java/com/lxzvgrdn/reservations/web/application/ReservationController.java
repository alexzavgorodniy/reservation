package com.lxzvgrdn.reservations.web.application;

import com.lxzvgrdn.reservations.domain.RoomReservation;
import com.lxzvgrdn.reservations.service.ReservationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String getReservations(@RequestParam(value = "date", required = false) String dateString, Model model) {
        List<RoomReservation> roomReservationsList = reservationService.getRoomReservationsForDate(dateString);
        model.addAttribute("roomReservations", roomReservationsList);

        return "reservations";
    }

}