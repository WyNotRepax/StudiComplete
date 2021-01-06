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

  // TODO: eventuell sinnvoll ergaenzen
}
