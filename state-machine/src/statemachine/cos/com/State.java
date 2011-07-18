package statemachine.cos.com;

import java.lang.Object;
import java.util.ArrayList;

public class State extends Object {
    private final static String TAG = "State";

    private String mName;
    private StateMachine mStateMachine = null;
    private State mParentState = null;
    private State mInitialState = null;
    private int mDepth = 0;
    private bool mEntered = false;
    private ArrayList<Transition> mTransitions = new ArrayList();
    private ArrayList<State> mChildStates = new ArrayList();

    public State(StateMachine stateMachine, String name) {
	mStateMachine = stateMachine;
	mName = name;
    }

    public State(State parentState, String name) {
	mParentState = parentState;
	mName = name;
    }

    public final String getName() {
	return mName;
    }

    public State getParentState() {
	return mParentState;
    }

    public StateMachine getStateMachine() {
	if (mStateMachine != null) {
	    return mStateMachine;
	}else{
	    return mParentState.getStateMachine();
	}
    }

    public void addTransition(Transition transition) {
	mTransitions.add(transition);
    }

    public int getDepth() {
	return mDepth;
    }

    /* const Event *event, const Transition *transition */
    protected void onEnter() {
	Log.d(TAG, toString()+".onEnter()");
    }

    protected void onLeave() {
	Log.d(TAG, toString()+".onLeave()");
    }

    private void setInitialState(State state) {
	mInitialState = state;
    }

    private void addState(State childState) {
	mChildStates.add(childState);
    }

    private Transition eventTest(Event event) {
	if (mTransitions.isEmpty()) {
	    return null;
	}else{
	    // TODO
	    return mTransitions.get(0);
	}
    }

    private State enter(bool autojump) {
	if (mEntered) {
	    throw new Exception("re-enter "+toString());
	}
	mEntered = true;

	onEnter();
	resetTransitions();
	if (autojump) {
	    if (mInitialState) {
		return mInitialState.enter(true);
	    }else{
		throw new Exception(toString() + "has no initial-state");
	    }
	}else{
	    return this;
	}
    }

    private void leave() {
	if (! mEntered) {
	    throw new Exception(toString() + "is not entered");
	}
	mEntered = false;

	onLeave();
    }

    @Override public String toString() {
	return getClass().getName() + "[" +
	    "name=" + mName + "]";
    }
}