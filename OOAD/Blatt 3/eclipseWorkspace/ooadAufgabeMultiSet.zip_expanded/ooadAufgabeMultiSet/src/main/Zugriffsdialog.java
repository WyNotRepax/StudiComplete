package main;

import com.google.common.collect.Multiset;

import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.HashMultiset;

public class Zugriffsdialog {

	private static class Sammelbild {
		public final String bildserie;
		public final int bildnummer;

		public Sammelbild(String bildserie, int bildnummer) {
			this.bildserie = bildserie;
			this.bildnummer = bildnummer;
		}

		@Override
		public int hashCode() {
			return Objects.hash(bildserie, Integer.valueOf(bildnummer));
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Sammelbild)) {
				return false;
			}
			Sammelbild sammelbild = (Sammelbild) o;
			return sammelbild.bildserie.equals(bildserie) && sammelbild.bildnummer == bildnummer;
		}

		@Override
		public String toString() {
			return String.format("Bild{%s,%d}", bildserie, bildnummer);
		}
	}

	private Multiset<Sammelbild> sammelbilder;

	public Zugriffsdialog() {
		sammelbilder = HashMultiset.create();
	}

	public void nutzungsdialog() {
		int eingabe = -1;
		while (eingabe != 0) {
			System.out.println("---------------\n" + " (0) Programm beenden\n" + " (1) Sammelbild hinzufuegen\n"
					+ " (2) Sammelbilder herausgeben\n" + " (3) Nummern einer Serie zeigen\n"
					+ " (4) Gesamtbestand anzeigen:");
			eingabe = Eingabe.leseInt();
			switch (eingabe) {
			case 1:
				this.sammelbildHinzufuegen();
				break;
			case 2:
				this.sammelbilderHerausgeben();
				break;
			case 3:
				this.serienbilderZeigen();
				break;
			case 4:
				this.gesamtbestand();
				break;
			}
		}
	}

	public void gesamtbestand() {
		System.out.print("Bilder: [");
		System.out.print(
				sammelbilder
				.elementSet()
				.stream()
				.map((bild) -> {
					int count = sammelbilder.count(bild);
					if (count > 1) {
						return String.format("%s x %d", bild.toString(), count);
					} else {
						return bild.toString();
					}
				})
				.collect(Collectors.joining(", "))
				);
		System.out.println("]");
	}

	public void sammelbildHinzufuegen() {
		System.out.print("Welche Serie? ");
		String serie = Eingabe.leseString();
		System.out.print("Welche Nummer? ");
		int nr = Eingabe.leseInt();
		Sammelbild sammelbild = new Sammelbild(serie, nr);
		sammelbilder.add(sammelbild);
	}

	public void sammelbilderHerausgeben() {
		System.out.print("Welche Serie? ");
		String serie = Eingabe.leseString();
		System.out.print("Welche Nummer? ");
		int nr = Eingabe.leseInt();
		int anzahl = 0;
		while (anzahl < 1) {
			System.out.print("Wieviele? ");
			anzahl = Eingabe.leseInt();
		}
		
		Sammelbild sammelbild = new Sammelbild(serie, nr);
		if (sammelbilder.count(sammelbild) >= anzahl) {
			sammelbilder.remove(sammelbild, anzahl);
			System.out.println("Loeschen erfolgreich");
		} else {
			System.out.println("Loeschen nicht erfolgreich");
		}

	}

	public void serienbilderZeigen() {
		System.out.print("Welche Serie? ");
		String serie = Eingabe.leseString();
		
		System.out.print(
				sammelbilder
				.elementSet()
				.stream()
				.filter((bild) -> {
					return bild.bildserie.equals(serie);
				})
				.map((bild) -> {
					int count = sammelbilder.count(bild);
					if (count > 1) {
						return String.format("%s x %d", bild.toString(), count);
					} else {
						return bild.toString();
					}
		}).collect(Collectors.joining(",")));
		System.out.println("]");

	}
	
}
