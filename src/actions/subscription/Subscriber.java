package actions.subscription;

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
    public abstract void receive(String content, String date);

    /**
     * An abstract method to return the specified subscription type.
     * @return a subscription type
     */
    public abstract SubscriptionType getSubscriptionType();

    /**
     * An abstract method that returns email/phone number depending on the subscriber type.
     * @return email/phone number
     */
    public abstract String getContactDetails();
}
