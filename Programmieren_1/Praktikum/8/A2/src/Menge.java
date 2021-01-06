public class Menge {
	private Person[] persons;

	Menge() {
		persons = new Person[0];
	}

	Menge(Menge m) {
		for (Person p : m.persons) {
			this.addPerson(new Person(p));
		}
	}

	public void addPerson(Person p) {
		if(p != null){
			if (!this.contains(p)) {
				Person[] temp = new Person[persons.length + 1];
				for (int i = 0; i < persons.length; i++) {
					temp[i] = persons[i];
				}
				temp[persons.length] = p;
				persons = temp;
			}
		}
	}

	public boolean contains(Person p) {
		if(p == null){
			return false;
		}
		for (Person person : this.persons) {
			if (person.equals(p)) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(String firstName, String lastName) {
		return this.contains(new Person(firstName, lastName));
	}

	public void removePerson(Person p) {
		if (this.contains(p)) {
			Person[] temp = new Person[persons.length - 1];
			int n = 0;
			for (Person person : persons) {
				if (!person.equals(p)) {
					temp[n] = person;
					n++;
				}
			}
		}
	}

	public boolean equals(Menge m) {
		if(m == null){
			return false;
		}
		for (Person p : m.persons) {
			if (!this.contains(p)) {
				return false;
			}
		}
		for (Person p : this.persons) {
			if (!m.contains(p)) {
				return false;
			}
		}
		return true;
	}

	public Menge intersect(Menge m) {
		Menge ret = new Menge();
		for (Person p : this.persons) {
			if (m.contains(p)) {
				ret.addPerson(new Person(p));
			}
		}
		return ret;
	}

	public Menge removed(Menge m) {
		Menge ret = new Menge();
		for (Person p : this.persons) {
			if (!m.contains(p)) {
				ret.addPerson(p);
			}
		}
		return ret;
	}

	public Menge union(Menge m) {
		Menge ret = new Menge(m);
		for (Person p : this.persons) {
			ret.addPerson(p);
		}
		return ret;
	}
}
