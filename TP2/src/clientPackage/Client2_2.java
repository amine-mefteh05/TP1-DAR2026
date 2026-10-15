package clientPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commonPackage.Operation;

public class Client2_2 {

    static final Pattern SYNTAXE = Pattern.compile(
            "^\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*$"
    );

    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté.");

            Socket socket = new Socket("localhost", 1234);
            System.out.println("Je suis un client connecté.");

            // Création des flux d'objets
            ObjectOutputStream oos = new ObjectOutputStream(
                    socket.getOutputStream()
            );
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(
                    socket.getInputStream()
            );

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print(
                        "Donnez une opération (ex : 55 * 25) ou quit : "
                );

                String saisie = sc.nextLine().trim();

                if (saisie.equalsIgnoreCase("quit")) {
                    break;
                }

                Matcher m = SYNTAXE.matcher(saisie);

                if (!m.matches()) {
                    System.out.println(
                            "Syntaxe invalide, rien n'est envoyé."
                    );
                    continue;
                }

                // Création de l'objet Operation
                double a = Double.parseDouble(m.group(1));
                char operateur = m.group(2).charAt(0);
                double b = Double.parseDouble(m.group(3));

                Operation op = new Operation(a, operateur, b);

                // Envoi de l'objet au serveur
                oos.writeObject(op);
                oos.flush();

                // Réception de la réponse
                Object reponse = ois.readObject();
                System.out.println("Résultat : " + reponse);
            }

            // Signal de fin de communication
            oos.writeObject("FIN");
            oos.flush();

            System.out.println("Fin de la communication.");

            sc.close();
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "Erreur côté client : " + e.getMessage()
            );
        }
    }
}