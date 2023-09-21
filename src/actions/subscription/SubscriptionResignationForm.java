package actions.subscription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that displays a subscription resignation form that allows to resign from receiving newsletters.
 */
public class SubscriptionResignationForm extends JFrame implements ActionListener {
    private Subscriber.SubscriptionType type;
    private final SubscriptionResignationCallback callback;
    private JTextField mediumField;
    private JRadioButton emailButton, phoneButton;
    private ButtonGroup buttonGroup;
    private JButton confirmationButton, backButton;

    /**
     * Constructs a SubscriptionResignationForm object.
     * @param callback callback to notify when a button is clicked
     */
    public SubscriptionResignationForm(SubscriptionResignationCallback callback) {
        this.callback = callback;
        initForm();
    }

    /**
     * Makes the form visible.
     */
    public void displaySubscriptionResignationForm() {
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
            String medium = mediumField.getText().trim();
            callback.onResignationConfirmed(medium, type);
        } else if (event.getSource() == backButton) {
            dispose();
        }
    }

    /**
     * Initializes the subscription form.
     */
    private void initForm() {
        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        setLayout(layout);
        setTitle("Subscription resignation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel infoLabel = new JLabel("To resign from the newsletter subscription choose its type");
        JLabel mediumLabel = new JLabel();

        emailButton = new JRadioButton("Email subscription");
        emailButton.addActionListener(e -> {
            type = Subscriber.SubscriptionType.EMAIL;
            mediumLabel.setText("Email");
        });

        phoneButton = new JRadioButton("SMS subscription");
        phoneButton.addActionListener(e -> {
            type = Subscriber.SubscriptionType.SMS;
            mediumLabel.setText("Phone number");
        });

        buttonGroup = new ButtonGroup();
        buttonGroup.add(emailButton);
        buttonGroup.add(phoneButton);

        mediumField = new JTextField();

        confirmationButton = new JButton("Confirm");
        confirmationButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(infoLabel)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(emailButton)
                                .addComponent(phoneButton))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mediumLabel)
                                .addComponent(mediumField))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(confirmationButton)
                                .addComponent(backButton))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(infoLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(emailButton)
                                .addComponent(phoneButton))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(mediumLabel)
                                .addComponent(mediumField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(confirmationButton)
                                .addComponent(backButton))
        );

        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 300));
    }
}
