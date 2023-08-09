package com.example.tcs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/slots")
public class SlotController {
    @Autowired
    private SlotService slotService;
    @GetMapping
    public ResponseEntity<List<Slot>> getAllSlots() {
        return new ResponseEntity<>(slotService.allSlots(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Slot>> getSingleSlot(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Slot>>(slotService.singleSlot(id), HttpStatus.OK);
    }

    @PostMapping("/editSlot")
    public ResponseEntity<Member> editSlot(@RequestBody SlotRequest request) {
        String date = request.getDate();
        String startTime = request.getStartTime();
        String memberType = request.getMemberType();
        ObjectId memberId = request.getMemberId();
        return new ResponseEntity<Member>(slotService.editSlot(date, startTime, memberType, memberId), HttpStatus.CREATED);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SlotRequest {
        private String date;
        private String startTime;
        private String memberType;
        private ObjectId memberId;

        // getters and setters
    }


    @PostMapping("/createSlot")
    public ResponseEntity<Slot> createSlot(@RequestBody SlotRequest request) {
        String date = request.getDate();
        String startTime = request.getStartTime();
        ObjectId id = new ObjectId();

        return new ResponseEntity<Slot>(slotService.createSlot(date, startTime, id), HttpStatus.CREATED);
    }


}
