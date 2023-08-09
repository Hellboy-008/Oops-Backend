package com.example.tcs;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SlotRepository extends MongoRepository<Slot, ObjectId> {

    Optional<Slot> findSlotByDateAndStartTime(String date, String startTime);

    List<Slot> findSlotsByDate(String date);
    Optional<Slot> findSlotByStartTime(String startTime);
}
