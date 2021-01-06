
public class Main {
	public static void main(String[] args) {
		Locomotive bigChief = new Locomotive(23, 5311);
		Train santaFe = new Train(bigChief);
		santaFe.add(new Car(12, 50));
		santaFe.add(new Car(15, 75));
		santaFe.add(new Car(20, 100));
		
		System.out.println(1);
		santaFe.print();

		Locomotive steelHorse = new Locomotive(21, 5409);
		Train rioGrandeExpress = new Train(steelHorse);
		rioGrandeExpress.add(new Car(13, 60));
		rioGrandeExpress.add(new Car(18, 80));

		System.out.println(2);
		rioGrandeExpress.print();

		rioGrandeExpress.relink(santaFe);
		
		System.out.println(3);
		rioGrandeExpress.print();
		
		rioGrandeExpress.revert();
		
		System.out.println(4);
		rioGrandeExpress.print();
	}
}
