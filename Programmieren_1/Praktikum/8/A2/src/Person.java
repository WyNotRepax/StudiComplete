
public class Person {
	private String firstName;
	private String lastName;

	Person(String firstname, String lastname) {
		this.firstName = firstname;
		this.lastName = lastname;
	}
	
	Person(Person p){
		this.firstName = new String(p.firstName);
		this.lastName = new String(p.lastName);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean equals(Person p) {
		if(p == null){
			return false;
		}
		return (this.firstName.equals(p.firstName) && this.lastName.equals(p.lastName));
	}
}
