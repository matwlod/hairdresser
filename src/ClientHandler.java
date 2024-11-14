import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addReservation(String command){
        int h;
        try {
            h = Integer.parseInt(command.split(" ")[1]);
        } catch (NumberFormatException e) {
            h = 0;
        }
        boolean res = Server.calendar.makeRes(getName(),h);
        if (res){
            Server.updateCalendar();
        }
        else {
            sendMessage("Failed to add");
        }
    }
    public void removeReservation(String command){
        int h;
        try {
            h = Integer.parseInt(command.split(" ")[1]);
        } catch (NumberFormatException e) {
            h = 0;
        }
        boolean res = Server.calendar.delRes(getName(), h);
        if (res) {
            Server.updateCalendar();
        } else {
            sendMessage("Failed to remove");
        }
    }
    public void setUserName(String command){
        setName(command.split(" ",2)[1]);
        System.out.println("name set for client: " + getName());
        sendMessage(Server.getCalendar());
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received message from client: " + inputLine);
                if (inputLine.startsWith("add")) {
                    addReservation(inputLine);
                }
                else if (inputLine.startsWith("remove")) {
                    removeReservation(inputLine);

                }
                else if (inputLine.startsWith("setname")) {
                    setUserName(inputLine);
                }
                else {
                    sendMessage("Unknown Command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                Server.clients.remove(this);
                System.out.println("Client disconnected: " + socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void sendMessage(String message) {
        out.println(message);
    }
}