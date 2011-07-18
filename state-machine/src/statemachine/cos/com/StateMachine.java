package statemachine.cos.com;

import java.lang.Object;
import android.util.

public class StateMachine extends Object {
    private State mRootState;

    public StateMachine() {
	mRootState = new State(".root");
    }

    public void addState(State state) {
	mRootState.addState(state);
    }

    public void setInitialState(State initialState) {
	mRootState.setInitialState(initialState);
    }

    public void sendEvent(Event event) {
    }
}