package ipcpop3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TransactionState extends POP3State {
    public TransactionState(POP3Context context) {
        super(context);
    }

    public void apop() {
        System.out.println("APOP Transaction");
    }

    public void stat() {
        System.out.println("STAT Transaction");
        int nbMessage = 0;
        // TODO : Récupération du nombre de messages par lecture du fichier
        // TODO : Voir Mail pour la lecture d'un fichier et la récupération du nombre de .<CR><LF>
        try {
//            int nbLigne = 0;
//            String ligne = null;
            File file = new File("chemin_vers_mon_fichier");
            BufferedReader reader = new BufferedReader(new FileReader(file));
//            try {
//                while((ligne = reader.readLine()) != null) {
//                    nbLigne++;
//                }
//            } finally {
//                reader.close();
//            }

            long sizeMessage = file.length(); // taille du fichier en octets
            System.out.println("+OK" + " " + nbMessage + " " + sizeMessage);
        } catch (IOException ex) {
            // erreur d'entrée/sortie ou fichier non trouvé
            ex.printStackTrace();
        }
    }

    public void retr() {
        System.out.println("RETR Transaction");
    }

    public void quit() {
        System.out.println("QUIT Transaction");
    }
}
