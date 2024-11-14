import java.io.*;
import java.net.*;
import java.util.List;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 23232;

    private static Socket socket;
    private static  BufferedReader in;

    static void serverListener(){

    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Welcome to our haircuts.com !!!.\nWhat's your name?.");
            String username = userInput.readLine();
            if (username != null){
                out.println("setname "+username);
            }




            Thread receiveThread = new Thread(() -> {
                String serverResponse;
                try {
                    while ((serverResponse = in.readLine()) != null) {
                        if  (serverResponse.startsWith("Failed")){
                            System.out.println(serverResponse);}

                        if  (serverResponse.startsWith("update|")){
                            List<String> names = List.of(serverResponse.split("\\|"));

                            System.out.printf("Our free hours:\n");
                            for (int i=1;i<9;i++){
                                System.out.printf(String.valueOf(i+10-1)+":00 - "+names.get(i)+"\n");
                            }
                        }
                    }
                } catch (IOException e) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    e.printStackTrace();
                }
            });
            receiveThread.start();




            String userInputLine;
            System.out.printf("Hi,"+username+ "\nType 'exit' if you want to leave.\nadd {hour} - to make a reservation\nremove {hour} - to remove your reservation\n");
            while ((userInputLine = userInput.readLine()) != null) {
                if ("exit".equalsIgnoreCase(userInputLine)) {
                    socket.close();
                    break;
                }
                out.println(userInputLine);
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
