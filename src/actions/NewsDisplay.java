package actions;

import core.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to display news from the supplied resources.
 */
public class NewsDisplay extends JFrame implements ActionListener {
    private final Resources resources;
    private JTextField dateField;
    private JTextArea newsArea;
    private JButton confirmationButton, todayDateButton, clearButton, backButton;

    /**
     * Constructs a NewsDisplay object.
     * @param resources object to get data from
     */
    public NewsDisplay(Resources resources) {
        this.resources = resources;
        initForm();
    }

    /**
     * Makes the window visible.
     */
    public void displayNewsInformation() {
        setVisible(true);
    }

    /**
     * Handles actions based on the user choice via a button click.
     * @param event the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == confirmationButton) {
            String date = dateField.getText().trim();

            if (isDateFormatCorrect(date)) {

                try {
                    newsArea.setText(resources.getResource(date));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                JOptionPane.showMessageDialog(null, "The provided data does not align with the expected format.");
                resetFields();
            }

        } else if (event.getSource() == todayDateButton) {

            try {
                String date = new SimpleDateFormat("dd/MM/yy").format(new Date());
                dateField.setText(date);
                newsArea.setText(resources.getResource(date));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (event.getSource() == clearButton) {
            resetFields();

        } else if (event.getSource() == backButton) {
            dispose();
        }
    }

    /**
     * Initializes the window with news.
     */
    private void initForm() {
        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        setLayout(layout);
        setTitle("News display");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel infoLabel = new JLabel("Provide a date to check news from that day.");

        JLabel dateLabel = new JLabel("Date [dd/mm/yy]");
        dateField = new JTextField();

        newsArea = new JTextArea();
        newsArea.setEditable(false);

        confirmationButton = new JButton("Confirm");
        confirmationButton.addActionListener(this);

        todayDateButton = new JButton("Today's date");
        todayDateButton.addActionListener(this);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(infoLabel)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(dateLabel)
                                .addComponent(dateField))
                        .addComponent(newsArea)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(confirmationButton)
                                .addComponent(todayDateButton)
                                .addComponent(clearButton)
                                .addComponent(backButton))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(infoLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(dateLabel)
                                .addComponent(dateField))
                        .addComponent(newsArea)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(confirmationButton)
                                .addComponent(todayDateButton)
                                .addComponent(clearButton)
                                .addComponent(backButton))
        );

        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500,300));
    }

    /**
     * Checks if the provided data's format is correct using regular expressions.
     * @param date the date to have a format checked
     * @return true if the format is dd/mm/yy, false otherwise
     */
    private boolean isDateFormatCorrect(String date) {
        String regex = "^\\d{2}/\\d{2}/\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    /**
     * Resets all the fields.
     */
    private void resetFields() {
        newsArea.setText("");
        dateField.setText("");
    }
}
