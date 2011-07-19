package statemachine.cos.com;

import java.lang.Object;
import java.lang.RuntimeException;
import java.util.ArrayList;

import android.util.Log;

public class State extends Object {
    private final static String TAG = "StateMachineActivity";

    private String mName;
    private State mParentState = null;
    private State mInitialState = null;
    private int mDepth = 0;
    private boolean mEntered = false;
    private ArrayList<Transition> mTransitions = null;
    private ArrayList<State> mChildStates = null;

    public State(StateMachine stateMachine, String name) {
	mName = name;
	stateMachine.addState(this);
    }

    public State(State parentState, String name) {
	mName = name;
	parentState.addState(this);
    }

    /* for creating the __root__ state only */
    private State() {
    }

    static State createRootState() {
	State state = new State();
	state.mName = "__root__";
	state.mDepth = 0;

	return state;
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

    void addTransition(Transition transition) {
	if (mTransitions == null) {
	    mTransitions = new ArrayList();
	}
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
	    throw new RuntimeException("re-enter "+this);
	}
	mEntered = true;

	onEnter();
	resetTransitions();

	if (autojump) {
	    if (mChildStates == null) {
		return this;
	    }else{
		if (mInitialState != null) {
		    return mInitialState.enter(true);
		}else if (mChildStates.size() == 1) {
		    return mChildStates.get(0).enter(true);
		}else{
		    throw new RuntimeException(this + "has no initial-state");
		}
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
	if (mChildStates == null) {
	    mChildStates = new ArrayList();
	}
	mChildStates.add(childState);
	childState.mParentState = this;
	childState.mDepth = getDepth() + 1;
    }

    /* const Event *event, const Transition *transition */
    protected void onEnter() {
	Log.d(TAG, this+".onEnter()");
    }

    protected void onLeave() {
	Log.d(TAG, this+".onLeave()");
    }

    Transition eventTest(Event event) {
	Log.d(TAG, this + "eventTest()");
	if (mTransitions == null || mTransitions.isEmpty()) {
	    return null;
	}else{
	    // TODO
	    Log.d(TAG, this + "eventTest() = " + mTransitions.get(0));
	    return mTransitions.get(0);
	}
    }

    void resetTransitions() {
	// TODO
    }
}