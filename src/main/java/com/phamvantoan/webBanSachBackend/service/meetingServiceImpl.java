package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.meetingRepository;
import com.phamvantoan.webBanSachBackend.entity.Meeting;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class meetingServiceImpl implements meetingService{
    private meetingRepository meetingrepository;
    @Autowired
    public meetingServiceImpl(meetingRepository meetingrepository){
        this.meetingrepository = meetingrepository;
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
}
