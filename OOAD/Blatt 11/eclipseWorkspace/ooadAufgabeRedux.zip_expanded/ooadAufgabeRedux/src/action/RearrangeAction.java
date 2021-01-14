package action;

import java.util.Arrays;
import java.util.List;

public class RearrangeAction extends Action {
	  public RearrangeAction(String... par1){
		    super(Arrays.asList(par1));
		  }

		  public RearrangeAction(List<String> list) {
		    super(list);
		  }

		  @Override
		  public String toString() {
		    return "AddAction{parameter=" + super.parameter + '}';
		  } 
}
