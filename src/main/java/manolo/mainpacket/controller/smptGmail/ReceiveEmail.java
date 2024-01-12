package manolo.mainpacket.controller.smptGmail;

import lombok.Getter;
import lombok.SneakyThrows;
import manolo.mainpacket.controller.MainController;

import javax.mail.*;
import java.util.Properties;

@Getter
public class ReceiveEmail{

    private MainController mainController;
    private Properties properties;
    private Session session;
    private static String emailFrom = "";
    private static String passwordFrom = "";

    public ReceiveEmail(MainController mainController){
        this.mainController=mainController;
        emailFrom=mainController.getCurrentUser().getEmail();
        passwordFrom=mainController.getCurrentUser().getPassword();
        properties= new Properties();
    }
    @SneakyThrows
    public void check(){

        // Mail transfer protocol
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.store.protocol", "imaps");
        properties.setProperty("mail.imaps.host", "imap.gmail.com");
        properties.setProperty("mail.imaps.port", "993");
        properties.setProperty("mail.imaps.ssl.enable", "true");

        session= Session.getDefaultInstance(properties);

        // Modeling messages and access protocol
        Store store= session.getStore("imaps");
        store.connect("imap.gmail.com", emailFrom, passwordFrom);

        // Folder object to open email inbox
        Folder folder= store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        Message[] message = folder.getMessages();

        int z=0;
        String from;
        for (int i= message.length-1; i>= message.length-15; i--){
            Message message1= message[i];
            from=removeExtra(message1.getFrom()[0].toString());

            mainController.getEmailView().getTextAreas().get(z).setText(from+ " Asunto: "+ message1.getSubject());
            z++;
        }

        folder.close(true);
        store.close();
    }

    private String removeExtra(String from){
        from= from.replaceFirst("<.*?>", "");
        from="<b>"+ "De: "+ from+"</b>";
        return from;
    }
}