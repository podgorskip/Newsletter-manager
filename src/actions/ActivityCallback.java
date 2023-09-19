package actions;

/**
 * An interface that allows to evoke the action chosen by a user.
 */
public interface ActivityCallback {
    /**
     * Notifies and passes information about an action if a button was clicked.
     * @param action a type of action to be performed
     */
    void onActivityChosen(Actions action);
}
