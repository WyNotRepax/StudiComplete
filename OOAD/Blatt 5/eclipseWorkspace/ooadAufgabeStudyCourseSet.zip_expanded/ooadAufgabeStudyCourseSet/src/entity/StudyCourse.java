/*
 * Author: Benno Steinkamp
 * Date: 15.11.2020
 */

package entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StudyCourse {
	private String name;
	private Set<Student> students;

	public StudyCourse() {
		name = "";
		students = new HashSet<>();
	}

	public StudyCourse(String name) {
		this();
		this.name = name;
	}

	public boolean addStudent(int matnr, String name) {
		return students.add(new Student(matnr, name));
	}

	public boolean removeStudent(int matnr) {
		return students.remove(new Student(matnr, ""));
	}

	public Student findStudent(int matnr) {
		for (Student student : students) {
			if (student.getMatnr() == matnr) {
				return student;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("Studiengang [name=%s,sudenten=[%s]]", name,
				students.stream().map(Student::toString).collect(Collectors.joining(",")));
	}
}
