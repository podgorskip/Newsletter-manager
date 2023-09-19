package subscription;

/**
 * An interface that allows to handle data provided by a user.
 */
public interface SubscriptionCallback {
    /**
     * Notifies and passes provided data after a user clicks the confirmation button.
     * @param firstName provided first name
     * @param lastName provided last name
     * @param medium provided medium (email/phone number)
     * @param type provided subscription type
     */
    void onSubscriptionConfirmed(String firstName, String lastName, String medium, Subscriber.SubscriptionType type);
}
