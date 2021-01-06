
public class World {
	private int size;
	private boolean[][] world;
	private Ant ant;
	
	public World(int size) {
		this.size = size;
		this.world = new boolean[size][size];
		this.ant = new Ant(size/2,size/2,this);
	}
	
	public int getSize(){
		return this.size;
	}
	
	public boolean isCellBlack(int row, int column){
		return world[row][column];
	}
	public void black(int row, int column){
		//System.out.println(row + " " + column + " SCHWARZ");
		world[row][column] = true;
	}
	public void white(int row, int column){
		world[row][column] = false;
	}
	
	public Ant getAnt(){
		return ant;
	}
}
