import subscription.Subscriber;
import subscription.SubscriptionCallback;
import subscription.SubscriptionForm;

import javax.swing.*;
import java.io.IOException;

/**
 * A class that manages subscribers and newsletters delivery.
 */
public class NewsletterManager {
    private final Provider provider;
    private final Resources resources;

    /**
     * Constructs a NewsletterManager object.
     * @param provider an object that handles newsletters delivery
     * @param resources an object that provides the newsletter's content
     */
    public NewsletterManager(Provider provider, Resources resources) {
        this.provider = provider;
        this.resources = resources;
    }

    /**
     * Sends a newsletter content to all the email subscribers.
     * @throws IOException if there's a resource error
     */
    public void sendEmailSubscribers() throws IOException {
        provider.sendViaEmail(resources.getResource());
    }

    /**
     * Sends a newsletter content to all the SMS subscribers.
     * @throws IOException if there's a resource error
     */
    public void sendSmsSubscribers() throws IOException {
        provider.sendViaSms(resources.getResource());
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            ActivityForm activityForm = new ActivityForm(new ActivityCallback() {
                @Override
                public void onActivityChosen(Actions action) {
                    if (action == Actions.ADD_SUBSCRIBER) {
                        addSubscriber();
                    } else if (action == Actions.REMOVE_SUBSCRIBER) {
                        removeSubscriber();
                    } else if (action == Actions.SHOW_NEWS) {
                        showCurrentNews();
                    }
                }
            });

            activityForm.displayActivityForm();

        });
    }

    /**
     * Adds a new subscriber based on the subscription form.
     */
    private void addSubscriber() {
        SwingUtilities.invokeLater(() -> {
            SubscriptionForm subscriptionForm = new SubscriptionForm(new SubscriptionCallback() {
                @Override
                public void onSubscriptionConfirmed(String firstName, String lastName, String medium, Subscriber.SubscriptionType type) {
                    if (type == Subscriber.SubscriptionType.EMAIL) {
                        provider.addEmailSubscriber(firstName, lastName, medium);
                    } else if (type == Subscriber.SubscriptionType.SMS) {
                        provider.addSmsSubscriber(firstName, lastName, medium);
                    }
                }
            });

            subscriptionForm.displaySubscriptionForm();

        });
    }

    private void removeSubscriber() {}
    private void showCurrentNews() {}
}
