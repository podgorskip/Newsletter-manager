package subscription;

public abstract class Subscriber {
    private final String firstName;
    private final String lastName;

    public enum SubscriptionType {
        EMAIL, SMS;
    }

    public Subscriber(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public abstract void receive(String content);

    public abstract SubscriptionType getSubscriptionType();

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
