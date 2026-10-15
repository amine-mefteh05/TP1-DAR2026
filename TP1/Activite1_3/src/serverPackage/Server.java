package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static final int PORT = 1233;

    public static void main(String[] args) throws IOException {

        // Adresse IP réelle de la machine (ou passée en argument)
        InetAddress adresse;
        if (args.length > 0) {
            adresse = InetAddress.getByName(args[0]);
        } else {
            adresse = InetAddress.getLocalHost();
        }

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(adresse, PORT));

        System.out.println("Serveur en attente sur " + adresse.getHostAddress() + ":" + PORT);

        // Le serveur accepte 2 clients (comme en Activité 1-2)
        for (int i = 1; i <= 2; i++) {
            Socket socket = serverSocket.accept();
            System.out.println("Le client " + i + " est connecté : "
                    + socket.getInetAddress().getHostAddress());
            new ClientHandler(socket, i).start();
        }

        serverSocket.close();
    }
}

// Un thread par client
class ClientHandler extends Thread {

    private final Socket socket;
    private final int numero;

    ClientHandler(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            int choix = dis.readInt();
            int x = dis.readInt();
            System.out.println("Client " + numero + " : choix = " + choix + ", x = " + x);

            double resultat = 0;
            switch (choix) {
                case 1: resultat = x + 5; break;
                case 2: resultat = x - 5; break;
                case 3: resultat = x * 5; break;
                case 4: resultat = x / 5.0; break;
                default:
                    System.out.println("Client " + numero + " : choix invalide");
            }

            System.out.println("Client " + numero + " : résultat = " + resultat);
            dos.writeDouble(resultat);
            System.out.println("Client " + numero + " : résultat envoyé");

            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}