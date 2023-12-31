package actions.subscription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that displays a subscription form which allows new users to sign up for a specified type of actions.subscription.
 */
public class SubscriptionForm extends JFrame implements ActionListener {
    private Subscriber.SubscriptionType type;
    private JTextField firstNameField, lastNameField, mediumField;
    private JRadioButton emailButton, phoneButton;
    private JButton confirmationButton, backButton;
    private ButtonGroup buttonGroup;
    private final SubscriptionCallback subscriptionCallback;

    /**
     * Constructs a SubscriptionForm object and initializes it.
     * @param callback callback to notify when a button is clicked
     */
    public SubscriptionForm(SubscriptionCallback callback) {
        this.subscriptionCallback = callback;
        initForm();
    }

    /**
     * Makes the form visible.
     */
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

            if (!emailButton.isSelected() && !phoneButton.isSelected()) {
                notSubscriptionTypeChosen();
                return;
            }

            String firstName = firstNameField.getText();
            if (firstName.isEmpty()) {
                emptyFieldMessage("first name");
                return;
            }

            String lastName = lastNameField.getText();
            if (lastName.isEmpty()) {
                emptyFieldMessage("last name");
                return;
            }

            String medium = mediumField.getText();
            if (medium.isEmpty()) {
                if (type == Subscriber.SubscriptionType.EMAIL) {
                    emptyFieldMessage("email");
                    return;
                } else if (type == Subscriber.SubscriptionType.SMS) {
                    emptyFieldMessage("phone number");
                    return;
                }
            }

            resetFields();
            subscriptionCallback.onSubscriptionConfirmed(firstName, lastName, medium, type);

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

        buttonGroup = new ButtonGroup();
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
        buttonGroup.clearSelection();
        mediumField.setText("");
    }

    private void emptyFieldMessage(String fieldType) {
        JOptionPane.showMessageDialog(this, "The " + fieldType + " cannot be empty.");
    }

    private void notSubscriptionTypeChosen() {
        JOptionPane.showMessageDialog(this, "A subscription type must be chosen.");
    }
}
