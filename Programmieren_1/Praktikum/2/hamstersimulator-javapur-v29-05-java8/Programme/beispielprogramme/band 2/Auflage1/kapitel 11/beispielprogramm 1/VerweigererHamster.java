import de.hamster.debugger.model.Territorium;import de.hamster.model.HamsterException;import de.hamster.model.HamsterInitialisierungsException;import de.hamster.model.HamsterNichtInitialisiertException;import de.hamster.model.KachelLeerException;import de.hamster.model.MauerDaException;import de.hamster.model.MaulLeerException;import de.hamster.debugger.model.Hamster;class VerweigererHamster extends SoldatenHamster {
  VerweigererHamster(int r, int s, int b, int k) {
    super(r, s, b, k);
  }
  
  // VerweigererHamster tut gar nichts
  
  public void linksUm() { }
  public void vor() { }
  void kehrt() { }
  void rechtsUm() { }
}
