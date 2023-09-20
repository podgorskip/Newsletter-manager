package core;

import actions.subscription.EmailSender;
import actions.subscription.Subscriber;
import actions.subscription.SubscriptionCallback;
import actions.subscription.SubscriptionForm;
import actions.Actions;
import actions.ActivityForm;
import actions.ActivityCallback;

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

    public void start() {
        SwingUtilities.invokeLater(() -> {
            ActivityForm activityForm = new ActivityForm(new ActivityCallback() {
                @Override
                public void onActivityChosen(Actions action) {

                    switch (action) {
                        case ADD_SUBSCRIBER -> { addSubscriber(); }
                        case REMOVE_SUBSCRIBER -> { removeSubscriber(); }
                        case SHOW_NEWS -> { showCurrentNews(); }
                        case SEND_EMAIL -> {
                            try {
                                if (sendEmailSubscribers()) {
                                    String message = "Email newsletter provided.";
                                    JOptionPane.showMessageDialog(null, message);
                                }
                            } catch (IOException e) { throw new RuntimeException(e); }
                        }
                        case SEND_SMS -> {
                            try { sendSmsSubscribers(); }
                            catch (IOException e) { throw new RuntimeException(e); }
                        }
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

    /**
     * Sends a newsletter content to all the email subscribers.
     * @return true if the newsletter is sent successfully, false otherwise
     * @throws IOException if there's a resource error
     */
    private boolean sendEmailSubscribers() throws IOException {
        String[] content = resources.getResource();
        return provider.sendViaEmail(content[0], content[1]);
    }

    /**
     * Sends a newsletter content to all the SMS subscribers.
     * @throws IOException if there's a resource error
     */
    private void sendSmsSubscribers() throws IOException {
        String[] content = resources.getResource();
        provider.sendViaSms(content[0], content[1]);
    }

    private void removeSubscriber() {}
    private void showCurrentNews() {}
}
