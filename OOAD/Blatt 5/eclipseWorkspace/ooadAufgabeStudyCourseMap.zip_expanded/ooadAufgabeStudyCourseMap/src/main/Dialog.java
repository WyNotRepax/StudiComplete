package main;

import entity.Student;
import entity.StudyCourse;

public class Dialog {
	
	private StudyCourse course = new StudyCourse("Informatik");
		
	public Dialog(){
           // control();
	}

    public void control() {
        int eingabe = -1;
        while (eingabe != 0){
            System.out.println(
                    "\n(0) terminate\n"
                            + "(1) add new student\n"
                            + "(2) remove student\n"
                            + "(3) search student\n"
                            + "(4) show course of Studies"
            );
            eingabe = Eingabe.leseInt();
            switch (eingabe){
                case 1: {
                    newStudent();
                    break;
                }
                case 2: {
                    removeStudent();
                    break;
                }
                case 3: {
                    searchStudent();
                    break;
                }
                case 4: {
                    showCourse();
                    break;
                }
            }
        }
    }

    public void showCourse() {
        System.out.println(""+this.course);
    }

    public void searchStudent() {
        System.out.print("matriculation number: ");
        int matnr = Eingabe.leseInt();
        Student tmp = this.course.findStudent(matnr);
        if (tmp == null){
            System.out.println("student not found");
        } else {
            System.out.println(""+tmp);
        }
    }

    public void removeStudent() {
        System.out.print("matriculation number: ");
        int matnr = Eingabe.leseInt();
        if (this.course.removeStudent(matnr)){
            System.out.println("student removed");
        } else {
            System.out.println("student not removed");
        }
    }

    public void newStudent() {
        System.out.print("matriculation number: ");
        int matnr = Eingabe.leseInt();
        System.out.print("name: ");
        String name = Eingabe.leseString();
        if (this.course.addStudent(matnr, name)){
            System.out.println("student added");
        } else {
            System.out.println("student not added");
        }
    }
}
