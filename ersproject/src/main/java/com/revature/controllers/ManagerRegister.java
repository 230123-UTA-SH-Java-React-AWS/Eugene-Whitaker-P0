package com.revature.controllers;

import com.revature.App;
import com.revature.model.Manager;
import com.revature.repository.ManagerIDRepository;
import com.revature.repository.ManagerRepository;
import com.revature.service.ManagerIDService;
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
 *         backend server that allows managers to register an account in the
 *         database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeLogin}</li>
 *         <li>{@link EmployeeRegister}</li>
 *         <li>{@link ManagerLogin}</li>
 *         <li>{@link ProcessTicket}</li>
 *         <li>{@link SubmitTicket}</li>
 *         </ul>
 *         for more information on other contexts.
 */
public class ManagerRegister implements HttpHandler {

    // Global variable for the Return Codes
    private static final int RCODE_SUCCESSFUL = 200;
    private static final int RCODE_REDIRECT = 301;
    private static final int RCODE_CLIENT_ERROR = 400;

    private static final String BADID = "BADID";
    private static final String BADEMAIL = "BADEMAIL";

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
            File file = new File("ersproject/src/main/java/com/revature/view/managerRegister.html"); 
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
            // for the managerids table
            // we do the to ensure a manager is registering in with
            // a predetermined managerID
            ManagerIDService IDservice = new ManagerIDService(new ManagerIDRepository());
            String clause = "ID = "+"\'"+newManager.getManagerID()+"\'";
            Integer managerID = IDservice.getObjectsWhere(clause);

            // If the query comes back as null
            // Send BAD Request response code back to browser
            OutputStream os = exchange.getResponseBody();
            String response;
            if (managerID != 0) {
                // Verify it by sending a query to the database
                // for the manager table
                ManagerService service = new ManagerService(new ManagerRepository());
                clause = "email = "+"\'"+newManager.getEmail()+"\'";
                Manager manager = service.getObjectsWhere(clause);

                // If the query comes back as not null
                // Send BAD Request response code back to browser
                if (manager.getEmail() == null) {
                    clause = "ID = "+"\'"+managerID+"\'";
                    manager = service.getObjectsWhere(clause);

                    // If the managerID sent from client 
                    // doesn't matches the database query 
                    // Send BAD Request response code back to browser
                    if (manager.getManagerID() == 0) {
                        service.saveToRepository(textBuilder.toString());
                        exchange.getResponseHeaders().add("Location", "http://localhost:8000/managerLogin");
                        exchange.sendResponseHeaders(RCODE_REDIRECT, -1);
                        App.logger.info("Registration for email: "+newManager.getEmail()+" password: "+newManager.getPassword());
                    } else {
                        response = BADID;
                        exchange.sendResponseHeaders(RCODE_CLIENT_ERROR, response.getBytes().length);
                        os.write(response.getBytes());
                        App.logger.info(BADID+": "+newManager.getManagerID()+" ID already assigned"); 
                    }
                } else {
                    response = BADEMAIL;
                    exchange.sendResponseHeaders(RCODE_CLIENT_ERROR, response.getBytes().length);
                    os.write(response.getBytes());
                    App.logger.info(BADEMAIL+": "+newManager.getEmail());
                }
            } else {
                response = BADID;
                exchange.sendResponseHeaders(RCODE_CLIENT_ERROR, response.getBytes().length);
                os.write(response.getBytes());
                App.logger.info(BADID+": "+newManager.getManagerID()+" ID not currently registered");
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
