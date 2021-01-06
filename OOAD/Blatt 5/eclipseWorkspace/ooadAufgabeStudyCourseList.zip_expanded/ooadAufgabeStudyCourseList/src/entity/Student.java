package entity;

public class Student {
	private int matnr;
	private String name;

	public Student() {

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
		return String.format("Student [matnr=%d, name=%s]", matnr, name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matnr;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (matnr != other.matnr)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
