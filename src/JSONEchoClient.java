import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class JSONEchoClient {
    public static void main(String[] args){
        JSONEchoClient theClient = new JSONEchoClient();
        theClient.go();
    }

    private void go() {
        while(true){
            try {
                Scanner systemInScanner = new Scanner(System.in);
                System.out.printf("Enter the message to send to the server.\n");
                String messageForServlet = systemInScanner.nextLine();

                URL url = new URL("http://localhost:8080/hello");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);//allows POST
                JSONOutputStream outToServer = new JSONOutputStream(connection.getOutputStream());

                HashMap<String, Object> request = new HashMap<>();
                request.put("status", "To Servlet");
                request.put("message", messageForServlet);

                outToServer.writeObject(request);

                JSONInputStream inFromServer = new JSONInputStream(connection.getInputStream());
                HashMap<String, Object> response = (HashMap<String, Object>) inFromServer.readObject();
                if (response.get("status").equals("To Client")) {
                    System.out.println("Sent request: " + request + "and got response " + response);
                } else {
                    System.out.println("Oops. got " + response);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
