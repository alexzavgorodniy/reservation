package com.lxzvgrdn.reservations.web.application;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lxzvgrdn.reservations.domain.RoomReservation;
import com.lxzvgrdn.reservations.service.ReservationService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;
    @Autowired
    private MockMvc mockMvc;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void getReservations() throws Exception {
        String date = "2017-01-01";
        List<RoomReservation> mockReservationList = new ArrayList<>();
        RoomReservation mockRoomReservation = new RoomReservation();
        mockRoomReservation.setLastName("Test");
        mockRoomReservation.setFirstName("JUnit");
        mockRoomReservation.setDate(DATE_FORMAT.parse(date));
        mockRoomReservation.setGuestId(1L);
        mockRoomReservation.setRoomNumber("J1");
        mockRoomReservation.setRoomId(100L);
        mockRoomReservation.setRoomName("JUnit Room");
        mockReservationList.add(mockRoomReservation);

        given(reservationService.getRoomReservationsForDate(date)).willReturn(mockReservationList);
        this.mockMvc.perform(get("/reservations?date=2017-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test, JUnit")));
    }
}