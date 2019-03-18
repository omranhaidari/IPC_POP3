package ipcpop3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mailbox {
    private List<Mail> mails;

    public Mailbox(String mailboxName) {
        // TODO Ouvrir le fichier de la boîte mail (avec gestion des erreurs)

        try {
            File file = new File("chemin_vers_mon_fichier");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            try {
                StreamUtil.readLine(reader);

            } finally {
                reader.close();
            }

        } catch (IOException ex) {
            // erreur d'entrée/sortie ou fichier non trouvé
            ex.printStackTrace();
        }
        // TODO Charger tous les mails dans la liste
        mails = new ArrayList<>();
        
    }
}
