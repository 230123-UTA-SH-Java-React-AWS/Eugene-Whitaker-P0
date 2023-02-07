package com.revature.controllers;

import com.revature.model.Manager;
import com.revature.repository.ManagerRepository;
import com.revature.service.ManagerService;
import com.revature.utils.StringBuilderUtil;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class uses {@link HttpHandler} to create a context for our
 *         backend server that allows managers to login using an account 
 *         that is in the the database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeLogin}</li>
 *         <li>{@link EmployeeRegister}</li>
 *         <li>{@link ManagerRegister}</li>
 *         <li>{@link ProcessTicket}</li>
 *         <li>{@link SubmitTicket}</li>
 *         </ul>
 *         for more information on other contexts.
 */
public class ManagerLogin implements HttpHandler {

    // Global variable for the Return Codes
    private static final int RCODE_SUCCESSFUL = 200;
    private static final int RCODE_REDIRECT = 301;
    private static final int RCODE_CLIENT_ERROR = 400;

    private static final String BADID = "BADID";
    private static final String BADEMAIL = "BADEMAIL";
    private static final String BADPASS = "BADPASS";

    /**
    * <p>
    * This method process HTTP <code>GET</code> Requests
    * </p>
    * 
    * @param exchange the exchange object the request information is sent 
    * through
    */
    private void getRequest(HttpExchange exchange) {
        try {
            File file = new File("ersproject/src/main/java/com/revature/view/managerLogin.html"); 
            OutputStream os = exchange.getResponseBody();
            exchange.sendResponseHeaders(RCODE_SUCCESSFUL, file.length());
            Files.copy(file.toPath(), os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * <p>
    * This method process HTTP <code>POST</code> Requests
    * </p>
    * 
    * @param exchange the exchange object the request information is sent 
    * through
    */
    private void postRequest(HttpExchange exchange) {
        try {
            // Get manager information from the client
            ObjectMapper mapper = new ObjectMapper();
            
            StringBuilder textBuilder = StringBuilderUtil.buildString(exchange);

            Manager newManager = mapper.readValue(textBuilder.toString(), Manager.class);
            
            // Verify it by sending a query to the database
            ManagerService service = new ManagerService(new ManagerRepository());
            String clause = "email = "+"\'"+newManager.getEmail()+"\'";
            Manager manager = service.getObjectsWhere(clause);

            // If the query comes back as null
            // Send BAD Request response code back to browser
            OutputStream os = exchange.getResponseBody();
            String response;
            if (manager.getEmail() != null) {
                // If the managerID sent from client 
                // doesn't matches the database query 
                // Send BAD Request response code back to browser
                if (manager.getManagerID() == newManager.getManagerID()) {
                    // If the password sent from client 
                    // doesn't matches the database query 
                    // Send BAD Request response code back to browser
                    if (manager.getPassword().equals(newManager.getPassword())) {
                        exchange.getResponseHeaders().add("Location", "http://localhost:8000/processTicket?email="+newManager.getEmail()+"&password="+newManager.getPassword()+"&managerid="+newManager.getManagerID());
                        exchange.sendResponseHeaders(RCODE_REDIRECT, -1);
                    } else {
                        response = BADPASS;
                        exchange.sendResponseHeaders(RCODE_CLIENT_ERROR, response.getBytes().length);
                        os.write(response.getBytes());
                    }
                } else {
                    response = BADID;
                    exchange.sendResponseHeaders(RCODE_CLIENT_ERROR, response.getBytes().length);
                    os.write(response.getBytes());
                }
        } else {
            response = BADEMAIL;
            exchange.sendResponseHeaders(RCODE_CLIENT_ERROR, response.getBytes().length);
            os.write(response.getBytes());
        }
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method process HTTP Request method and forwards the exchange 
     * object to its respective destination
     * </p>
     * 
     * @param exchange the exchange object the request information is sent 
     * through
     */
    @Override
    public void handle(HttpExchange exchange) {
        String verb = exchange.getRequestMethod();

        switch (verb) {
            case "GET":
                getRequest(exchange);
                break;
            case "POST":
                postRequest(exchange);
                break;
            default:
                break;
        }
    }
}
