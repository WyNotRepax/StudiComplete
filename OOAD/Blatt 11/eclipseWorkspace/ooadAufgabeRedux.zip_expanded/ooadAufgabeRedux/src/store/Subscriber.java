package store;

import state.State;

public interface Subscriber {

  void onChange(State state);
}
