package store;

import action.Action;
import action.AddAction;
import action.RearrangeAction;
import util.User;

public class AllowedStore extends AbstractDecoratorStore {

	public AllowedStore(StoreInterface store) {
		super(store);
	}

	@Override
	public void dispatch(Action action) {
		if(action instanceof AddAction) {
			if(!User.allowed(((AddAction)action).getParameter().get(1))) {
				return;
			}
		}
		else if(action instanceof RearrangeAction) {
			String currentResponsible = action.getParameter().get(0);
			String newResponsible = action.getParameter().get(1);
			if(!User.allowed(currentResponsible) || !User.allowed(newResponsible)) {
				System.out.println("Name nicht erlaubt!");
				return;
			}
		}
		super.dispatch(action);
	}
}
