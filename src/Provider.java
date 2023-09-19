import subscription.EmailSubscriber;
import subscription.SmsSubscriber;
import subscription.Subscriber;

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

    /**
     * Constructs a Provider object.
     */
    public Provider() {
        connection = DatabaseConnection.connection;
        subscribers = new ArrayList<>();
        restoreFromDatabase();
    }

    /**
     * Sends specified content to all the email subscribers.
     * @param content a content to be delivered
     */
    public void sendViaEmail(String content) {
        subscribers.stream()
                .filter(subscriber -> subscriber.getSubscriptionType() == Subscriber.SubscriptionType.EMAIL)
                .forEach(subscriber -> subscriber.receive(content));
    }

    /**
     * Sends specified content to all the SMS subscribers.
     * @param content a content to be delivered
     */
    public void sendViaSms(String content) {
       subscribers.stream()
               .filter(subscriber -> subscriber.getSubscriptionType() == Subscriber.SubscriptionType.SMS)
               .forEach(subscriber -> subscriber.receive(content));
    }

    /**
     * Adds a new email subscriber.
     * @param firstName the new subscriber's first name
     * @param lastName the new subscriber's last name
     * @param email the new subscriber's email
     * @return true if a subscriber is added correctly, false otherwise
     */
    public boolean addEmailSubscriber(String firstName, String lastName, String email) {
        String sql = "INSERT INTO subscribers (firstName, lastName, email) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            subscribers.add(new EmailSubscriber(firstName, lastName, email));

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a new SMS subscriber.
     * @param firstName the new subscriber's first name
     * @param lastName the new subscriber's last name
     * @param phoneNumber the new subscriber's phone number
     * @return true if a subscriber is added correctly, false otherwise
     */
    public boolean addSmsSubscriber(String firstName, String lastName, String phoneNumber) {
        String sql = "INSERT INTO subscribers (firstName, lastName, phoneNumber) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            subscribers.add(new SmsSubscriber(firstName, lastName, phoneNumber));

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phoneNumber);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
}
