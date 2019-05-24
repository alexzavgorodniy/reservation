package com.lxzvgrdn.reservations.repository;

import com.lxzvgrdn.reservations.entity.Room;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    Optional<Room> findByNumber(String number);
}