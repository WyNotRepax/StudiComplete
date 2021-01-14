package reducer;

import action.Action;
import action.AddAction;
import action.FinishAction;
import action.ProtectAction;
import action.RearrangeAction;
import action.ShowResponsibilitiesAction;
import action.DeleteAction;
import action.TestAction;
import entity.Task;
import state.State;

public class Reducer {

	public State reduce(State state, Action action) {
		this.reduceIntern(state, action);
		return state.clone();
	}

	private void reduceIntern(State state, Action action) {
		if (action instanceof AddAction) {
			state.add(action.getParameter().get(0), action.getParameter().get(1));
			return;
		}

		if (action instanceof DeleteAction) {
			state.delete(((DeleteAction) action).getDeleteId());
			return;
		}

		if (action instanceof TestAction) {
			state.setTaskList(((TestAction) action).getTestdata());
			return;
		}

		if (action instanceof FinishAction) {
			state.getTaskList().getTasks().get(((FinishAction) action).getCompleteId()).setFinished(true);
			return;
		}

		if (action instanceof ShowResponsibilitiesAction) {
			// state.getTaskList().getTasks().values().stream().filter(task->task.getResponsible().equals(((ShowResponsibilitiesAction)action).getResponsible())).forEach(task->System.out.println(task));
			String responsible = ((ShowResponsibilitiesAction) action).getResponsible();
			System.out.println(responsible + " ist responsible fuer:");
			for (Task task : state.getTaskList().getTasks().values()) {
				if (task.getResponsible().equals(responsible)) {
					System.out.println(task);
				}
			}
			System.out.println();

			return;
		}

		if (action instanceof ProtectAction) {
			for (int id : ((ProtectAction) action).getProtectIds()) {
				state.protect(id);
			}
			return;
		}
		
		if(action instanceof RearrangeAction) {
			String currentResponsible = action.getParameter().get(0);
			String newResponsible = action.getParameter().get(1);
			for(int id : state.getTaskList().getTasks().keySet()) {
				if(state.getTaskList().getTasks().get(id).getResponsible().equals(currentResponsible)) {
					state.getTaskList().getTasks().get(id).setResponsible(newResponsible);
				}
			}
			return;
		}

		throw new IllegalArgumentException("Action " + action + " nicht unterstuetzt");
	}
}
