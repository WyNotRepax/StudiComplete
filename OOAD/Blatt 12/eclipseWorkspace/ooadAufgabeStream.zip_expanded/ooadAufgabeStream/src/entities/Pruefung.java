package entities;

public class Pruefung {

    private Modul modul;
    private Student student;
    private int note;
    private int versuch;

    public Pruefung() {

    }

    public Pruefung(Modul modul, Student student, int note, int versuch) {
        super();
        this.modul = modul;
        this.student = student;
        this.note = note;
        this.versuch = versuch;
    }

    public Modul getModul() {
        return modul;
    }

    public void setModul(Modul modul) {
        this.modul = modul;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getVersuch() {
        return versuch;
    }

    public void setVersuch(int versuch) {
        this.versuch = versuch;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((modul == null) ? 0 : modul.hashCode());
        result = prime * result + note;
        result = prime * result + ((student == null) ? 0 : student.hashCode());
        result = prime * result + versuch;
        return result;
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
        Pruefung other = (Pruefung) obj;
        if (modul == null) {
            if (other.modul != null) {
                return false;
            }
        } else if (!modul.equals(other.modul)) {
            return false;
        }
        if (note != other.note) {
            return false;
        }
        if (student == null) {
            if (other.student != null) {
                return false;
            }
        } else if (!student.equals(other.student)) {
            return false;
        }
        if (versuch != other.versuch) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pruefung [modul=" + modul + ", student=" + student + ", note="
                + note + ", versuch=" + versuch + "]";
    }

}
