package statemachine.cos.com;

import java.lang.Object;

public class Transition extends Object {
    private String mName;

    public Transition(String name) {
	mName = name;
    }

    @Override public String toString() {
	return getClass().getName() + "[" +
	    "name=" + mName + "]";
    }
}