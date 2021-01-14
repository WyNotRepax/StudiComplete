package state;

import entity.TaskList;

import java.util.HashSet;
import java.util.Set;

public class State implements Cloneable {

	private TaskList taskList;
	private Set<Integer> protectedSet;

	public State() {
		this.taskList = new TaskList();
		this.protectedSet = new HashSet<>();
	}

	private State(TaskList taskList, Set<Integer> protectedSet) {
		this.taskList = taskList;
		this.protectedSet = protectedSet;
	}

	public TaskList getTaskList() {
		return this.taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

	public void add(String text, String responsible) {
		this.taskList.add(text, responsible);
	}

	public void delete(int id) {
		if (!this.protectedSet.contains(id)) {
			this.taskList.delete(id);
		}
	}

	public void protect(int id) {
		this.protectedSet.add(id);
	}

	@Override
	public State clone() {
		Set clonedMap = new HashSet<>(this.protectedSet);
		State result = new State(this.taskList.clone(), clonedMap);
		return result;
	}
}
