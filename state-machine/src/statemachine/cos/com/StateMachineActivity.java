package statemachine.cos.com;

import android.app.Activity;
import android.os.Bundle;

public class StateMachineActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	test();
    }

    void test() {
	StateMachine stateMachine = new StateMachine();
	State rootState = new State(stateMachine);
	State s1 = new State(rootState, "s1");
	State s2 = new State(rootState, "s2");

	rootState.setInitialState(s1);
    }
}
