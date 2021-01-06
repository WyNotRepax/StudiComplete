import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class A6 {
	public static void main(String[] args) {
		String firstName = IO.readString("Vorname: ");
		String lastName = IO.readString("Nachname: ");
		int tag = IO.readInt("Tag: ");
		int monat = IO.readInt("Monat: ");
		int jahr = IO.readInt("Jahr: ");
		LocalDate bday = LocalDate.of(jahr, monat, tag);
		LocalDate today = LocalDate.now();
		IO.println("Hallo " + firstName + " " + lastName);
		IO.println("Zwischen dem " + bday + " und " + today + " liegen " + ChronoUnit.DAYS.between(bday, today)
				+ " Tage!");
	}
}