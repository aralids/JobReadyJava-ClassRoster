package com.aralids.classroster.dao;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.aralids.classroster.dto.Student;

public class ClassRosterDaoFileImpl implements ClassRosterDao {
	
	final Map<String, Student> students = new HashMap<>();
	public static final String ROSTER_FILE = "roster.txt";
	public static final String DELIMITER = "::";
	 
	@Override
	public Student addStudent(String studentId, Student student) throws ClassRosterPersistenceException {
		loadRoster();
		Student newStudent = students.put(studentId, student);
		writeRoster();
		return newStudent;
	}

	@Override
	public List<Student> getAllStudents() throws ClassRosterPersistenceException {
		loadRoster();
		return new ArrayList(students.values());
    }

	@Override
	public Student getStudent(String studentId) throws ClassRosterPersistenceException {
		loadRoster();
		return students.get(studentId);
	}

	@Override
	public Student removeStudent(String studentId) throws ClassRosterPersistenceException {
		loadRoster();
		Student removedStudent = students.remove(studentId);
		writeRoster();
		return removedStudent;
	}
    
    private Student unmarshalStudent(String studentAsText){
        // studentAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // 1234::Ada::Lovelace::Java-September1842
        //
        // We then split that line on our DELIMITER - which we are using as ::
        // Leaving us with an array of Strings, stored in studentTokens.
        // Which should look like this:
        // ______________________________________
        // |    |   |        |                  |
        // |1234|Ada|Lovelace|Java-September1842|
        // |    |   |        |                  |
        // --------------------------------------
        //  [0]  [1]    [2]         [3]
        String[] studentTokens = studentAsText.split(DELIMITER);
  
        // Given the pattern above, the student Id is in index 0 of the array.
        String studentId = studentTokens[0];
  
        // Which we can then use to create a new Student object to satisfy
        // the requirements of the Student constructor.
        Student studentFromFile = new Student(studentId);
  
        // However, there are 3 remaining tokens that need to be set into the
        // new student object. Do this manually by using the appropriate setters.
  
        // Index 1 - FirstName
        studentFromFile.setFirstName(studentTokens[1]);
  
        // Index 2 - LastName
        studentFromFile.setLastName(studentTokens[2]);
  
        // Index 3 - Cohort
        studentFromFile.setCohort(studentTokens[3]);
  
        // We have now created a student! Return it!
        return studentFromFile;
    }
    
    private void loadRoster() throws ClassRosterPersistenceException {
        // create an initial scanner
        Scanner scanner = null;
  
        try {
            // Change scanner to read from file
            scanner = new Scanner(new BufferedReader(new FileReader(ROSTER_FILE)));
            // currentLine holds the most recent line read from the file
            String currentLine;
            // currentStudent holds the most recent student unmarshaled
            Student currentStudent;
            // Go through ROSTER_FILE line by line, decoding each line into a
            // Student object by calling the unmarshalStudent method.
            // Process while we have more lines in the file
            while (scanner.hasNextLine()) {
                // get the next line in the file
                currentLine = scanner.nextLine();
                // unmarshal the line into a Student
                currentStudent = unmarshalStudent(currentLine);
  
                // We are going to use the student id as the map key for our
                // student object.
                // Put currentStudent into the map using student id as the key
                students.put(currentStudent.getStudentId(), currentStudent);
            }
        } catch (FileNotFoundException e) {
            throw new ClassRosterPersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }
  
        // close scanner
        finally{
            if (scanner!=null) {
                scanner.close();
            }
        }
    }
    
    private String marshalStudent(Student aStudent){
        // We need to turn a Student object into a line of text for our file.
        // For example, we need an in-memory object to end up like this:
        // 4321::Charles::Babbage::Java-September1842
  
        // It's not a complicated process. Just get out each property,
        // and concatenate with our DELIMITER as a kind of spacer. 
  
        // Start with the student id, since that's supposed to be first.
        String studentAsText = aStudent.getStudentId() + DELIMITER;
  
        // add the rest of the properties in the correct order:
  
        // FirstName
        studentAsText += aStudent.getFirstName() + DELIMITER;
  
        // LastName
        studentAsText += aStudent.getLastName() + DELIMITER;
  
        // Cohort - don't forget to skip the DELIMITER here.
        studentAsText += aStudent.getCohort();
  
        // We have now turned a student to text! Return it!
        return studentAsText;
    }
    
    private void writeRoster() throws ClassRosterPersistenceException {
        // NOTE FOR APPRENTICES: We are not handling the IOException, but
        // we are translating it to an application-specific exception and
        // then simply throwing it (i.e., "reporting" it) to the code that
        // called it.  It is the responsibility of the calling code to
        // handle any errors that occur.
        PrintWriter out = null;
  
        try {
            out = new PrintWriter(new FileWriter(ROSTER_FILE));
            // Write out the Student objects to the roster file.
            // NOTE TO APPRENTICES: We could just grab the student map,
            // get the Collection of Students, and iterate over them but we've
            // already created a method that gets a List of Students, so
            // we'll reuse it.
            String studentAsText;
            List<Student> studentList = new ArrayList(students.values());
            for (Student currentStudent : studentList) {
                // turn a Student into a String
                studentAsText = marshalStudent(currentStudent);
                // write the Student object to the file
                out.println(studentAsText);
                // force PrintWriter to write line to the file
                out.flush();
            }
        } catch (IOException e) {
            throw new ClassRosterPersistenceException(
                    "Could not save student data.", e);
        }
        finally{
            // Clean up
            if (out!=null){
                out.close();
            }
        }
    }

}
