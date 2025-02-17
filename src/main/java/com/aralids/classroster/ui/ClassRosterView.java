package com.aralids.classroster.ui;

import java.util.List;

import com.aralids.classroster.dto.Student;

public class ClassRosterView {
	 
	final UserIO io;
	
	public ClassRosterView(UserIO io) {
	    this.io = io;
	}

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. List Students");
        io.print("2. Create New Student");
        io.print("3. View a Student");
        io.print("4. Remove a Student");
        io.print("5. Exit");

        return io.readInt("Please select from the above choices.");
    }
    
    public Student getNewStudentInfo() {
        Student currentStudent = new Student(io.readString("Please enter Student ID"));
        currentStudent.setFirstName(io.readString("Please enter First Name"));
        currentStudent.setLastName(io.readString("Please enter Last Name"));
        currentStudent.setCohort(io.readString("Please enter Cohort"));
        return currentStudent;
    }
    
    public void displayCreateStudentBanner() {
        io.print("=== Create Student ===");
    }
  
    public void displayCreateSuccessBanner() {
        io.readString("Student successfully created.  Please hit enter to continue");
    }
    
    public void displayStudentList(List<Student> studentList) {
        for (Student currentStudent : studentList) {
            String studentInfo = String.format("#%s : %s %s %s",
                    currentStudent.getStudentId(),
                    currentStudent.getFirstName(),
                    currentStudent.getLastName(),
                    currentStudent.getCohort());
            io.print(studentInfo);
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayDisplayAllBanner() {
        io.print("=== Display All Students ===");
    }
    
    public void displayDisplayStudentBanner () {
        io.print("=== Display Student ===");
    }
  
    public String getStudentIdChoice() {
        return io.readString("Please enter the Student ID.");
    }
  
    public void displayStudent(Student student) {
        if (student != null) {
            io.print(student.getStudentId());
            io.print(student.getFirstName() + " " + student.getLastName());
            io.print(student.getCohort());
            io.print("");
        } else {
            io.print("No such student.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayRemoveStudentBanner () {
        io.print("=== Remove Student ===");
    }
  
    public void displayRemoveResult(Student studentRecord) {
        if(studentRecord != null){
          io.print("Student successfully removed.");
        }else{
          io.print("No such student.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
  
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

	public void displayRemoveSuccessBanner() {
		io.print("Student removed successfully!!!");
		
	}
}