package actions.subscription;

public interface SubscriptionResignationCallback {
    void onResignationConfirmed(String medium, Subscriber.SubscriptionType subscriptionType);
}
