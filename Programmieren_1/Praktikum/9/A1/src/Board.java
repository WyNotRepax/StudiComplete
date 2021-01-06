import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class Board {

	private final int width;
	private final int height;
	private final char mainCar;

	private Field[][] fields;

	Board(int width, int height, char mainCar) {
		this.width = width;
		this.height = height;
		this.mainCar = mainCar;
		fields = new Field[width][height];
		for(int x = 0; x < width;x++){
			for(int y = 0; y < height; y++){
				fields[x][y] = new Field(this,x,y);
			}
		}
	}

	public void placeCar(int x, int y, int length, char c, int direction) {
		new Car(c, length, getField(x, y), direction);
	}

	public Field getField(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return null;
		}
		return fields[x][y];
	}

	@Override
	public String toString() {
		String ret = "";
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (getField(x, y) != null) {
					ret += getField(x, y).current.toString();
				}
				else{
					ret += " ";
				}
			}
			ret += "\n";
		}
		return ret;
	}

}
