package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.meetingRepository;
import com.phamvantoan.webBanSachBackend.entity.Meeting;
import org.springframework.http.ResponseEntity;

public interface meetingService {
    public Meeting findByMeetingID(int meetingID);
    public ResponseEntity<?> deleteMeeting(int meetingID);
}
