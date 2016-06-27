package com.activiti.TimeTrackerApp.bean;

import com.activiti.TimeTrackerApp.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @Query("SELECT e FROM Employee e WHERE LOWER(e.userName) = LOWER(:userName)")
  Employee findByUserName(@Param("userName") String userName);

  @Modifying
  @Query("update Employee e set e.total_vacation_avail_days = :leavesToBeUpdated where e.userName = :userName")
  Integer updateVacationDetailsForEmployee(@Param("userName") String userName, @Param("leavesToBeUpdated") int leavesToBeUpdated);
  
 }


