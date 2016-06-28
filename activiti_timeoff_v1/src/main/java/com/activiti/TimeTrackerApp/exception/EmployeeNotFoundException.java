package com.activiti.TimeTrackerApp.exception;

public class EmployeeNotFoundException  extends Exception{

	private static final long serialVersionUID = -2583619594343009509L;

	public EmployeeNotFoundException()
	{

	}

	public EmployeeNotFoundException(String message)
	{
		super(message);
	}

	public EmployeeNotFoundException(Throwable cause)
	{
		super(cause);

	}

	public EmployeeNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}	

}
