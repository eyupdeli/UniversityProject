package university;

public class ExamResult {
	
	private Integer studentId; 
	private Integer courseID;
	private Integer grade;
	
	public ExamResult (Integer studentId, Integer courseID, Integer grade){
		this.studentId = studentId;
		this.courseID = courseID;
		this.grade = grade;
	}

	public Integer getGrade() {
		return grade;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public Integer getCourseID() {
		return courseID;
	}
	
}
