
public class Car extends RailVehicle {
	int capacity;


	Car(int length, int capacity) {
		super(length);
		this.capacity = capacity;
	}
	
	Car removeLast() {
		if(next.next == null) {
			Car ret = next;
			next = null;
			return ret;
		}
		else {
			return next.removeLast();
		}
	}

	@Override
	public String toString() {
		return "Kapazität: " + capacity + " Passagiere -- Länge: " + length + "m";
	}
	
	@Override
	int getAllPassengers() {
		if(next == null) {
			return capacity;
		}
		return capacity + next.getAllPassengers();
	}
}
