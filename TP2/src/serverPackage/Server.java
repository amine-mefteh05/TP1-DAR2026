package serverPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println(
                    "Je suis un serveur en attente de la connexion d'un client."
            );

            Socket socket = serverSocket.accept();
            System.out.println("Un client est connecté.");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter pw = new PrintWriter(
                    socket.getOutputStream(), true
            );

            String operande1 = br.readLine();

            while (operande1 != null && !operande1.equals("quit")) {

                String operateur = br.readLine();
                String operande2 = br.readLine();

                if (operateur == null || operande2 == null) {
                    break;
                }

                System.out.println(
                        "Opération reçue : " + operande1 + " "
                                + operateur + " " + operande2
                );

                String reponse;

                try {
                    double a = Double.parseDouble(operande1);
                    double b = Double.parseDouble(operande2);

                    reponse = calculer(a, operateur, b);

                } catch (NumberFormatException e) {
                    reponse = "Erreur : opérande invalide";
                }

                pw.println(reponse);
                System.out.println("Réponse envoyée : " + reponse);

                operande1 = br.readLine();
            }

            System.out.println("Fin de la communication.");

            socket.close();
            serverSocket.close();

        } catch (IOException e) {
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