package com.aralids.classroster;

import com.aralids.classroster.ui.*;
import com.aralids.classroster.dao.*;
import com.aralids.classroster.controller.ClassRosterController;
import com.aralids.classroster.service.*;

public class App {
	 
	public static void main(String[] args) {
	    // Instantiate the UserIO implementation
	    UserIO myIo = new UserIOConsoleImpl();
	    // Instantiate the View and wire the UserIO implementation into it
	    ClassRosterView myView = new ClassRosterView(myIo);
	    // Instantiate the DAO
	    ClassRosterDao myDao = new ClassRosterDaoFileImpl();
	    // Instantiate the Audit DAO
	    ClassRosterAuditDao myAuditDao = new ClassRosterAuditDaoFileImpl();
	    // Instantiate the service layer and wire the DAO and Audit DAO into it
	    ClassRosterServiceLayer myService = new ClassRosterServiceLayerImpl(myDao, myAuditDao);
	    // Instantiate the Controller and wire the service layer into it
	    ClassRosterController controller = new ClassRosterController(myService, myView);
	    // Kick off the Controller
	    controller.run();
	}
}