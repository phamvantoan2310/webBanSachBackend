package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.entity.Meeting;
import com.phamvantoan.webBanSachBackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createMeetingResponse {
    private Meeting meeting;
    private List<Integer> staffIDs;
}
