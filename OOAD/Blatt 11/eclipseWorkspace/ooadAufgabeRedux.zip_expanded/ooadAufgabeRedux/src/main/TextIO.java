package main;

import action.Action;
import action.ActionFactory;
import action.Art;
import entity.Task;
import entity.TaskList;
import reducer.Reducer;
import state.State;
import store.AllowedStore;
import store.Store;
import store.StoreInterface;
import store.Subscriber;
import store.TimerStore;

public class TextIO {

	private StoreInterface store = new AllowedStore(new TimerStore(new Store(new State(), new Reducer())));

	public TextIO() {
//    this.store.subscribe(newState -> {
//      System.out.println(newState.getTaskList());
//    });

		this.store.subscribe(new Subscriber() {
			@Override
			public void onChange(State s) {
				System.out.println(s.getTaskList());
			}
		});

	}

	public void dialog() {
		int eingabe = -1;
		while (eingabe != 0) {
			System.out.print("" + "(0) beenden\n" + "(1) Task hinzu\n" + "(2) Task loeschen\n"
					+ "(3) Testdaten einspielen \n" + "(4) Task abschliessen\n" + "(5) Task eines Bearbeiters\n"
					+ "(6) Tasks schuetzen \n" + "(7) Tasks neuzuordnen \n");
			eingabe = Eingabe.leseInt();
			try {
				switch (eingabe) {
				case 1: {
					this.newTask();
					break;
				}
				case 2: {
					this.deleteTask();
					break;
				}
				case 3: {
					this.testData();
					break;
				}
				case 4: {
					this.finishTask();
					break;
				}
				case 5: {
					this.showResponsibilities();
					break;
				}
				case 6: {
					this.protectTasks();
					break;
				}
				case 7: {
					this.rearrangeTasks();
					break;
				}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void deleteTask() {
		System.out.print("welche Id: ");
		int id = Eingabe.leseInt();
		Action action = ActionFactory.create(Art.DELETE, id);
		store.dispatch(action);
	}

	private void newTask() {
		System.out.print("neue Aufgabe: ");
		String text = Eingabe.leseString();
		System.out.print("Bearbeiter*in: ");
		String responsible = Eingabe.leseString();
		Action action = ActionFactory.create(Art.ADD, text, responsible);
		store.dispatch(action);
	}

	private void testData() {
		Action action = ActionFactory.create(Art.TESTDATA);
		store.dispatch(action);
	}

	private void finishTask() {
		System.out.print("welche Id: ");
		int id = Eingabe.leseInt();

		Action action = ActionFactory.create(Art.FINISH, id);
		store.dispatch(action);
	}

	private void showResponsibilities() {
		System.out.print("aktueller Bearbeiter: ");
		String bearbeiter = Eingabe.leseString();

		Action action = ActionFactory.create(Art.SHOW_RESPONSIBILITIES, bearbeiter);
		store.dispatch(action);
	}

	private void protectTasks() {
		System.out.print("kommaseparierte Liste nicht loeschbarer Tasks: ");
		String prot = Eingabe.leseString();
		
		String[] prots = prot.split(",");
		int[] protInts = new int[prots.length];
		for(int i = 0; i < prots.length; i++) {
			protInts[i] = Integer.parseInt(prots[i]);
		}
		
		Action action = ActionFactory.create(Art.PROTECT,protInts);
		store.dispatch(action);
		
	}

	private void rearrangeTasks() {
		System.out.print("aktueller Bearbeiter: ");
		String alt = Eingabe.leseString();
		System.out.print("neuer Bearbeiter: ");
		String neu = Eingabe.leseString();
		
		Action action = ActionFactory.create(Art.REARRANGE, alt, neu);
		store.dispatch(action);
		
	}
}
