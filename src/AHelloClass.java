import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;
import org.quickconnectfamily.json.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

// simulate the network going down, on both ends.
// ex: in the code for the server (java exit method), instead of replying.
// ex: on the client, send the request and exit. System.exit.
@WebServlet(name = "AHelloClass", urlPatterns = {"/hello"})
public class AHelloClass extends HttpServlet{
        //When server responds to user
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            System.out.println("This is the server running!");

            //Receive message from client
            JSONInputStream inFromClient = new JSONInputStream(request.getInputStream());
            //Respond to client
            JSONOutputStream outToClient = new JSONOutputStream(response.getOutputStream());

            HashMap<String, Object> dataMap = null;
            try {
                dataMap = (HashMap) inFromClient.readObject();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            SocketException, and the server simulates disconnecting.
//            System.exit(-1);
//            System.out.println("Did it get this far?");

            System.out.println("Data Received:" + dataMap + " from Bro. Barney's client");
            //overwrites the value "To Servlet" from the user and returns To Client
            dataMap.put("status", "To Client");
            try {
                outToClient.writeObject(dataMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("just sent "+dataMap);
        }

        // when the server receives a request
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            System.out.println("Do this before you go to the url!");
            doPost(request, response);
        }
}
