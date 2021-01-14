package action;

import java.util.Arrays;
import java.util.List;

public class AddAction extends Action{
  public AddAction(String... par1){
    super(Arrays.asList(par1));
  }

  public AddAction(List<String> list) {
    super(list);
  }

  @Override
  public String toString() {
    return "AddAction{parameter=" + super.parameter + '}';
  } 
}
