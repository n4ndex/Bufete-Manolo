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

    // Constants for the number of panels, labels, text fields, etc.
    private final int AMOUNT_PANELS = 3;
    private final int AMOUNT_LABELS = 5;
    private final int AMOUNT_TEXT_FIELDS = 3;
    private final int AMOUNT_PASSWORD_FIELDS = 1;
    private final int AMOUNT_BUTTONS = 1;
    private final int AMOUNT_CHECK_BOX = 1;
    private final int AMOUNT_COMBOS = 1;

    // Lists to store Swing components
    private ArrayList<JPanel> panels = new ArrayList<>();
    private ArrayList<JLabel> labels = new ArrayList<>();
    private ArrayList<JTextField> textFields = new ArrayList<>();
    private ArrayList<JPasswordField> passwordFields = new ArrayList<>();
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<JCheckBox> checks = new ArrayList<>();
    private ArrayList<JComboBox> combos = new ArrayList<>();

    // Model for text content
    private RegisterTexts model = new RegisterTexts();

    // Reference to the MainController
    private MainController mainController;

    public Register(MainController mainController) {
        this.mainController = mainController;
        // Create Swing components
        createPanels();
        createLabels();
        createTextField();
        createPasswordField();
        createButtons();
        createCheckBox();
        createCombos();

        // Arrange and display components in the window
        organizeAll();
        settings();
    }

    private void organizeAll() {
        combinePanels();
        addTitle();
        addTLTV();
        addButtons();
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
        for (int i = 1; i < AMOUNT_LABELS; i++) {
            if (i != AMOUNT_LABELS - 1) {
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
        panels.get(1).add(combos.getFirst());
    }

    private void addPassword() {
        panels.get(1).add(new JLabel(model.getTextsList().get(4)));
        panels.get(1).add(passwordFields.getFirst());
    }

    private void addRegisterLink() {
        JLabel loginLabel = new JLabel(model.getTextsList().get(6));
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labels.add(loginLabel);
        panels.get(1).add(loginLabel);
    }

    private void addButtons() {
        panels.get(1).add(buttons.getFirst());
    }

    private void addCheckBox() {
        panels.get(1).add(checks.getFirst());
    }

    private void addTitle() {
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        JLabel titleLabel = labels.getFirst();
        titleLabel.setFont(titleFont);
        panels.getFirst().add(titleLabel);
    }

    private void createPanels() {
        for (int i = 0; i < AMOUNT_PANELS; i++) {
            JPanel panel = new JPanel();
            panels.add(panel);
        }
    }

    private void createTextField() {
        for (int i = 0; i < AMOUNT_TEXT_FIELDS; i++) {
            JTextField textField;
            if (i == 0) {
                textField = new JTextField(9);  // Apply filter to DNI
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

    private void createPasswordField() {
        for (int i = 0; i < AMOUNT_PASSWORD_FIELDS; i++) {
            JPasswordField passwordField = new JPasswordField(20);
            passwordFields.add(passwordField);
        }
    }

    private void createLabels() {
        for (int i = 0; i < AMOUNT_LABELS; i++) {
            JLabel label = new JLabel(model.getTextsList().get(i));
            labels.add(label);
        }
    }

    private void createButtons() {
        for (int i = 0; i < AMOUNT_BUTTONS; i++) {
            JButton button = new JButton(model.getTextsList().getLast());
            buttons.add(button);
            buttons.getFirst().setEnabled(false);

            Font buttonFont = new Font("Arial", Font.BOLD, 16);
            button.setFont(buttonFont);
            button.setBackground(new Color(255, 215, 0));  // Set button background color to gold
        }
    }

    private void createCheckBox() {
        for (int i = 0; i < AMOUNT_CHECK_BOX; i++) {
            JCheckBox checkBox = new JCheckBox(model.getTextsList().get(7));
            checks.add(checkBox);
        }
    }

    private void createCombos() {
        mainController.getMainConnection().openConnection(mainController.getMainConnectionModel().getMYSQL_URL(),
                mainController.getMainConnectionModel().getMYSQL_DATABASE(),
                mainController.getMainConnectionModel().getMYSQL_USERNAME(),
                mainController.getMainConnectionModel().getPASSWORD());

        for (int i = 0; i < AMOUNT_COMBOS; i++) {
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

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screen.height;
        int screenWidth = screen.width;

        this.setSize(screenWidth / 4, screenHeight - 200);
        this.setTitle(model.getTitle());
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
