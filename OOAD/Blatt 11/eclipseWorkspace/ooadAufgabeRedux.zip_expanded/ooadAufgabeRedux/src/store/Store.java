package store;

import action.Action;
import java.util.ArrayList;
import java.util.List;
import reducer.Reducer;
import state.State;

public class Store implements StoreInterface {

  private State currentState;
  private Reducer reducer;
  private List<Subscriber> subscribers = new ArrayList<>();

  public Store(State initialState, Reducer reducer) {
    this.currentState = initialState;
    this.reducer = reducer;
  }

  @Override
  public State getState() { // nur fuer Tests
    System.out.println("getstate() in Store: Wenn das kein Test ist, "
        + "darf ich nicht aufgerufen werden!");
    return this.currentState;
  }

  @Override
  public void dispatch(Action action) {
    this.currentState = reducer.reduce(this.currentState, action);
    this.notifySubscribers();
  }
  
  @Override
  public void notifySubscribers() {
//    this.subscribers.forEach(subscriber -> 
//            subscriber.onChange(this.currentState.clone()));
    for (Subscriber s: this.subscribers){
      s.onChange(this.currentState.clone());
    }
  }
    
  @Override
  public void subscribe(Subscriber subscriber) {
    this.subscribers.add(subscriber);
    subscriber.onChange(this.currentState.clone());
  }
}
