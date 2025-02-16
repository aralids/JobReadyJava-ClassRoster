package com.aralids.classroster;

import com.aralids.classroster.ui.*;
import com.aralids.classroster.dao.*;
import com.aralids.classroster.controller.ClassRosterController;

public class App {
	 
	public static void main(String[] args) {
	    UserIO myIo = new UserIOConsoleImpl();
	    ClassRosterView myView = new ClassRosterView(myIo);
	    ClassRosterDao myDao = new ClassRosterDaoFileImpl();
	    ClassRosterController controller =
	            new ClassRosterController(myDao, myView);
	    controller.run();
	} 
}