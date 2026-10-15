package ClientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Je suis un client pas encore connecté...");

        Socket socket = new Socket("127.0.0.1", 1233);

        System.out.println("Je suis un client connecté");

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        int x;
        do {
            System.out.print("Donnez un entier x (0 pour arrêter) : ");
            x = scanner.nextInt();

            // Envoyer x au serveur (0 compris, pour que le serveur s'arrête aussi)
            dos.writeInt(x);

            if (x != 0) {
                int result = dis.readInt();
                System.out.println("Résultat reçu du serveur : " + result);
            }
        } while (x != 0);

        System.out.println("Fin de la communication.");

        dis.close();
        dos.close();
        socket.close();
        scanner.close();
    }
}