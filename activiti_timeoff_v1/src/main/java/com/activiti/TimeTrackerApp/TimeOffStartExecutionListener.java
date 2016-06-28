package com.activiti.TimeTrackerApp;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class TimeOffStartExecutionListener implements ExecutionListener { 

	private static final long serialVersionUID = 2657893056856370461L;
	
	@Override 
	public void notify(DelegateExecution execution) throws Exception {		

		Map<String, Object> processVariableMap = execution.getVariables();
		
	}
	
	/*public void signal(String executionId) {
	    CommandExecutor.execute(new SignalCmd(executionId, null, null, null));
	  }
	  
	  public void signal(String executionId, Map<String, Object> processVariables) {
	    commandExecutor.execute(new SignalCmd(executionId, null, null, processVariables));
	  }*/
	
	/*Context.getProcessEngineConfiguration().getEventDispatcher().dispatchEvent(
	          ActivitiEventBuilder.createSignalEvent(ActivitiEventType.ACTIVITY_SIGNALED, signalEventSubscriptionEntity.getActivityId(), signalEventName, 
	              null, signalEventSubscriptionEntity.getExecutionId(), signalEventSubscriptionEntity.getProcessInstanceId(), 
	              signalEventSubscriptionEntity.getProcessDefinitionId()));*/

}
