package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.model.viewmodels.LoginTexts;

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

    int amountPanels = 3;
    int amountLabels = 3;
    int amountTextFields = 1;
    int amountPasswordFields = 1;
    int amountButtons = 1;

    ArrayList<JPanel> panels = new ArrayList<>();
    ArrayList<JLabel> labels = new ArrayList<>();
    ArrayList<JTextField> textFields = new ArrayList<>();
    ArrayList<JPasswordField> passwordFields = new ArrayList<>();
    ArrayList<JButton> buttons = new ArrayList<>();
    LoginTexts model = new LoginTexts();

    public Login() {
        // Creación de elementos de la ventana
        createPanels(amountPanels);
        createLabels(amountLabels);
        createDniField(amountTextFields);
        createPasswordField(amountPasswordFields);
        createButtons(amountButtons);

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
                addRegisterLink();
            }
        }
    }

    private void addPassword() {
        panels.get(1).add(new JLabel(model.getTextsList().get(2)));
        panels.get(1).add(passwordFields.get(0));
    }

    private void addRegisterLink() {
        JLabel registerLabel = new JLabel(model.getTextsList().get(3));
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labels.add(registerLabel);
        panels.get(1).add(registerLabel);
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

    private void createDniField(int max) {
        for (int i = 0; i < max; i++) {
            JTextField dniField = new JTextField(9);

            // Filtro de 9 caracteres maximo
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

    private void settings() {
        ImageIcon icon = new ImageIcon("target/classes/assets/icon_app.jpg");
        setIconImage(icon.getImage());
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        this.setSize(width / 4, height - 450);
        this.setTitle(model.getTitle());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}