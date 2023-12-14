package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.model.viewmodels.RegisterTexts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class Register extends JFrame {

    int amountPanels = 3;
    int amountLabels = 4;
    int amountTextFields = 3;
    int amountPasswordFields = 1;
    int amountButtons = 1;

    ArrayList<JPanel> panels = new ArrayList<>();
    ArrayList<JLabel> labels = new ArrayList<>();
    ArrayList<JTextField> textFields = new ArrayList<>();
    ArrayList<JPasswordField> passwordFields = new ArrayList<>();
    ArrayList<JButton> buttons = new ArrayList<>();
    RegisterTexts model = new RegisterTexts();

    public Register() {
        // Creación de elementos de la ventana
        createPanels(amountPanels);
        createLabels(amountLabels);
        createTextField(amountTextFields);
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
        // Configurar diseño del panel 1 (GridLayout)
        panels.get(1).setLayout(new BoxLayout(panels.get(1), BoxLayout.Y_AXIS));

        // Configurar diseño del panel final (FlowLayout centrado)
        panels.get(2).setLayout(new FlowLayout(FlowLayout.CENTER));

        // Añadir el panel con GridLayout al panel final
        panels.get(2).add(panels.get(1));

        // Añadir el panel final al JFrame
        this.add(panels.get(2), BorderLayout.CENTER);

        // Configurar diseño del panel 0 (centrado)
        panels.get(0).setLayout(new FlowLayout(FlowLayout.CENTER));

        // Añadir el panel del título al JFrame en la parte superior
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
        panels.get(1).add(new JLabel("Contraseña:"));
        panels.get(1).add(passwordFields.get(0));

    }

    private void addRegisterLink() {
        JLabel loginLabel = new JLabel(model.getTextsList().get(4));
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

    private void settings() {
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        this.setSize(width/3, height/4);
        this.setTitle("Login Bufete");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
    }
}