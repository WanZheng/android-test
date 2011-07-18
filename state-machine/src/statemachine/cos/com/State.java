package statemachine.cos.com;

public class State {
    private String mName;
    private StateMachine mStateMachine;
    private State mParentState;
    private State mInitalState;

    public State(StateMachine stateMachine, String name);
    public State(State parentState, String name);

    public final String getName() {
	return mName;
    }

    public State getParentState() {
	return mParentState;
    }

    public StateMachine getStateMachine() {
	return mStateMachine;
    }
    
    int depth() const { return m_depth; }
    void setInitialState(State *state);
    void addTransition(Transition *transition);
protected:
    virtual void onEnter(const Event *event, const Transition *transition);
    virtual void onLeave(const Event *event, const Transition *transition);
private:
    State(); /* XXX: for root state only */
    void addState(State *childState);
    Transition *eventTest(const Event *);
    State *enter(const Event *event, const Transition *transition, bool autojump);
    void leave(const Event *event, const Transition *transition);
    void resetTransitions();
    friend class StateMachine;
private:
    std::vector<Transition *> m_transitions;
    std::vector<State *> m_childStates;
    int m_depth;
    bool m_entered;
}