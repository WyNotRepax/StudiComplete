
public class Locomotive extends RailVehicle{
	int type;
	
	
	public Locomotive(int length, int type) {
		super(length);
		this.type = type;
	}
	
	Car removeFirst() {
		if(next == null) {
			return null;
		}
		Car ret = next;
		next = next.next;
		return ret;
	}
	
	Car removeLast() {
		if(next == null) {
			return null;
		}
		else if(next.next == null){
			Car ret = next;
			next = null;
			return ret;
		}
		else{
			return next.removeLast();
		}
	}
	
	Car unlink(){
		Car ret = next;
		next = null;
		return ret;
	}
	
	@Override
	public String toString() {
		return "Modell: " + type + " -- Länge: " + length + "m";
	}
}
