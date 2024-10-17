package university;

public class Student {
	
	private Integer id;
	private String name;
	private String surname;
	
	Student(String name, String surname, Integer id){
		this.name=name;
		this.surname=surname;
		this.id=id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return id + " " + name + " " + surname;
	}
	
}



	
