
public class Field {
	public final Field top;
	public final Field bottom;
	public final Field left;
	public final Field right;

	private final Board board;
	
	public Car current;

	Field(Board board, int x, int y) {
		this.board = board;
		top = board.getField(x, y - 1);
		bottom = board.getField(x, y + 1);
		left = board.getField(x - 1, y);
		right = board.getField(x + 1, y);
	}
	
	public Field getRelative(int direction){
		switch (direction) {
		case Direction.UP:
			return top;
		case Direction.DOWN:
			return bottom;
		case Direction.LEFT:
			return left;
		case Direction.RIGHT:
			return right;
		default:
			return null;
		}
	}
	
	public boolean isEmpty(){
		return current == null;
	}
}
