package clientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Je suis un client pas encore connecté...");

        // Étape 4.f : "localhost" remplacé par une adresse IP
        Socket socket = new Socket("127.0.0.1", 1233);

        System.out.println("Je suis un client connecté");

        System.out.print("Donnez un entier x : ");
        int x = scanner.nextInt();

        // Envoyer x au serveur
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(x);

        System.out.println("Nombre envoyé au serveur : " + x);

        // Recevoir le résultat
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int result = dis.readInt();

        System.out.println("Résultat reçu du serveur : " + result);

        dis.close();
        dos.close();
        socket.close();
        scanner.close();
    }
}