package subscription;

/**
 * A class that represents a SMS subscriber entity, allowing for receiving newsletters via provided phone number.
 */
public class SmsSubscriber extends Subscriber {
    private final String phoneNumber;
    private final SubscriptionType subscriptionType;

    /**
     * Constructs a SmsSubscriber object.
     * @param firstName subscriber's first name
     * @param lastName subscriber's last name
     * @param phoneNumber subscriber's phone number
     */
    public SmsSubscriber(String firstName, String lastName, String phoneNumber) {
        super(firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.subscriptionType = SubscriptionType.SMS;
    }

    /**
     * Receives a newsletter content.
     * @param content a newsletter content
     */
    @Override
    public void receive(String content) {
        System.out.println(phoneNumber + " got: ");
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
}
