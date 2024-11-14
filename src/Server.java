import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 23232;
    static final List<ClientHandler> clients = new ArrayList<>();

    public static Manager calendar = new Manager();
    static void broadcastMessage(String message) {
        System.out.println("Broadcasting message: " + message);
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
    static void updateCalendar(){
        StringBuilder updated = new StringBuilder();
        for (Slot slot: Manager.getSlots()){
            updated.append(slot.getName());
            updated.append("|");
        }
        broadcastMessage("update|"+updated);
    }
    static String getCalendar(){
        StringBuilder updated = new StringBuilder();
        for (Slot slot: Manager.getSlots()){
            updated.append(slot.getName());
            updated.append("|");
        }
        return "update|"+updated;
    }
    static void startServer(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Manager.setSlots();
        startServer();

    }
}
