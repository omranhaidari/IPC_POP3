package ipcpop3;

import java.io.IOException;

public class TransactionState extends POP3State {
    public TransactionState(POP3Context context) {
        super(context);
    }

    public void apop(String username, String password) {
        System.out.println("APOP Transaction");
    }

    public void stat() {
        System.out.println("STAT Transaction");

        int nbMessage = this.context.getMailbox().getMailCount(); // nombre de message dans le mail
        int sizeMessage = this.context.getMailbox().getMailboxSize(); // taille du fichier en octets
        System.out.println("+OK" + " " + nbMessage + " " + sizeMessage);
    }

    public void retr(int messageNumber) {
        Mail mail = context.getMailbox().getMail(messageNumber);
        try {
            if (mail.getState().equals(MailStateEnum.DELETED))
                throw new Exception("mail " + messageNumber + " is deleted");
            String message = mail.toString();
            System.out.println("+OK " + message.length()/8 + " octets follow");
            System.out.println(message);
        } catch (Exception e) {
            System.out.println("-ERR numéro de message invalide");
        }
    }


    public void quit() {
        System.out.println("QUIT Transaction");
        try {
            context.getMailbox().write();
            context.getMailbox().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
