package com.revature.controllers;

import com.revature.model.Manager;
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
     * This method processes a <code>POST</code> request from the client to register
     * a new manager account.
     * </p>
     * 
     * @param exchange the exchange captured by the server
     */
    private void postRequest(HttpExchange exchange) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            StringBuilder textBuilder = StringBuilderUtil.buildString(exchange);

            Manager newManager = mapper.readValue(textBuilder.toString(), Manager.class);

            ManagerIDService IDservice = new ManagerIDService();
            String clause = "ID = "+"\'"+newManager.getManagerID()+"\'";
            int managerID = IDservice.getObjectsWhere(clause);

            OutputStream os = exchange.getResponseBody();
            String response;
            if (managerID != 0) {
                ManagerService service = new ManagerService();
                clause = "email = "+"\'"+newManager.getEmail()+"\'";
                Manager manager = service.getObjectsWhere(clause);

                if (manager.getEmail() == null) {
                    clause = "ID = "+"\'"+managerID+"\'";
                    manager = service.getObjectsWhere(clause);

                    if (manager.getManagerID() == 0) {
                        service.saveToRepository(textBuilder.toString());
                        exchange.getResponseHeaders().add("Location", "http://localhost:8000/managerLogin");
                        exchange.sendResponseHeaders(RCODE_REDIRECT, -1);
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
            } else {
                response = BADID;
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
     * <p></p>
     * 
     * @param exchange
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
