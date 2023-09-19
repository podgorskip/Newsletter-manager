package actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActivityForm extends JFrame implements ActionListener {
    private JButton signUpButton, resignButton, showCurrentNewsButton, backButton;
    private final ActivityCallback activityCallback;

    public ActivityForm(ActivityCallback activityCallback) {
        this.activityCallback = activityCallback;
        initForm();
    }

    public void displayActivityForm() {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == signUpButton) {
            activityCallback.onActivityChosen(Actions.ADD_SUBSCRIBER);
        } else if (event.getSource() == resignButton) {
            activityCallback.onActivityChosen(Actions.REMOVE_SUBSCRIBER);
        } else if (event.getSource() == showCurrentNewsButton) {
            activityCallback.onActivityChosen(Actions.SHOW_NEWS);
        } else if (event.getSource() == backButton) {
            dispose();
        }
    }

    private void initForm() {
        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        setLayout(layout);
        setTitle("Activity manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel signUpLabel = new JLabel("Sign up for the newsletter actions.subscription");
        signUpButton = new JButton("Sign up");
        signUpButton.addActionListener(this);

        JLabel resignLabel = new JLabel("Resign from the newsletter actions.subscription");
        resignButton = new JButton("Resign");
        resignButton.addActionListener(this);

        JLabel showCurrentNewsLabel = new JLabel("Show current news");
        showCurrentNewsButton = new JButton("Show");
        showCurrentNewsButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.addActionListener(this);


        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(signUpLabel)
                                .addComponent(resignLabel)
                                .addComponent(showCurrentNewsLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(signUpButton)
                                .addComponent(resignButton)
                                .addComponent(showCurrentNewsButton)
                                .addComponent(backButton))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(signUpLabel)
                                .addComponent(signUpButton))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(resignLabel)
                                .addComponent(resignButton))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(showCurrentNewsLabel)
                                .addComponent(showCurrentNewsButton))
                        .addGap(50)
                        .addComponent(backButton)
                        .addGap(20)
        );

        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(350, 200));
    }
}
