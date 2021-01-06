/*
 * Author: Benno Steinkamp
 * Date: 15.11.2020
 */

package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StudyCourse {
	private String name;
	private Map<Integer, Student> students;

	public StudyCourse() {
		name = "";
		students = new HashMap<>();
	}

	public StudyCourse(String name) {
		this();
		this.name = name;
	}

	public boolean addStudent(int matnr, String name) {
		if (students.containsKey(matnr)) {
			return false;
		}
		students.put(matnr, new Student(matnr, name));
		return true;
	}

	public boolean removeStudent(int matnr) {
		return students.remove(matnr) != null;
	}

	public Student findStudent(int matnr) {
		return students.get(matnr);
	}

	@Override
	public String toString() {
		return String.format("Studiengang [name=%s,sudenten=[%s]]", name,
				students.values().stream().map(Student::toString).collect(Collectors.joining(",")));
	}
}
