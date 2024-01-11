package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.controller.smptGmail.ReceiveEmail;
import manolo.mainpacket.model.viewmodels.EmailTexts;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class Email extends JFrame implements Runnable{

    private ReceiveEmail receiveEmail;

    private Thread thread;
    private boolean keepChecking;

    // Model for email text
    private EmailTexts model;

    // Lists to store panels, buttons, and labels
    private ArrayList<JPanel> panels;
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> labels;

    // Variable to store the label name option
    private String optionLabelName;

    // Constructor that takes mainController as a parameter
    public Email(MainController mainController) {
        receiveEmail = new ReceiveEmail(mainController);
        this.thread = new Thread();
        this.model = mainController.getEmailModel();
        keepChecking = true;
        creation(); // Initialize and set up the view
        settings(); // Set parameters for the view
    }

    // Method to set view parameters
    private void settings() {
        setSize(model.getNumDimensions().get(0), model.getNumDimensions().get(1));
        setLocation(model.getNumDimensions().get(2), model.getNumDimensions().get(3));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Method to initialize and set up the view
    public void creation() {
        ImageIcon icon = new ImageIcon("target/classes/assets/icon_app.jpg");
        setIconImage(icon.getImage());
        panelsSettings(); // Set up panels
        labelSettings(); // Set up labels
        buttonSettings(); // Set up buttons
        emailSettings(); // Set up email JTextArea
    }

    // Method to set up panel settings
    public void panelsSettings() {
        panels = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            panels.add(new JPanel());
        }

        // PANEL 0
        add(panels.get(0));
        panels.get(0).setLayout(new BorderLayout());
        panels.get(0).add(panels.get(1), BorderLayout.NORTH);
        panels.get(0).add(panels.get(2), BorderLayout.CENTER);
        panels.get(0).add(panels.get(3), BorderLayout.WEST);

        // PANEL 1
        panels.get(1).setBackground(Color.DARK_GRAY);

        // PANEL 2
        panels.get(2).setBackground(Color.GRAY);

        // PANEL 3
        panels.get(3).setLayout(new GridLayout(5, 1));
        panels.get(3).setBackground(Color.DARK_GRAY);
    }

    // Method to set up button settings
    private void buttonSettings() {
        buttons = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            buttons.add(new JButton(model.getTexts().get(i)));
        }

        // BUTTON 0
        buttons.getFirst().setBackground(Color.WHITE);
        buttons.getFirst().setForeground(Color.BLACK);
        buttons.get(0).setFocusPainted(false);
        panels.get(1).add(buttons.get(0));

        // BUTTON 1
        buttons.get(1).setBackground(Color.DARK_GRAY);
        buttons.get(1).setFocusPainted(false);
        buttons.get(1).setBorderPainted(false);
        buttons.get(1).setForeground(Color.WHITE);
        panels.get(3).add(buttons.get(1));
    }

    // Method to set up label settings
    public void labelSettings() {
        labels = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            // Determine the label name based on the index
            if (i == 0) {
                optionLabelName = model.getTexts().get(2);
            } else if (i == 1) {
                optionLabelName = model.getTexts().get(3);
            } else if (i == 2) {
                optionLabelName = model.getTexts().get(4);
            } else {
                optionLabelName = model.getTexts().get(5);
            }

            labels.add(new JLabel(optionLabelName));
            labels.get(i).setForeground(Color.WHITE);
            panels.get(3).add(labels.get(i));
        }
    }

    // Method to set up email JTextArea
    public void emailSettings() {
        JTextArea emailTextArea = new JTextArea();
        emailTextArea.setText("AQUI APARECERAN LOS EMAILS");
        panels.get(2).add(emailTextArea);
    }

    @SneakyThrows
    @Override
    public void run() {

        while(keepChecking){

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
                checkEmails();

        }
    }

    private void checkEmails(){
        receiveEmail.check();
    }
}
