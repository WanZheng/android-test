package statemachine.cos.com;

import java.lang.Object;

public class StateMachine extends Object {
    private State mRootState = null;

    public StateMachine() {
	new State(this, ".root");
    }

    public void addState(State state) {
	if (mRootState == null) {
	    mRootState = state;
	}else{
	    mRootState.addState(state);
	}
    }

    public void setInitialState(State initialState) {
	mRootState.setInitialState(initialState);
    }

    public void sendEvent(Event event) {
    }
}