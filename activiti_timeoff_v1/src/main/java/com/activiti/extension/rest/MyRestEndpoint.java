package com.activiti.extension.rest;

import com.activiti.domain.idm.User;
import com.activiti.security.SecurityUtils;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/my-rest-endpoint")
public class MyRestEndpoint {

	@Autowired
	private TaskService taskService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public MyRestEndpointResponse executeCustonLogic() {

		User currentUser = SecurityUtils.getCurrentUserObject();
		long taskCount = taskService.createTaskQuery().taskAssignee(String.valueOf(currentUser.getId())).count();

		MyRestEndpointResponse myRestEndpointResponse = new MyRestEndpointResponse();
		myRestEndpointResponse.setFullName(currentUser.getFullName());
		myRestEndpointResponse.setEmail(currentUser.getEmail());
		myRestEndpointResponse.setTaskCount(taskCount);
		return myRestEndpointResponse;

	}

	private static final class MyRestEndpointResponse {

		private String fullName;
		private String email;
		private long taskCount;
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public long getTaskCount() {
			return taskCount;
		}
		public void setTaskCount(long taskCount) {
			this.taskCount = taskCount;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}			

	}
}