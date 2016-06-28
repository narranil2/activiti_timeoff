package com.activiti.TimeTrackerApp.bean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.activiti.TimeTrackerApp.domain.TimeOffRequest;

@Repository
public interface TimeOffRequestRepository  extends JpaRepository<TimeOffRequest, Long>{

}
