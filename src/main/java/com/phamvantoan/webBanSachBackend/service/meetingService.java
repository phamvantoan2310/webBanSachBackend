package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.meetingRepository;
import com.phamvantoan.webBanSachBackend.entity.Meeting;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface meetingService {
    public Meeting findByMeetingID(int meetingID);
    public ResponseEntity<?> deleteMeeting(int meetingID);
    public ResponseEntity<?> createMeeting(Meeting meeting, List<Integer> staffs);
    public ResponseEntity<?> cancelMeeting(int meetingID);
}
