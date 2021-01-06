
public class Car {
	private final char symbol;
	private final int direction;
	Car(char symbol,int direction){
		this.symbol = symbol;
		this.direction = direction;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public boolean facing(int d) {
		switch(d) {
		case Direction.NORTH:
		case Direction.SOUTH:
			return (direction == Direction.NORTH || direction == Direction.SOUTH);
		case Direction.EAST:
		case Direction.WEST:
			return (direction == Direction.EAST || direction == Direction.WEST);
		}
		return false;
	}
	
	public boolean equals(Car c) {
		return equals(c.symbol);
	}
	
	public boolean equals(char c) {
		return c == this.symbol;
	}
}
