package statemachine.cos.com;

import java.lang.Object;
import java.util.ArrayList;

import android.util.Log;

public class StateMachine extends Object {
    private final static String TAG = "StateMachineActivity";
    private State mRootState = null;
    private State mCurrentState = null;

    public StateMachine() {
	mRootState = State.createRootState();
    }

    public void start() {
	if (mCurrentState != null) {
	    throw new RuntimeException("StateMachine is running already");
	}
	mCurrentState = mRootState.enter(new Event("StateMachine startup"), true);
    }

    public State getCurrentState() {
	return mCurrentState;
    }

    void addState(State state) {
	mRootState.addState(state);
    }

    public void setInitialState(State initialState) {
	mRootState.setInitialState(initialState);
    }

    public void sendEvent(Event event) {
	processEvent(event);
    }

    private void processEvent(Event event) {
    	State old_state, new_state;
    	Transition trans = null;
    	int i, n;
    	ArrayList<State> current_2_transHost = new ArrayList();
    	ArrayList<State> target_2_ancestor = new ArrayList();

    	if (mCurrentState == null) {
    	    throw new RuntimeException("StateMachine is not running");
    	}

    	for (old_state = mCurrentState; /* NULL */; old_state = old_state.getParentState()) {
    	    if (old_state.getDepth() <= 0) {
		Log.d(TAG, "no transition is triggered");
    		return;
    	    }
    	    /* XXX: can't leave right now, since don't know wheather there is a proper transition with a not-null target */
    	    current_2_transHost.add(old_state);
    	    if ((trans = old_state.eventTest(event)) != null) {
    		break;
    	    }
    	}

    	Log.d(TAG, "find transition " + trans);
    	new_state = trans.getTarget();
	if (new_state == null) {
	    throw new RuntimeException("the target state is null");
	}
    
	n = current_2_transHost.size();
	for (i=0; i<n; i++) {
	    current_2_transHost.get(i).leave(event);
	}

    	for (i=old_state.getDepth() - new_state.getDepth(); i>1; --i) {
    	    old_state = old_state.getParentState();
    	    old_state.leave(event);
    	}

    	for (i=new_state.getDepth() - old_state.getDepth(); i>=0; --i) {
    	    target_2_ancestor.add(0, new_state);
    	    new_state = new_state.getParentState();
    	}

	if (old_state.getDepth() != new_state.getDepth() + 1) {
	    throw new RuntimeException("assert (old_state.getDepth() == new_state.getDepth() + 1)");
	}

    	while (old_state.getParentState() != new_state) {
    	    old_state = old_state.getParentState();
    	    old_state.leave(event);

    	    target_2_ancestor.add(0, new_state);
    	    new_state = new_state.getParentState();
    	}

    	if(target_2_ancestor.size() == 0){
	    old_state.getParentState().leave(event);
	    target_2_ancestor.add(0, new_state);
	}

    	trans.action(event);

	n = target_2_ancestor.size();
	State s;
	for (i=0; i<n; i++) {
	    s = target_2_ancestor.get(i);
    	    if (s == trans.getTarget()) {
    		mCurrentState = s.enter(event, true);
    	    }else{
    		s.enter(event, false);
    	    }
    	}

    	for (i=new_state.getDepth(); i>0; --i, new_state=new_state.getParentState()) {
    	    new_state.resetTransitions();
    	}
    }
}
