package ipcpop3;

import ipcpop3.Utils.StreamUtil;

import java.io.IOException;

public class TransactionState extends POP3State {
    public TransactionState(POP3Context context) {
        super(context);
    }

    public void apop(String[] parameters) throws IOException {
        System.out.println("APOP Transaction");
        this.context.answer("-ERR a mailbox is already opened");
    }

    public void stat(String[] parameters) throws IOException {
        System.out.println("STAT Transaction");

        int nbMessage = this.context.getMailbox().getMailCount(); // nombre de message dans le mail
        int sizeMessage = this.context.getMailbox().getMailboxSize(); // taille du fichier en octets

        this.context.answer("+OK" + " " + nbMessage + " " + sizeMessage);
    }

    public void retr(String[] parameters) throws IOException {
        String msgNumber = parameters[1];
        int messageNumber = Integer.parseInt(msgNumber);
        Mail mail = context.getMailbox().getMail(messageNumber);
        try {
            if (mail.getState().equals(MailStateEnum.DELETED)) {
                throw new Exception("mail " + messageNumber + " is deleted");
            }

            String message = mail.toString();

            this.context.answer("+OK " + message.getBytes().length + " bytes follow");
            this.context.answerText(message);

        } catch (Exception e) {

            this.context.answer("-ERR invalid message number");
        }
    }


    public void quit(String[] parameters) throws IOException {
        System.out.println("QUIT Transaction");
        try {
            Mailbox mailbox = context.getMailbox();
            mailbox.write();
            mailbox.close();
            this.context.setRunning(false);

            this.context.answer("+OK POP3 server signing off (" + mailbox.getMailCount() + " message(s) left)");
        } catch (IOException e) {
            e.printStackTrace();

            this.context.answer("-ERR some deleted messages not removed");
        }
    }
}
