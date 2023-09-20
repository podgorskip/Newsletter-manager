package actions.subscription;

/**
 * A class that represents an email subscriber entity, allowing for receiving newsletters via provided email.
 */
public class EmailSubscriber extends Subscriber {
    private final String email;
    private final SubscriptionType subscriptionType;

    /**
     * Constructs an EmailSubscriber object.
     * @param firstName subscriber's first name
     * @param lastName subscriber's last name
     * @param email subscriber's email
     */
    public EmailSubscriber(String firstName, String lastName, String email) {
        super(firstName, lastName);
        this.email = email;
        this.subscriptionType = SubscriptionType.EMAIL;
    }

    /**
     * Receives a newsletter content.
     * @param content a newsletter content
     */
    @Override
    public void receive(String content, String date) {
        System.out.println("[" + date + "] " + email + " got:");
        System.out.println(content);
    }

    /**
     * Returns the entity's subscription type.
     * @return type of subscription
     */
    @Override
    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * Returns the subscriber's email.
     * @return subscriber's email
     */
    @Override
    public String getContactDetails() {
        return email;
    }
}


