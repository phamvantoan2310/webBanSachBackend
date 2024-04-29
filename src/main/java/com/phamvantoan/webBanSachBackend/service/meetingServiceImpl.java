package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.meetingRepository;
import com.phamvantoan.webBanSachBackend.entity.Meeting;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class meetingServiceImpl implements meetingService{
    private meetingRepository meetingrepository;
    private userService userservice;
    @Autowired
    public meetingServiceImpl(meetingRepository meetingrepository ,@Lazy userService userservice){
        this.meetingrepository = meetingrepository;
        this.userservice = userservice;
    }
    @Override
    public Meeting findByMeetingID(int meetingID) {
        return this.meetingrepository.findByMeetingID(meetingID);
    }

    @Override
    public ResponseEntity<?> deleteMeeting(int meetingID) {
        Meeting meeting = findByMeetingID(meetingID);

        Iterator<User> iterator = meeting.getUserList().iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            user.getMeetingList().remove(meeting);
            iterator.remove();
        }
        this.meetingrepository.delete(meeting);

        return ResponseEntity.ok("Xóa cuộc họp thành công");
    }

    @Override
    public ResponseEntity<?> createMeeting(Meeting meeting, List<Integer> staffIDs) {
        List<User> users = new ArrayList<>();

        for(int id : staffIDs){
            users.add(this.userservice.findByUserID(id));
        }

        meeting.setUserList(users);


        this.meetingrepository.save(meeting);
        return ResponseEntity.ok("Tạo cuộc họp thành công");
    }

    @Override
    public ResponseEntity<?> cancelMeeting(int meetingID) {
        try {
            Meeting meeting = this.meetingrepository.findByMeetingID(meetingID);

            Iterator<User> iterator = meeting.getUserList().iterator();
            while (iterator.hasNext()){
                User user = iterator.next();
                user.getMeetingList().remove(meeting);
                iterator.remove();
            }

            this.meetingrepository.delete(meeting);
            return ResponseEntity.ok("Hủy cuộc họp thành công");
        }catch (Exception e){
            throw e;
        }
    }
}
