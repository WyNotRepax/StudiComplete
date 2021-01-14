package action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entity.TaskList;

public class ActionFactory {

	// auch mehrere create-Methoden denkbar
	public static Action create(Art command, Object... value) {
		try {
			switch (command) {
			case ADD:
				return createAddAction(value);
			case DELETE:
				return createDeleteAction(value);
			case TESTDATA:
				return createTestdataAction(value);
			case FINISH:
				return createFinishAction(value);
			case SHOW_RESPONSIBILITIES:
				return createShowResponibilitiesAction(value);
			case PROTECT:
				return createProtectAction(value);
			case REARRANGE:
				return createRearrangeAction(value);
			default:
				throw new IllegalArgumentException(
						"Action(" + command + "," + Arrays.asList(value) + ") existiert nicht");
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(
					"Action(" + command + "," + Arrays.asList(value) + ") hat falschen Parametertyp :" + e);
		}
	}

	private static RearrangeAction createRearrangeAction(Object[] params) {
		List<String> tmp = new ArrayList<>();
		for (Object o : params) {
			tmp.add(o.toString());
		}
		if (tmp.size() < 2) {
			throw new IllegalArgumentException("REARRANGE benoetigt zwei Parameter");
		}
		return new RearrangeAction(tmp);
	}

	private static ProtectAction createProtectAction(Object[] params) {
		if (params.length == 0) {
			throw new IllegalArgumentException("PROTECT benoetigt Parameter");
		}
		return new ProtectAction((int[]) params[0]);
	}

	private static Action createShowResponibilitiesAction(Object[] params) {
		if (params.length == 0) {
			throw new IllegalArgumentException("SHOW_RESPONSIBILITIES benoetigt Parameter");
		}
		return new ShowResponsibilitiesAction(params[0].toString());
	}

	private static FinishAction createFinishAction(Object[] params) {
		if (params.length == 0) {
			throw new IllegalArgumentException("FINISH benoetigt Parameter");
		}
		return new FinishAction((Integer) params[0]);
	}

	private static TestAction createTestdataAction(Object[] params) {
		if (params.length != 0) {
			throw new IllegalArgumentException("TESTDATA benoetigt keine Parameter");
		}
		return new TestAction(TaskList.testdaten());
	}

	private static AddAction createAddAction(Object[] params) {

		List<String> tmp = new ArrayList<>();
//      Arrays.stream(value).forEach(v -> tmp.add((String) v));
		for (Object o : params) {
			tmp.add(o.toString());
		}
		if (tmp.size() < 2) {
			throw new IllegalArgumentException("ADD benoetigt zwei Parameter");
		}
		return new AddAction(tmp);
	}

	private static DeleteAction createDeleteAction(Object[] params) {

		if (params.length == 0) {
			throw new IllegalArgumentException("DELETE benoetigt Parameter");
		}
		return new DeleteAction((Integer) params[0]);
	}
}
