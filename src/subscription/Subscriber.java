package subscription;

/**
 * An abstract class that is a base for more specific subscriber classes depending on a subscription type.
 */
public abstract class Subscriber {
    private final String firstName;
    private final String lastName;

    /**
     * Available subscription types.
     */
    public enum SubscriptionType {
        EMAIL, SMS;
    }

    /**
     * Constructs a Subscriber object.
     * @param firstName subscriber's first name
     * @param lastName subscriber's last name
     */
    public Subscriber(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * An abstract method to be overloaded that manipulates the received newsletter content.
     * @param content newsletter's content
     */
    public abstract void receive(String content);

    /**
     * An abstract method to return the secified subscription type.
     * @return a subscription type
     */
    public abstract SubscriptionType getSubscriptionType();
}
