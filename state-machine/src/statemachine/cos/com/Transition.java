package statemachine.cos.com;

import java.lang.Object;

public class Transition extends Object {
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
    }

    @Override public String toString() {
	return getClass().getName() + "[" +
	    "name=" + mName + "]";
    }
}