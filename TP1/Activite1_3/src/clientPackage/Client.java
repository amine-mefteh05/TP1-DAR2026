package clientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static final int PORT = 1233;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Adresse IP du serveur : ");
        String ip = scanner.next();

        System.out.println("Je suis un client pas encore connecté...");

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(ip), PORT));

        System.out.println("Je suis un client connecté");

        // Menu
        System.out.println("===== Calculatrice =====");
        System.out.println("1. Addition       (x + 5)");
        System.out.println("2. Soustraction   (x - 5)");
        System.out.println("3. Multiplication (x * 5)");
        System.out.println("4. Division       (x / 5)");
        System.out.print("Votre choix : ");
        int choix = scanner.nextInt();

        System.out.print("Donnez un entier x : ");
        int x = scanner.nextInt();

        // Envoyer le choix et x
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(choix);
        dos.writeInt(x);
        System.out.println("Choix et nombre envoyés au serveur : " + choix + ", " + x);

        // Recevoir le résultat
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        double resultat = dis.readDouble();
        System.out.println("Résultat reçu du serveur : " + resultat);

        dis.close();
        dos.close();
        socket.close();
        scanner.close();
    }
}