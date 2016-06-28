package com.activiti.TimeTrackerApp.bean;

import com.activiti.TimeTrackerApp.domain.Employee;
import com.activiti.TimeTrackerApp.domain.TimeOffRequest;

import java.util.List;

public interface TimeOffAppService {

  void createDemoEmployees();

  void createEmployee(Employee aEmployee);

  void delete(int id);

  List<Employee> findAll();

  Employee findEmpByUserName(String userName);

  int updateVacationDetailsForEmployee(int leavesToBeUpdated, String userName);
  
  Employee findById(Long id);
  
  void insertTimeOffForEmployee(TimeOffRequest aTimeOffRequest);

}
