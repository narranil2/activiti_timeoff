package com.activiti.TimeTrackerApp.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.activiti.TimeTrackerApp.EmployeeTimeOffRequest;
import com.activiti.api.idm.AbstractExternalIdmSourceSyncService;
import com.activiti.domain.sync.ExternalIdmGroup;
import com.activiti.domain.sync.ExternalIdmGroupImpl;
import com.activiti.domain.sync.ExternalIdmQueryResult;
import com.activiti.domain.sync.ExternalIdmQueryResultImpl;
import com.activiti.domain.sync.ExternalIdmUser;
import com.activiti.domain.sync.ExternalIdmUserImpl;


@Component
public class FileSyncService extends AbstractExternalIdmSourceSyncService {
	
	final static Logger logger = Logger.getLogger(EmployeeTimeOffRequest.class);

	@Autowired
	private Environment env;

	@Override
	protected void additionalPostConstruct() {
		// TODO Auto-generated method stub

	}

	@Override
	protected ExternalIdmQueryResult getAllUsersAndGroupsWithResolvedMembers(Long tenantId) {
		try {
			List<ExternalIdmUserImpl> users = readUsers();
			List<ExternalIdmGroupImpl> groups = readGroups(users);
			return new ExternalIdmQueryResultImpl(users, groups);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected List<? extends ExternalIdmGroup> getGroupsModifiedSince(Date arg0, Long arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getIdmType() {
		// TODO Auto-generated method stub
		return "FILE";
	}

	@Override
	protected String getScheduledDifferentialSyncCronExpression() {
		// TODO Auto-generated method stub
		//return "0 0 0 * * ?"; // midnight
		return "0 17 00 ? * *";//Execute at 17:00PM every day
	}

	@Override
	protected String getScheduledFullSyncCronExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getTenantAdminIdentifiers(Long tenantId) {
		// TODO Auto-generated method stub
		return new String[] { "jlennon" };
	}

	@Override
	protected String[] getTenantManagerIdentifiers(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<? extends ExternalIdmUser> getUsersModifiedSince(Date arg0, Long arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isDifferentialSyncEnabled(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isFullSyncEnabled(Long arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	protected List<ExternalIdmUserImpl> readUsers() throws IOException, ParseException {
		
		logger.info("Under read users()::::::");

		List<ExternalIdmUserImpl> users = new ArrayList<ExternalIdmUserImpl>();

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("users.txt");
		
		System.out.println("Input stream data::::"+ inputStream.toString());
		
		logger.info("Under read users::::::" + inputStream.toString());
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = bufferedReader.readLine();
		while (line != null) {

			String[] parsedLine = line.split(";");

			ExternalIdmUserImpl user = new ExternalIdmUserImpl();
			user.setId(parsedLine[0]);
			user.setOriginalSrcId(parsedLine[0]);
			user.setFirstName(parsedLine[1]);
			user.setLastName(parsedLine[2]);
			user.setEmail(parsedLine[3]);
			user.setPassword(parsedLine[4]);
			//TODO
			//user.setLastModifiedTimeStamp(dateFormat.parse(parsedLine[5]));
			System.out.println("Date retrieved for the User::::"+parsedLine[5]);

			users.add(user);
			line = bufferedReader.readLine();
		}

		inputStream.close();
		logger.info("Under readUsers users length::::::" + users.size());
		return users;
	}

	protected List<ExternalIdmGroupImpl> readGroups(List<ExternalIdmUserImpl> users) throws IOException, ParseException {

		logger.info("Under readGroups()::::::");
		
		List<ExternalIdmGroupImpl> groups = new ArrayList<ExternalIdmGroupImpl>();

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("groups.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = bufferedReader.readLine();
		while (line != null) {

			String[] parsedLine = line.split(":");
			String groupId = parsedLine[0];
			System.out.println("Date retrieved for the Group::::"+parsedLine[2]);

			ExternalIdmGroupImpl group = new ExternalIdmGroupImpl();
			group.setOriginalSrcId(groupId);
			group.setName(groupId);

			List<ExternalIdmUserImpl> members = new ArrayList<ExternalIdmUserImpl>();
			String[] memberIds = parsedLine[1].split(";");
			for (String memberId : memberIds) {
				for (ExternalIdmUserImpl user : users) {
					if (user.getId().equals(memberId)) {
						members.add(user);
					}
				}
			}
			group.setUsers(members);
			//TODO
			//group.setLastModifiedTimeStamp(dateFormat.parse(parsedLine[2]));			
			groups.add(group);
			line = bufferedReader.readLine();
		}

		inputStream.close();
		return groups;
	}

}

