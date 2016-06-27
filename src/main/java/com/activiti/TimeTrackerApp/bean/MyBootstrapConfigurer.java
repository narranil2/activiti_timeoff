package com.activiti.TimeTrackerApp.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.activiti.api.boot.BootstrapConfigurer;

@Component
public class MyBootstrapConfigurer implements BootstrapConfigurer {

  @Autowired
  private FileSyncService fileSyncService;

  public void applicationContextInitialized(org.springframework.context.ApplicationContext applicationContext) {
    fileSyncService.asyncExecuteFullSynchronizationIfNeeded(null);
  }

}
