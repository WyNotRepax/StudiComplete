package store;

import action.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import reducer.Reducer;
import state.State;

public abstract class AbstractDecoratorStore implements StoreInterface {

  protected StoreInterface store;

  public AbstractDecoratorStore(StoreInterface store) {
    this.store = Objects.requireNonNull(store);
  }

  @Override
  public State getState() {
    return this.store.getState();
  }

  @Override
  public void dispatch(Action action) {
    this.store.dispatch(action);
  }
   
  @Override
  public void notifySubscribers() {
    this.store.notifySubscribers();
  }


  @Override
  public void subscribe(Subscriber subscriber) {
    this.store.subscribe(subscriber);
  }
  
}
