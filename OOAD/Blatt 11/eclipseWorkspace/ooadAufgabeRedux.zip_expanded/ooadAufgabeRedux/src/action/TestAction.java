package action;

import entity.TaskList;

public class TestAction extends Action {

	private TaskList testdata;
	TestAction(TaskList testdata){
		this.testdata = testdata;
	}
	public TaskList getTestdata() {
		return testdata;
	}
	public void setTestdata(TaskList testdata) {
		this.testdata = testdata;
	}
	@Override
	public String toString() {
		return "TestAction{}";
	}
}
