import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import statemachine.cos.com.*;

public class StateMachineActivity extends Activity
{
    private final static String TAG = "StateMachineActivity";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	test();
    }

    void test() {
	Log.d(TAG, "test");
	StateMachine stateMachine = new StateMachine();
	State rootState = new State(stateMachine, "root");
	State s1 = new State(rootState, "s1");
	State s11 = new State(s1, "s11");
	State s12 = new State(s1, "s12");
	s1.setInitialState(s12);
	State s2 = new State(rootState, "s2");
	State s21 = new State(s2, "s21");

	rootState.setInitialState(s1);
	Log.d(TAG, "1a");
	Transition trans = new Transition(s1, s2, "s1 -> s2");
	Log.d(TAG, "1");
	/*
	trans.setOnTransitionListener(new Transition.OnTransitionListener() {
		@Override public void onTransition(Event event, Transition transition) {
		    Log.d(TAG, "onTransition("+event+", "+transition+")");
		}
	    });
	*/
	Log.d(TAG, "2");

	stateMachine.start();
	Log.d(TAG, "current: " + stateMachine.getCurrentState());

	stateMachine.sendEvent(null);
    }
}
