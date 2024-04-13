package com.phamvantoan.webBanSachBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "meet")
@Data
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private int meetingID;

    @Column(name = "meeting_content")
    private String meetingContent;

    @Column(name = "meeting_schedule", columnDefinition = "DATE")
    private LocalDate meetingSchedule;

    @Column(name = "meeting_hour", columnDefinition = "TIME")
    private LocalTime meetingHour;

    @Column(name = "location")
    private String Location;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_meet", joinColumns = @JoinColumn(name = "meeting_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList;
}
