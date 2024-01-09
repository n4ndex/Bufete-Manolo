package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.RegisterTexts;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class Register extends JFrame {

    int amountPanels = 3;
    int amountLabels = 5;
    int amountTextFields = 3;
    int amountPasswordFields = 1;
    int amountButtons = 1;
    int amountCheckBox = 1;
    int amountCombos = 1;

    ArrayList<JPanel> panels = new ArrayList<>();
    ArrayList<JLabel> labels = new ArrayList<>();
    ArrayList<JTextField> textFields = new ArrayList<>();
    ArrayList<JPasswordField> passwordFields = new ArrayList<>();
    ArrayList<JButton> buttons = new ArrayList<>();
    ArrayList<JCheckBox> checks = new ArrayList<>();
    ArrayList<JComboBox> combos = new ArrayList<>();
    RegisterTexts model = new RegisterTexts();
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
        createCombos(amountCombos);

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
        panels.get(1).setLayout(new GridLayout(0, 1, 5, 5));

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

    private void addCombo() {
        panels.get(1).add(new JLabel(model.getTextsList().get(5)));
        panels.get(1).add(combos.get(0));
    }

    private void addPassword() {
        panels.get(1).add(new JLabel(model.getTextsList().get(4)));
        panels.get(1).add(passwordFields.get(0));

    }

    private void addRegisterLink() {
        JLabel loginLabel = new JLabel(model.getTextsList().get(6));
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
            JTextField textField;
            if (i == 0) {
                textField = new JTextField(9);  // Aplicar filtro al DNI
                ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                        if ((fb.getDocument().getLength() + string.length()) <= 9) {
                            super.insertString(fb, offset, string, attr);
                        }
                    }

                    @Override
                    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                        if ((fb.getDocument().getLength() + text.length() - length) <= 9) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });
            } else {
                textField = new JTextField(10);
            }

            textFields.add(textField);
        }
    }

    private void createPasswordField(int max) {
        for (int i = 0; i < max; i++) {
            JPasswordField passwordField = new JPasswordField(20);
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
            buttons.get(0).setEnabled(false);

            Font buttonFont = new Font("Arial", Font.BOLD, 16);
            button.setFont(buttonFont);
            button.setBackground(new Color(255, 215, 0));  // Dorado
        }
    }


    private void createCheckBox(int max) {
        for (int i = 0; i < max; i++) {
            JCheckBox checkBox = new JCheckBox(model.getTextsList().get(7));
            checks.add(checkBox);
        }
    }

    private void createCombos(int max) {
        mainController.getMainConnection().openConnection(mainController.getMainConnectionModel().getMYSQL_URL(), mainController.getMainConnectionModel().getMYSQL_DATABASE(), mainController.getMainConnectionModel().getMYSQL_USERNAME(), mainController.getMainConnectionModel().getPASSWORD());

        for (int i = 0; i < max; i++) {
            JComboBox<String> combo = new JComboBox<>(mainController.getMainConnection().getLawyerNames().toArray(new String[0]));
            combo.insertItemAt("", 0);
            combo.setSelectedIndex(0);
            combos.add(combo);
        }

        mainController.getMainConnection().closeConnection();
    }

    private void settings() {
        ImageIcon icon = new ImageIcon("target/classes/assets/icon_app.jpg");
        setIconImage(icon.getImage());
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        this.setSize(width / 4, height - 200);
        this.setTitle("Register Bufete");
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}