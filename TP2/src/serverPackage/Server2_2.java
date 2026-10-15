package serverPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import commonPackage.Operation;

public class Server2_2 {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println(
                    "Je suis un serveur en attente de la connexion d'un client."
            );

            Socket socket = serverSocket.accept();
            System.out.println("Un client est connecté.");

            // Création des flux d'objets
            ObjectOutputStream oos = new ObjectOutputStream(
                    socket.getOutputStream()
            );
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(
                    socket.getInputStream()
            );

            Object recu = ois.readObject();

            while (recu instanceof Operation) {

                // Conversion de l'objet reçu
                Operation op = (Operation) recu;

                // Extraction des attributs
                double a = op.getOperande1();
                char operateur = op.getOperateur();
                double b = op.getOperande2();

                System.out.println(
                        "Opération reçue : " + a + " "
                                + operateur + " " + b
                );

                // Calcul du résultat
                String reponse = calculer(
                        a, String.valueOf(operateur), b
                );

                // Envoi de la réponse sous forme d'objet String
                oos.writeObject(reponse);
                oos.flush();

                System.out.println("Réponse envoyée : " + reponse);

                // Réception de l'opération suivante ou de "FIN"
                recu = ois.readObject();
            }

            System.out.println("Fin de la communication.");

            socket.close();
            serverSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "Erreur côté serveur : " + e.getMessage()
            );
        }
    }

    static String calculer(double a, String operateur, double b) {
        double r;

        switch (operateur) {
            case "+":
                r = a + b;
                break;

            case "-":
                r = a - b;
                break;

            case "*":
                r = a * b;
                break;

            case "/":
                if (b == 0) {
                    return "Erreur : division par zéro impossible";
                }
                r = a / b;
                break;

            default:
                return "Erreur : opérateur inconnu";
        }

        return formater(a) + " " + operateur + " "
                + formater(b) + " = " + formater(r);
    }

    static String formater(double v) {
        if (v == Math.rint(v) && !Double.isInfinite(v)) {
            return String.valueOf((long) v);
        }

        return String.valueOf(v);
    }
}