package entity;

public class Projektwhiteboard extends Whiteboard implements Whiteboardobserver {

	private String projektname;
	
	public Projektwhiteboard(String projektname){
		super();
		this.projektname = projektname;
	}
	
	@Override
	public void update(String nachricht) {
		this.observerInformieren(nachricht);
	}

	public void neueInformation(String information) {
		this.observerInformieren(information);
	}
	
	@Override
	public String toString() {
		return String.format("Projekt %s: [%s]",this.projektname,super.toString());
	}
}
