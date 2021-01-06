import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		/*
		 * B tester = new B(); tester.supertest();
		 */
		/*
		ArrayList<Object> test = new ArrayList<Object>();
		
		test.addAll(null);
		test.equals(test);
		*/
		
		System.out.println(1%0);
	}

}

class A {
	A() {

	}

	void test() {
		System.out.println("A");
	}

	void supertest() {
		A test = (A) this;
		test.test();
	}
}

class B extends A {
	B() {

	}

	@Override
	void test() {
		System.out.println("B");
	}

	@Override
	void supertest() {
		super.supertest();
	}
}