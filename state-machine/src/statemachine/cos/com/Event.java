package statemachine.cos.com;

import java.lang.Object;

public class Event extends Object {
    private String mName;

    public Event(String name) {
	mName = name;
    }

    @Override public String toString() {
	return getClass().getName() + "[" +
	    "name=\"" + mName + "\"]";
    }
}