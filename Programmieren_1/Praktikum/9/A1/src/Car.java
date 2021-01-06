
public class Car {

	public final int direction;

	public final char symbol;
	public final int length;

	private Field[] fields;

	Car(char symbol, int length, Field startField, int direction) {
		this.symbol = symbol;
		this.length = length;

		this.direction = Direction.convert(direction);

		fields = new Field[length];
		Field temp = startField;

		for (int i = 0; i < length; i++) {
			fields[i] = temp;

		}
	}

	public boolean move(int direction) {
		if (Direction.convert(direction) != this.direction) {
			return false;
		}
		int i = 0;
		
		while (fields[i].getRelative(direction).current.equals(this)) {
			i++;
		}
		Field targetField = fields[i].getRelative(direction);
		if(!targetField.isEmpty()){
			return false;
		}
		targetField.current = this;
		while(targetField.getRelative(Direction.opposite(direction)).current.equals(this)){
			targetField = targetField.getRelative(Direction.opposite(direction));
		}
		targetField.current = null;
		return true;
		
		
	}

	public boolean equals(Car c) {
		if(c == null){
			return false;
		}
		if (c.direction == direction && c.symbol == symbol && c.length == length) {
			return true;
		}
		return false;
	}
	
	public String toString(){
		return "" + symbol;
	}

}
