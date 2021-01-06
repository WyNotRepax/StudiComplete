
public class Test {
	public static void main(String[] args) {
		Cardinal c = new Cardinal(1);
		c.sub(null);
		Cardinal.sub(c, c);
		c.toString();
		c.equals(c);
	}
}
