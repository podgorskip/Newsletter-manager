package subscription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that displays a subscription form which allows new users to sign up for a specified type of subscription.
 */
public class SubscriptionForm extends JFrame implements ActionListener {
    private Subscriber.SubscriptionType type;
    private JTextField firstNameField, lastNameField, mediumField;
    private JRadioButton emailButton, phoneButton;
    private JButton confirmationButton, backButton;
    private final SubscriptionCallback subscriptionCallback;

    /**
     * Constructs a subscription.SubscriptionForm object and initializes it.
     */
    public SubscriptionForm(SubscriptionCallback callback) {
        this.subscriptionCallback = callback;
        initForm();
    }

    public void displaySubscriptionForm() {
        setVisible(true);
    }

    /**
     * Handles actions chosen by a user via button click.
     * Sends callback after the confirmation button is clicked.
     * @param event the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == confirmationButton) {

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String medium = mediumField.getText();

            subscriptionCallback.onSubscriptionConfirmed(firstName, lastName, medium, type);
            resetFields();

        } else if (event.getSource() == backButton) {
            dispose();
        }
    }

    /**
     * Initializes the subscription form.
     */
    private void initForm() {
        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        setLayout(layout);
        setTitle("Subscription form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel firstNameLabel = new JLabel("First name");
        firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last name");
        lastNameField = new JTextField();

        JLabel subscriptionTypeLabel = new JLabel("Subscription type");
        mediumField = new JTextField();
        JLabel label = new JLabel();

        emailButton = new JRadioButton("Email subscription");
        emailButton.addActionListener(event -> {
            type = Subscriber.SubscriptionType.EMAIL;
            label.setText("Email");
        });

        phoneButton = new JRadioButton("SMS subscription");
        phoneButton.addActionListener(event -> {
            type = Subscriber.SubscriptionType.SMS;
            label.setText("Phone number");
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(emailButton);
        buttonGroup.add(phoneButton);

        confirmationButton = new JButton("Confirm");
        confirmationButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(firstNameLabel)
                                .addComponent(lastNameLabel)
                                .addComponent(subscriptionTypeLabel)
                                .addComponent(label))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(firstNameField)
                                .addComponent(lastNameField)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(emailButton)
                                        .addComponent(phoneButton))
                                .addComponent(mediumField)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(confirmationButton)
                                        .addComponent(backButton))
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(firstNameLabel)
                                .addComponent(firstNameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lastNameLabel)
                                .addComponent(lastNameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(subscriptionTypeLabel)
                                .addComponent(emailButton)
                                .addComponent(phoneButton))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label)
                                .addComponent(mediumField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(confirmationButton)
                                .addComponent(backButton))
        );

        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 300));
    }

    /**
     * Resets all the text fields and the ration button's choice.
     */
    private void resetFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailButton.setSelected(false);
        phoneButton.setSelected(false);
        mediumField.setText("");
    }
}
