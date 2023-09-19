package subscription;

public class SmsSubscriber extends Subscriber {
    private final String phoneNumber;
    private final SubscriptionType subscriptionType;

    public SmsSubscriber(String firstName, String lastName, String phoneNumber) {
        super(firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.subscriptionType = SubscriptionType.SMS;
    }

    @Override
    public void receive(String content) {
        System.out.println(phoneNumber + " got: ");
        System.out.println(content);
    }

    @Override
    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
