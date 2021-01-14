package action;

import java.util.Arrays;
import java.util.List;

public abstract class Action{

  // generell sollte auf String-Parameter, die auch andere
  // Werte kodieren sollen, aus Typsicherheitsgruenden
  // verzichtet werden
  protected List<String> parameter;
  
  public Action(List<String> parameter) {
    this.parameter = parameter;
  }
  
  public Action(String... par1){
    this(Arrays.asList(par1));
  }

  public List<String> getParameter() {
    return this.parameter;
  }
}
