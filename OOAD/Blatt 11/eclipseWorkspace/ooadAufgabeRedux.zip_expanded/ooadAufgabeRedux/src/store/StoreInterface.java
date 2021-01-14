package store;

import action.Action;
import state.State;

public interface StoreInterface {

  public void dispatch(Action action);
  public State getState();
  public void subscribe(Subscriber subscriber);
  public void notifySubscribers();
  
}
