package entities;

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
    public int hashCode() {
        return this.matnr;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        if (matnr != other.matnr) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student [matnr=" + matnr + ", name=" + name + "]";
    }

}
