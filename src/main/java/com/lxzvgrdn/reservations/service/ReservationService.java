package com.lxzvgrdn.reservations.service;

import com.lxzvgrdn.reservations.domain.RoomReservation;
import com.lxzvgrdn.reservations.entity.Guest;
import com.lxzvgrdn.reservations.entity.Reservation;
import com.lxzvgrdn.reservations.entity.Room;
import com.lxzvgrdn.reservations.repository.GuestRepository;
import com.lxzvgrdn.reservations.repository.ReservationRepository;
import com.lxzvgrdn.reservations.repository.RoomRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
            ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(String dateString) {
        Date date = convertDate(dateString);
        Iterable<Room> rooms = roomRepository.findAll();
        HashMap<Long, RoomReservation> map = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();

            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getNumber());

            map.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = reservationRepository.findByDate(new java.sql.Date(date.getTime()));
        if (reservations != null) {
            reservations.forEach(reservation -> {
                Optional<Guest> guestResponse = guestRepository.findById(reservation.getGuestId());

                if (guestResponse.isPresent()) {
                    Guest guest = guestResponse.get();
                    RoomReservation roomReservation = map.get(reservation.getId());

                    roomReservation.setFirstName(guest.getFirstName());
                    roomReservation.setLastName(guest.getLastName());
                    roomReservation.setDate(date);
                    roomReservation.setGuestId(guest.getId());
                }
            });
        }
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long roomId : map.keySet()) {
            roomReservations.add(map.get(roomId));
        }

        return roomReservations;
    }

    private Date convertDate(String dateString) {
        Date date;
        if (dateString != null) {
            try {
                date = DATE_FORMAT.parse(dateString);
            } catch (ParseException pe) {
                date = new Date();
            }
        } else {
            date = new Date();
        }

        return date;
    }
}