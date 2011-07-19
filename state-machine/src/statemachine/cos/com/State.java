package statemachine.cos.com;

import java.lang.Object;
import java.lang.RuntimeException;
import java.util.ArrayList;

import android.util.Log;

public class State extends Object {
    private final static String TAG = "State";

    private String mName;
    private State mParentState = null;
    private State mInitialState = null;
    private int mDepth = 0;
    private boolean mEntered = false;
    private ArrayList<Transition> mTransitions = new ArrayList();
    private ArrayList<State> mChildStates = new ArrayList();

    public State(StateMachine stateMachine, String name) {
	mName = name;
	stateMachine.addState(this);
    }

    public State(State parentState, String name) {
	mName = name;
	parentState.addState(this);
    }

    public void setInitialState(State state) {
	mInitialState = state;
    }

    public final String getName() {
	return mName;
    }

    public State getParentState() {
	return mParentState;
    }

    /*
    public StateMachine getStateMachine() {
	if (mStateMachine != null) {
	    return mStateMachine;
	}else{
	    return mParentState.getStateMachine();
	}
    }
    */

    public void addTransition(Transition transition) {
	mTransitions.add(transition);
    }

    public int getDepth() {
	return mDepth;
    }

    @Override public String toString() {
	return getClass().getName() + "[" +
	    "name=" + mName + "]";
    }

    State enter(boolean autojump) {
	if (mEntered) {
	    throw new RuntimeException("re-enter "+toString());
	}
	mEntered = true;

	onEnter();
	resetTransitions();
	if (autojump) {
	    if (mInitialState != null) {
		return mInitialState.enter(true);
	    }else if (mChildStates.size() == 1) {
		return mChildStates.get(0);
	    }else{
		throw new RuntimeException(toString() + "has no initial-state");
	    }
	}else{
	    return this;
	}
    }

    void leave() {
	if (! mEntered) {
	    throw new RuntimeException(toString() + "is not entered");
	}
	mEntered = false;

	onLeave();
    }

    void addState(State childState) {
	mChildStates.add(childState);
	childState.mParentState = this;
    }

    /* const Event *event, const Transition *transition */
    protected void onEnter() {
	Log.d(TAG, toString()+".onEnter()");
    }

    protected void onLeave() {
	Log.d(TAG, toString()+".onLeave()");
    }

    private Transition eventTest(Event event) {
	if (mTransitions.isEmpty()) {
	    return null;
	}else{
	    // TODO
	    return mTransitions.get(0);
	}
    }

    private void resetTransitions() {
	// TODO
    }
}