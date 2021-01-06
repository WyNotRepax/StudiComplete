import static org.junit.Assert.*;

import org.junit.Test;

public class TaschenrechnerTest {
	Taschenrechner rechner = new Taschenrechner();
	
	@Test
	public void testAddiere() {
		int a = 5;
		int b = 3;
		assertEquals(8,rechner.addiere(a, b));
	}
	
	@Test
	public void testDividiere() {
		int a = 10;
		int b = 5;
		assertEquals(2,rechner.dividiere(a, b));
	}
	
	@Test
	public void testModulo() {
		int a = 8;
		int b = 5;
		assertEquals(3,rechner.modulo(a,b));
	}
	
	@Test
	public void testMultipliziere() {
		int a = 5;
		int b = 3;
		assertEquals(15,rechner.multipliziere(a, b));
	}
	
	@Test
	public void testSubtrahiere() {
		int a = 5;
		int b = 3;
		assertEquals(2,rechner.subtrahiere(a, b));
	}

}
