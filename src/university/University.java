package university;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	
	private String name;
	private String nameOfRector;
	private String lastNameOfRector;
	private Integer studentRegId = 10000; 
	private Integer courseRegId = 10; 
	
	private Map<Integer, Student> students = new HashMap<>();
	private Map<Integer, Course> courses = new HashMap<>();
	private Map<Integer, List<ExamResult>> results = new HashMap<>();
	

	// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name=name;
	
		// Example of logging
		// logger.info("Creating extended university object");
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		nameOfRector=first;
		lastNameOfRector=last;
	}
	
	/**
	 * Retrieves the rector of the university with the format "First Last"
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		return nameOfRector+ " " +lastNameOfRector;
	}
	
// R2
	/**
	 * Enrol a student in the university
	 * The university assigns ID numbers 
	 * progressively from number 10000.
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		Student s1 = new Student(first, last, studentRegId++);
		students.put(s1.getId(), s1);
	    logger.log(Level.INFO, "Enrolled student: " + last);
		return s1.getId();
	}
	
	/**
	 * Retrieves the information for a given student.
	 * The university assigns IDs progressively starting from 10000
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		return students.get(id).toString();
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * Course codes are assigned progressively starting from 10.
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		Course c1= new Course(title, teacher, courseRegId++);
		courses.put(c1.getId(), c1);
	    logger.log(Level.INFO, "Activated course: " + title); // Log message for course activation
		return c1.getId();
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		return courses.get(code).toString();
	}
	
// R4		
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		courses.get(courseCode).addStudent(students.get(studentID));
		logger.log(Level.INFO, "Registered student " + studentID + " to course " + courseCode); // Log message for registration
		
	}
	
	/**
	 * Retrieve a list of attendees.
	 * 
	 * The students appear one per row (rows end with `'\n'`) 
	 * and each row is formatted as describe in in method {@link #student}
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		Collection<Student> students = courses.get(courseCode).getStudents();
		
		StringBuilder attendees= new StringBuilder();
		
		for (Student student : students) {
			attendees.append(student).append("\n");	
		}
		
		return attendees.toString().trim();
		}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		List<Course> attendedCourses = courses.values().stream()
									.filter(c -> c.getStudentsById().containsKey(studentID))
									.collect(Collectors.toList());
		
		StringBuilder registeredStudents = new StringBuilder();
//		String str = "";
		for (Course course : attendedCourses) {
			registeredStudents.append(course.toString()).append("\n");
//			str += course.toString() + "\n";
		}
		
		return registeredStudents.toString().trim();
	}


// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	
	public void exam(int studentId, int courseID, int grade) {
		ExamResult result = new ExamResult(studentId, courseID, grade);
		
		if(results.get(studentId) == null) {
			results.put(result.getStudentId(), new ArrayList<>());
		}
		
	    results.get(studentId).add(result);	
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	
	public String studentAvg(int studentId) {

		if(results.get(studentId) == null || results.get(studentId).isEmpty()) {
			return "Student " + studentId + " hasn't taken any exams";
		}
		
		double totalGrade = 0;
		double counter = 0;
		
		for (ExamResult result : results.get(studentId)) {
			totalGrade += result.getGrade();			
			counter++;
		}
		
		double avgGrade = totalGrade / counter;
		return "Student " + studentId + " : " + avgGrade;

	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {

		String courseName = courses.get(courseId).getTitle(); 	
		Integer totalGrade = 0;
		Integer count = 0;
		
		for (List<ExamResult> examResults : results.values()) {
			for(ExamResult result : examResults) {
				if(result.getCourseID() == courseId) {
					totalGrade += result.getGrade();
					count++;
				}
			}
		}
		
		if(count == 0) {
			return "No student has taken the exam in " + courseName;
		}
		
		return "The average for the course "+ courseName + " is: " + (float) totalGrade/count;
	}

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	
	 public String topThreeStudents() {	 
		 
	        Map<Integer, Double> studentScores = new HashMap<>();

	        results.keySet().stream()
	        	.forEach(r->{
	        		int numExamsTaken = results.get(r).size();
	        		
	        		if(numExamsTaken == 0)
	        			return;
	        		
	        		double avg = results.get(r)
	        				.stream()
	        				.mapToDouble(ExamResult::getGrade)
	        				.sum()/numExamsTaken;
	        		
	        		long numCourses = courses.values().stream()
	        				.filter(courses -> courses.getStudentsById().containsKey(r))
	        				.count();
	        		
	        		double bonus = numExamsTaken / numCourses *10;
	        	
	        		studentScores.put(r, avg+bonus);
	        	
	        });
	        
	        List<Integer> topStudents = studentScores.keySet().stream()
	        		.sorted((student1, student2) -> studentScores.get(student2).compareTo(studentScores.get(student1)))
	        		.limit(3)
	        		.collect(Collectors.toList());
	        
	        
	        StringBuilder result = new StringBuilder();
	        
	        for (Integer studentId: topStudents) {
	        	Student student = students.get(studentId);
	        	double score = studentScores.get(studentId);
	        	
	            result.append(student.getName()).append(" ")
	            	  .append(student.getSurname()).append(" : ")
	            	  .append(score).append("\n");
	        }
	        	
	        return result.toString().trim();
	 
	 }
	 
// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
	 public static final Logger logger = Logger.getLogger("University");
    
    

}
