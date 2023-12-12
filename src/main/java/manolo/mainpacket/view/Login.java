package manolo.mainpacket.view;

import manolo.mainpacket.model.LoginTexts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Login extends JFrame{

    int amountPanels = 3;
    int amountLabels = 4;
    int amountTextFields = 3;
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
        createTextField(amountTextFields);
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
        panels.get(1).setLayout(new GridLayout(amountLabels + amountTextFields, 2));

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
            panels.get(1).add(labels.get(i));
            panels.get(1).add(textFields.get(i-1));
        }
    }

    private void addPassword() {
        panels.get(1).add(new JLabel("Contraseña:"));
        panels.get(1).add(passwordFields.get(0));
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

    private void createLabels(int max) {
        for (int i = 0; i < max; i++) {
            JLabel label = new JLabel(model.getTextsList().get(i));
            labels.add(label);
        }
    }

    private void createButtons(int max) {
        for (int i = 0; i < max; i++) {
            JButton button = new JButton(model.getTextsList().get(4));
            buttons.add(button);
        }
    }

    private void settings() {
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        this.setSize(600, 500);
        this.setTitle("Login Bufete");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}