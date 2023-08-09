package com.example.tcs;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SlotService {
    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Slot> allSlots() {
        return slotRepository.findAll();
    }

    public Optional<Slot> singleSlot(ObjectId id) {
        return slotRepository.findById(id);
    }

    public Member editSlot(String date, String startTime, String memberType, ObjectId memberId) {

        System.out.println("Date: " + date + ", Start Time: " + startTime + ", Member Type: " + memberType + ", Member ID: " + memberId);

        Optional<Slot> optionalSlot = slotRepository.findSlotByDateAndStartTime(date, startTime);
        Slot slot = optionalSlot.orElse(null);
        System.out.println("Slot: " + slot);

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElse(null);
        System.out.println("Member: " + member);

        Optional<Facility> optionalFacility = facilityRepository.findById(memberId);
        Facility facility = optionalFacility.orElse(null);
        System.out.println("Facility: " + facility);

        if ("User".equals(memberType) && slot != null && member != null) {
            mongoTemplate.update(Slot.class)
                    .matching(Criteria.where("_id").is(slot.getId()))
                    .apply(new Update().push("userAvail").value(member))
                    .first();
            System.out.println("User added to slot: " + slot.getId());
        } else if ("Facility".equals(memberType) && slot != null && member != null) {
            mongoTemplate.update(Slot.class)
                    .matching(Criteria.where("_id").is(slot.getId()))
                    .apply(new Update().push("facAvail").value(facility))
                    .first();
            System.out.println("Facility added to slot: " + slot.getId());
        }
        return member;

    }

    public Slot createSlot(String date, String time, ObjectId id) {
        Slot slot = new Slot();
        slot.setDate(date);
        slot.setStartTime(time);
        slot.setId(id);

        return slotRepository.insert(slot);
    }
}
