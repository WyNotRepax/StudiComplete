package zustand;

public class S2 implements Zustand {

	@Override
	public Zustand a() {
		return new S3();
	}

	@Override
	public Zustand b() {
		return new S4();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof S2;
	}

}
