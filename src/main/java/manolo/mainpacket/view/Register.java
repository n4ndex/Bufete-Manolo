package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.RegisterTexts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class Register extends JFrame {

    private int amountPanels = 3;
    private int amountLabels = 5;
    private int amountTextFields = 3;
    private int amountPasswordFields = 1;
    private int amountButtons = 1;
    private int amountCheckBox = 1;
    private int amountCombos = 1;

    private ArrayList<JPanel> panels = new ArrayList<>();
    private ArrayList<JLabel> labels = new ArrayList<>();
    private ArrayList<JTextField> textFields = new ArrayList<>();
    private ArrayList<JPasswordField> passwordFields = new ArrayList<>();
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<JCheckBox> checks = new ArrayList<>();
    private ArrayList<JComboBox> combos = new ArrayList<>();
    private RegisterTexts model = new RegisterTexts();
    private MainController mainController;

    public Register(MainController mainController) {
        this.mainController = mainController;
        // Creación de elementos de la ventana
        createPanels(amountPanels);
        createLabels(amountLabels);
        createTextField(amountTextFields);
        createPasswordField(amountPasswordFields);
        createButtons(amountButtons);
        createCheckBox(amountCheckBox);

        // Visualización de elementos en ventana
        ordenateAll();
        settings();
    }

    private void ordenateAll() {
        combinePanels();
        addTitle();
        addTLTV();
        addButtons();
    }

    private void addCheckBox() {
        panels.get(1).add(checks.get(0));
    }

    private void addTitle() {
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        JLabel titleLabel = labels.get(0);
        titleLabel.setFont(titleFont);
        panels.get(0).add(titleLabel);
    }

    private void combinePanels() {
        panels.get(1).setLayout(new BoxLayout(panels.get(1), BoxLayout.Y_AXIS));

        panels.get(2).setLayout(new FlowLayout(FlowLayout.CENTER));

        panels.get(2).add(panels.get(1));

        this.add(panels.get(2), BorderLayout.CENTER);

        panels.get(0).setLayout(new FlowLayout(FlowLayout.CENTER));

        this.add(panels.get(0), BorderLayout.NORTH);
    }

    private void addTLTV() {
        for (int i = 1; i < amountLabels; i++) {
            if (i != amountLabels - 1) {
                panels.get(1).add(labels.get(i));
                panels.get(1).add(textFields.get(i - 1));
            } else {
                addPassword();
                addCheckBox();
                addCombo();
                addRegisterLink();
            }
        }
    }

    private void addPassword() {
        panels.get(1).add(new JLabel(model.getTextsList().get(4)));
        panels.get(1).add(passwordFields.get(0));

    }

    private void addRegisterLink() {
        JLabel loginLabel = new JLabel(model.getTextsList().get(5));
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labels.add(loginLabel);
        panels.get(1).add(loginLabel);
    }

    private void addButtons() {
        panels.get(1).add(buttons.get(0));
    }

    private void createPanels(int max) {
        for (int i = 0; i < max; i++) {
            JPanel panel = new JPanel();
            panels.add(panel);
        }
    }

    private void createTextField(int max) {
        for (int i = 0; i < max; i++) {
            JTextField textField = new JTextField(30);
            textFields.add(textField);
        }
    }

    private void createPasswordField(int max) {
        for (int i = 0; i < max; i++) {
            JPasswordField passwordField = new JPasswordField(30);
            passwordFields.add(passwordField);
        }
    }

    private void createLabels(int max) {
        for (int i = 0; i < max; i++) {
            JLabel label = new JLabel(model.getTextsList().get(i));
            labels.add(label);
        }
    }

    private void createButtons(int max) {
        for (int i = 0; i < max; i++) {
            JButton button = new JButton(model.getTextsList().get(model.getTextsList().size() - 1));
            buttons.add(button);
        }
    }

    private void createCheckBox(int max) {
        for (int i = 0; i < max; i++) {
            JCheckBox checkBox = new JCheckBox(model.getTextsList().get(6));
            checks.add(checkBox);
        }
    }

    private void settings() {
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height + 200;
        int width = pantalla.width;
        this.setSize(width / 3, height / 3);
        this.setTitle("Register Bufete");
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}