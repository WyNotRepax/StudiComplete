package entity;

//import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashSet;
//import java.util.List;
import java.util.Set;

public class Mitarbeiter implements Serializable{

	private static final long serialVersionUID = 458076069326042941L;
	private int id;
	private static int idGenerator=100;
	private String vorname;
	private String nachname;
	private Set<Fachgebiet> fachgebiete;
		
	public Mitarbeiter(String vorname, String nachname) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.id = idGenerator++;
		fachgebiete = new HashSet<Fachgebiet>();
	}
	
	public Mitarbeiter(){ // nur fuer Serialisierung
		fachgebiete = new HashSet<Fachgebiet>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	// /*
	public Set<Fachgebiet> getFachgebiete() {
		return fachgebiete;
	}

	public void setFachgebiete(Set<Fachgebiet> fachgebiete) {
		this.fachgebiete = fachgebiete;
	}
	// */
	
	public void addFachgebiet(Fachgebiet f){
		fachgebiete.add(f);
		if(fachgebiete.size()>3){
			fachgebiete.remove(f);
			throw new IllegalArgumentException("Maximal 3 Fachgebiete");
		}
	}
	
	public void removeFachgebiet(Fachgebiet f){
		fachgebiete.remove(f);
	}
	
	public boolean hatFachgebiet(Fachgebiet f){
		return fachgebiete.contains(f);
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Mitarbeiter other = (Mitarbeiter) obj;
		return (id == other.id);
	}
	
	@Override
	public String toString(){
		StringBuilder erg= new StringBuilder(vorname+
				" "+nachname+" ("+id+")[ ");
		for(Fachgebiet f:fachgebiete)
			erg.append(f+" ");
		erg.append("]");
		return erg.toString();
	}
	
}
