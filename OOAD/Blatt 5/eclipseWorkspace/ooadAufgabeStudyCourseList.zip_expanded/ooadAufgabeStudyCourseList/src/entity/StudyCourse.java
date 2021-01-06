/*
 * Author: Benno Steinkamp
 * Date: 15.11.2020
 */
package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudyCourse {
	private String name;
	private List<Student> students;

	public StudyCourse() {
		name = "";
		students = new ArrayList<>();
	}

	public StudyCourse(String name) {
		this();
		this.name = name;
	}

	public boolean addStudent(int matnr, String name) {
		for (Student student : students) {
			if (student.getMatnr() == matnr) {
				return false;
			}
		}
		students.add(new Student(matnr, name));
		return true;
	}

	public boolean removeStudent(int matnr) {
		for (Student student : students) {
			if (student.getMatnr() == matnr) {
				students.remove(student);
				return true;
			}
		}
		return false;
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
