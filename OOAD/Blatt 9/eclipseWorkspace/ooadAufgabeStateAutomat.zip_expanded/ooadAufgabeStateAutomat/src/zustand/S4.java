package zustand;

public class S4 implements Zustand {

	@Override
	public Zustand a() {
		return this;
	}

	@Override
	public Zustand b() {
		return this;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof S4;
	}
}
