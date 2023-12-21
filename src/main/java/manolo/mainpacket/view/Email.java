package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.model.viewmodels.EmailTexts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class Email extends JFrame {

    private EmailTexts model;
    private ArrayList<JPanel> panels;
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> labels;
    private String optionLabelName;

    public Email(EmailTexts emailModel){
        this.model=emailModel;
        creation();
        viewParams();
    }


    private void viewParams() {
        setSize(model.getNumDimensions().get(0), model.getNumDimensions().get(1));
        setLocation(model.getNumDimensions().get(2),model.getNumDimensions().get(3));
        //setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void creation(){
        panelsSettings();
        labelSettings();
        buttonSettings();
        emailSettings();
    }

    public void panelsSettings() {
        panels=new ArrayList<>();

        for(int i=0; i<4; i++){
            panels.add(new JPanel());
        }
        //PANEL 0
        add(panels.get(0));
        panels.get(0).setLayout(new BorderLayout());
        panels.get(0).add(panels.get(1), BorderLayout.NORTH);
        panels.get(0).add(panels.get(2), BorderLayout.CENTER);
        panels.get(0).add(panels.get(3), BorderLayout.WEST);

        //PANEL 1
        panels.get(1).setBackground(Color.DARK_GRAY);

        //PANEL 2
        panels.get(2).setBackground(Color.GRAY);

        //PANEL 3
        panels.get(3).setLayout(new GridLayout(5,1));
        panels.get(3).setBackground(Color.DARK_GRAY);
    }

    private void buttonSettings() {
        buttons= new ArrayList<>();

        for (int i=0; i<2;i++){
            buttons.add(new JButton(model.getTexts().get(i)));
        }
        //BUTTON 0
        buttons.get(0).setBackground(Color.WHITE);
        buttons.get(0).setForeground(Color.BLACK);
        //buttons.get(0).setBorderPainted(false);
        buttons.get(0).setFocusPainted(false);
        panels.get(1).add(buttons.get(0));

        //BUTTON 1
        buttons.get(1).setBackground(Color.DARK_GRAY);
        buttons.get(1).setFocusPainted(false);
        buttons.get(1).setBorderPainted(false);
        buttons.get(1).setForeground(Color.WHITE);
        panels.get(3).add(buttons.get(1));
    }

    public void labelSettings(){
        labels= new ArrayList<>();

        for (int i=0; i<4; i++){
            if (i==0){
            optionLabelName=model.getTexts().get(2);
            }
            if (i==1){
            optionLabelName=model.getTexts().get(3);
            }
            if (i==2){
            optionLabelName=model.getTexts().get(4);
            }
            if (i==3){
            optionLabelName=model.getTexts().get(5);
            }
            labels.add(new JLabel(optionLabelName));
            labels.get(i).setForeground(Color.WHITE);
            panels.get(3).add(labels.get(i));
        }

    }

    public void emailSettings(){
        JTextArea prueba= new JTextArea();
        prueba.setText("AQUI APARECERAN LOS EMAILS");
        panels.get(2).add(prueba);
    }

}
