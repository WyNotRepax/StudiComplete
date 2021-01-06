package zustand;

public class S3 implements Zustand{

  @Override
  public Zustand a() {
    return new S4();
  }

  @Override
  public Zustand b() {
    return this;
  }

  @Override
	public boolean equals(Object o) {
		return o instanceof S3;
	}
}
