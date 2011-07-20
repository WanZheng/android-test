package statemachine.cos.com;

import java.lang.Object;

import android.util.Log;

public class Transition extends Object {
    private final static String TAG = "StateMachineActivity";
    private String mName;
    State mSource;
    State mTarget;

    public Transition(State source, State target, String name) {
	mSource = source;
	mTarget = target;
	mName = name;

	mSource.addTransition(this);
    }

    public State getTarget() {
	return mTarget;
    }

    void action() {
	Log.d(TAG, this + "action()");
    }

    protected boolean eventTest(Event event) {
	// TODO:
	return true;
    }

    protected void reset() {
    }

    @Override public String toString() {
	return getClass().getName() + "[" +
	    "name=" + mName + "]";
    }
}