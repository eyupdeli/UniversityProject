package university;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Course {
	
	private Integer id;
	private String title;
	private String teacher;
	private Map<Integer,Student> studentsById = new HashMap<>();
	
	Course(String title, String teacher, Integer id){
		this.title=title;
		this.teacher=teacher;
		this.id=id;	
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getTeacherName() {
		return teacher;
	}
	
	public Integer getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return id + "," + title + "," + teacher;
	}
	
	public Map<Integer, Student> getStudentsById() {
		return studentsById;
	}
	
	public List<Student> getStudents(){
		return studentsById.values().stream().collect(Collectors.toList());
	}
	
	public void addStudent(Student s) {
		studentsById.put(s.getId(), s);
	}
	
	public void addStudents(Collection<Student> students) {
		Map<Integer, Student> studentsMap = students.stream().collect(Collectors.toMap(Student::getId, s -> s));
		this.studentsById.putAll(studentsMap);
	}
	
}
