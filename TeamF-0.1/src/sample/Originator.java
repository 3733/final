package sample;

import javafx.scene.*;

public class Originator {

    private Scene state;
    // The class could also contain additional data that is not part of the
    // state saved in the memento...

    public void set(Scene state) {
        this.state = state;
        System.out.println("Originator: Setting state to " + state);
    }

    public MementoWindow saveToMemento() {
        System.out.println("Originator: Saving to Memento.");
        return new MementoWindow(this.state);
    }

    public void restoreFromMemento(MementoWindow memento) {
        this.state = memento.getSavedState();
        System.out.println("Originator: State after restoring from Memento: " + state);
    }

    public static class MementoWindow {

        private final Scene state;

        public MementoWindow(Scene stateToSave) {
            state = stateToSave;
        }

        // accessible by outer class only
        private Scene getSavedState() {
            return state;
        }
    }
}
