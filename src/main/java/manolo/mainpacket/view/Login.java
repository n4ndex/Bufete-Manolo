package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.model.viewmodels.LoginTexts;
import manolo.mainpacket.model.viewmodels.LoginTexts_en;
import manolo.mainpacket.model.viewmodels.LoginTexts_es;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class Login extends JFrame {

    // Constants for the number of panels, labels, text fields, password fields, and buttons
    private final int AMOUNT_PANELS = 3;
    private final int AMOUNT_LABELS = 3;
    private final int AMOUNT_TEXT_FIELDS = 1;
    private final int AMOUNT_PASSWORD_FIELDS = 1;
    private final int AMOUNT_BUTTONS = 1;

    // Lists to store panels, labels, text fields, password fields, and buttons
    ArrayList<JPanel> panels = new ArrayList<>();
    ArrayList<JLabel> labels = new ArrayList<>();
    ArrayList<JTextField> textFields = new ArrayList<>();
    ArrayList<JPasswordField> passwordFields = new ArrayList<>();
    ArrayList<JButton> buttons = new ArrayList<>();

    // Model for login texts
    private LoginTexts model;
    private LoginTexts_es modelEs = new LoginTexts_es();
    private LoginTexts_en modelEn = new LoginTexts_en();

    // ComboBox for language selection
    JComboBox<Object> languageComboBox;

    // Constructor
    public Login(String language) {
        switchLanguage(language);

        // Create elements of the window
        createPanels();
        createLabels();
        createDniField();
        createPasswordField();
        createButtons();
        createLanguageComboBox();

        // Display elements in the window
        ordinateAll();
        settings();
    }

    private void switchLanguage(String language) {
        switch (language) {
            case "espanol":
                model = modelEs;
                break;
            case "english":
                model = modelEn;
                break;
        }
    }

    // Method to organize all elements in the window
    private void ordinateAll() {
        combinePanels();
        addTitle();
        addLanguageComboBox();
        addTLTV();
        addButtons();
    }

    // Method to add the title label to the first panel
    private void addTitle() {
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        JLabel titleLabel = labels.getFirst();
        titleLabel.setFont(titleFont);
        panels.getFirst().add(titleLabel);
    }

    // Method to add language selection ComboBox to the first panel
    private void addLanguageComboBox() {
        panels.getFirst().add(languageComboBox);
    }

    // Method to combine and set layouts for specific panels
    private void combinePanels() {
        panels.get(1).setLayout(new GridLayout(0, 1, 5, 5));

        panels.get(2).setLayout(new FlowLayout(FlowLayout.CENTER));

        panels.get(2).add(panels.get(1));

        this.add(panels.get(2), BorderLayout.CENTER);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        flowLayout.setHgap(75);  // Set horizontal gap (margin)
        panels.getFirst().setLayout(flowLayout);

        this.add(panels.getFirst(), BorderLayout.NORTH);
    }

    // Method to add labels, text fields, password fields, and register link to the second panel
    private void addTLTV() {
        for (int i = 1; i < AMOUNT_LABELS; i++) {
            if (i != AMOUNT_LABELS - 1) {
                panels.get(1).add(labels.get(i));
                panels.get(1).add(textFields.getFirst());
            } else {
                addPassword();
                addRegisterLink();
            }
        }
    }

    // Method to add password label and password field to the second panel
    private void addPassword() {
        panels.get(1).add(new JLabel(model.getTextsList().get(2)));
        panels.get(1).add(passwordFields.getFirst());
    }

    // Method to add register link to the second panel
    private void addRegisterLink() {
        JLabel registerLabel = new JLabel(model.getTextsList().get(3));
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labels.add(registerLabel);
        panels.get(1).add(registerLabel);
    }

    // Method to add buttons to the second panel
    private void addButtons() {
        panels.get(1).add(buttons.getFirst());
    }

    // Method to add the language selection ComboBox to the first panel
    private void createLanguageComboBox() {
        String espanolIconPath = model.getESPANOL_ICON_PATH();
        String englishIconPath = model.getENGLISH_ICON_PATH();

        ImageIcon espanolIcon = new ImageIcon(new ImageIcon(espanolIconPath).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon englishIcon = new ImageIcon(new ImageIcon(englishIconPath).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        Object[] languageOptions = {espanolIcon, englishIcon};
        languageComboBox = new JComboBox<>(languageOptions);
    }

    // Method to create and initialize panels with background color
    private void createPanels() {
        for (int i = 0; i < AMOUNT_PANELS; i++) {
            JPanel panel = new JPanel();
            panels.add(panel);
        }
    }

    // Method to create and initialize text fields with a character limit filter
    private void createDniField() {
        for (int i = 0; i < AMOUNT_TEXT_FIELDS; i++) {
            JTextField dniField = new JTextField(9);

            // Filter for a maximum of 9 characters
            ((AbstractDocument) dniField.getDocument()).setDocumentFilter(new DocumentFilter() {
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

            textFields.add(dniField);
        }
    }

    // Method to create and initialize password fields
    private void createPasswordField() {
        for (int i = 0; i < AMOUNT_PASSWORD_FIELDS; i++) {
            JPasswordField passwordField = new JPasswordField(20);
            passwordFields.add(passwordField);
        }
    }

    // Method to create and initialize labels
    private void createLabels() {
        for (int i = 0; i < AMOUNT_LABELS; i++) {
            JLabel label = new JLabel(model.getTextsList().get(i));
            labels.add(label);
        }
    }

    // Method to create and initialize buttons
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

    // Method to set up frame settings
    private void settings() {
        ImageIcon icon = new ImageIcon(model.getICON_PATH());
        setIconImage(icon.getImage());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screen.height;
        int screenWidth = screen.width;

        this.setSize(screenWidth / 4, screenHeight - 450);
        this.setTitle(model.getTitle());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        // Focus on the JTextField after displaying the window
        EventQueue.invokeLater(() -> {
            textFields.getFirst().requestFocusInWindow();
        });
    }
}
