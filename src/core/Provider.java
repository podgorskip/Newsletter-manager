package core;

import actions.subscription.EmailSender;
import actions.subscription.EmailSubscriber;
import actions.subscription.SmsSubscriber;
import actions.subscription.Subscriber;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that manages subscribers and allows for sending them newsletters depending on the chosen medium.
 */
public class Provider {
    private final Connection connection;
    private final List<Subscriber> subscribers;
    private final String newsletterEmail;

    /**
     * Constructs a Provider object.
     */
    public Provider(String newsletterEmail) {
        connection = DatabaseConnection.connection;
        subscribers = new ArrayList<>();
        this.newsletterEmail = newsletterEmail;
        restoreFromDatabase();
    }

    /**
     * Sends specified content to all the email subscribers.
     * @param content a content to be delivered
     * @param date the newsletter's date
     * @return true if sent successfully, false otherwise
     */
    public boolean sendViaEmail(String content, String date) {
        List<Subscriber> emailSubscribers = subscribers.stream()
                .filter(subscriber -> subscriber.getSubscriptionType() == Subscriber.SubscriptionType.EMAIL)
                .toList();

        List<String> emails = emailSubscribers.stream().map(Subscriber::getContactDetails).toList();

        EmailSender emailSender = new EmailSender(newsletterEmail, emails);
        emailSubscribers.forEach(subscriber -> subscriber.receive(content, date));
        return emailSender.send(content, date);
    }

    /**
     * Sends specified content to all the SMS subscribers.
     * @param content a content to be delivered
     */
    public void sendViaSms(String content, String date) {
       subscribers.stream()
               .filter(subscriber -> subscriber.getSubscriptionType() == Subscriber.SubscriptionType.SMS)
               .forEach(subscriber -> subscriber.receive(content, date));
    }

    /**
     * Adds a new email subscriber.
     * @param firstName the new subscriber's first name
     * @param lastName the new subscriber's last name
     * @param email the new subscriber's email
     */
    public void addEmailSubscriber(String firstName, String lastName, String email) {

        if (isEmailSubscriptionActive(email)) {
            JOptionPane.showMessageDialog(null, "Provided email is already signed up for the newsletter.");

        } else {

            String sql = "INSERT INTO subscribers (firstName, lastName, email) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 1) { subscribers.add(new EmailSubscriber(firstName, lastName, email)); }
                else { JOptionPane.showMessageDialog(null, "Failed to update the database"); }

            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Adds a new SMS subscriber.
     * @param firstName the new subscriber's first name
     * @param lastName the new subscriber's last name
     * @param phoneNumber the new subscriber's phone number
     */
    public void addSmsSubscriber(String firstName, String lastName, String phoneNumber) {

        if (isSmsSubscriptionActive(phoneNumber)) {
            JOptionPane.showMessageDialog(null, "Provided phone number is already signed up for the newsletter.");

        } else {
            String sql = "INSERT INTO subscribers (firstName, lastName, phoneNumber) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, phoneNumber);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 1) { subscribers.add(new SmsSubscriber(firstName, lastName, phoneNumber)); }
                else { JOptionPane.showMessageDialog(null, "Failed to update the database"); }

            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Removes an email subscriber.
     * @param email user's email to be removed from subscribing
     */
    public void removeEmailSubscriber(String email) {

        if (isEmailSubscriptionActive(email)) {
            String sql = "DELETE FROM subscribers WHERE email=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, email);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 1) { subscribers.removeIf(subscriber -> subscriber.getContactDetails().equals(email)); }
                else { JOptionPane.showMessageDialog(null, "Failed to remove from the database."); }

            } catch (SQLException e) { e.printStackTrace(); }

        } else {
            JOptionPane.showMessageDialog(null, "Provided email is not signed up for the subscription.");
        }
    }

    /**
     * Removes a sms subscriber.
     * @param phoneNumber user's phone number to be removed from subscribing
     */
    public void removeSmsSubscriber(String phoneNumber) {

        if (isSmsSubscriptionActive(phoneNumber)) {
            String sql = "DELETE FROM subscribers WHERE phoneNumber=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, phoneNumber);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 1) { subscribers.removeIf(subscriber -> subscriber.getContactDetails().equals(phoneNumber)); }
                else { JOptionPane.showMessageDialog(null, "Failed to remove from the database."); }

            } catch (SQLException e) { e.printStackTrace(); }

        } else {
            JOptionPane.showMessageDialog(null, "Provided phone number is not signed up for the subscription.");
        }
    }

    /**
     * Restores information about subscribers from a database.
     */
    private void restoreFromDatabase() {
        String sql = "SELECT * FROM subscribers";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");

                if (email != null) {
                    subscribers.add(new EmailSubscriber(firstName, lastName, email));
                } else if (phoneNumber != null) {
                    subscribers.add(new SmsSubscriber(firstName, lastName, phoneNumber));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if the provided email is sign up for the newsletter.
     * @param email an email to be checked
     * @return true if is signed up, false otherwise
     */
    private boolean isEmailSubscriptionActive(String email) {
        for (Subscriber subscriber : subscribers.stream().filter(subscriber -> subscriber.getSubscriptionType() == Subscriber.SubscriptionType.EMAIL).toList()) {
            if (subscriber.getContactDetails().equals(email)) { return true; }
        }
        return false;
    }

    /**
     * Checks if the provided phone number is sign up for the newsletter.
     * @param phoneNumber a phone number to be checked
     * @return true if is signed up, false otherwise
     */
    private boolean isSmsSubscriptionActive(String phoneNumber) {
        for (Subscriber subscriber : subscribers.stream().filter(subscriber -> subscriber.getSubscriptionType() == Subscriber.SubscriptionType.SMS).toList()) {
            if (subscriber.getContactDetails().equals(phoneNumber)) { return true; }
        }
        return false;
    }
}
