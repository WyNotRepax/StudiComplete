
public class Train {
	Locomotive locomotive;

	Train(Locomotive locomotive) {
		this.locomotive = locomotive;
	}

	void add(Car c) {
		locomotive.add(c);
	}

	void print() {
		System.out.println(this.toString() + "\n" + locomotive.print());
	}
	
	int getPassengers(){
		return locomotive.getAllPassengers();
	}
	
	int getLength() {
		return locomotive.getFullLength();
	}
	
	Car removeFirst() {
		return locomotive.removeFirst();
	}
	
	void relink(Train t) {
		add(t.unlink());
	}
	
	Car unlink(){
		return locomotive.unlink();
	}
	
	Car removeLast() {
		return locomotive.removeLast();
	}
	
	void revert(){
		Train t = new Train(locomotive);
		t.relink(this);
		while(true) {
			Car next = t.removeLast();
			if(next == null) {
				break;
			}
			add(next);
		}
	}
	
	@Override
	public String toString() {
		return "Gesamtkapazität: " + getPassengers() + " Passagiere -- Gesamtlänge: " + getLength() + "m";
	}
}
