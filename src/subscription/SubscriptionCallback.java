package subscription;

public interface SubscriptionCallback {
    void onSubscriptionConfirmed(String firstName, String lastName, String medium, Subscriber.SubscriptionType type);
}
