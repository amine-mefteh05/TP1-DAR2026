package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1233);

        System.out.println("Je suis un serveur en attente de la connexion d'un client");

        Socket socket = serverSocket.accept();

        System.out.println("Un client est connecté");

        // Recevoir le nombre envoyé par le client
        DataInputStream dis =
                new DataInputStream(socket.getInputStream());

        int nb = dis.readInt();

        System.out.println("Le serveur a reçu le nombre : " + nb);

        // Calculer nb × 5
        int x = nb * 5;

        System.out.println("Calcul du serveur : " + x);

        // Envoyer le résultat au client
        DataOutputStream dos =
                new DataOutputStream(socket.getOutputStream());

        dos.writeInt(x);

        System.out.println("Le résultat a été envoyé au client.");

        // Fermer les flux et les sockets
        dis.close();
        dos.close();
        socket.close();
        serverSocket.close();
    }
}