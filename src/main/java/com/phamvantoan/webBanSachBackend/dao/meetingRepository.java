package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(path = "meetings")
public interface meetingRepository extends JpaRepository<Meeting, Integer> {
    public Meeting findByMeetingID(int meetingID);

    Page<Meeting> findByUserList_UserID(@RequestParam("userID") int userId, Pageable pageable);
}
