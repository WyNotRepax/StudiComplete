package entity;

public class Student {
	private int matnr;
	private String name;
	
	public Student(){
		
	}

	public Student(int matnr, String name) {
		super();
		this.matnr = matnr;
		this.name = name;
	}

	public int getMatnr() {
		return matnr;
	}

	public void setMatnr(int matnr) {
		this.matnr = matnr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [matnr=" + matnr + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return matnr;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Student))
			return false;
		Student other = (Student) obj;
		return other.matnr == matnr;
	}
	
}
