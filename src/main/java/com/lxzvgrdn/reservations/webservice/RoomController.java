package com.lxzvgrdn.reservations.webservice;

import com.lxzvgrdn.reservations.entity.Room;
import com.lxzvgrdn.reservations.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    @Autowired
    private RoomRepository repository;

    @GetMapping(value = "/rooms")
    List<Room> getAllRooms(@RequestParam(required = false) String roomNumber) {
        List<Room> rooms = new ArrayList<>();
        if (roomNumber == null) {
            repository.findAll().forEach(rooms::add);
        } else {
            Room byNumber = repository.findByNumber(roomNumber)
                    .orElseThrow();
            rooms.add(byNumber);
        }

        return rooms;
    }
}