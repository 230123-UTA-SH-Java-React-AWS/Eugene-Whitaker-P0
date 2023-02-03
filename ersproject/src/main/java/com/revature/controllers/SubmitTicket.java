package com.revature.controllers;

import com.revature.model.Employee;
import com.revature.model.Ticket;
// import com.revature.service.EmployeeService;
import com.revature.service.TicketService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.File;
// import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

// import java.nio.file.Files;
// import java.util.Collections;
// import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class uses {@link HttpHandler} to create a context for our
 *         backend server that allows employees to submit {@link Ticket} using an account that is in the the database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeLogin}</li>
 *         <li>{@link EmployeeRegister}</li>
 *         <li>{@link ManagerLogin}</li>
 *         <li>{@link ManagerRegister}</li>
 *         </ul>
 *         for more information on other contexts.
 */
public class SubmitTicket implements HttpHandler {

    // Global variable for the Return Codes
    protected static final int RCODE_SUCCESSFUL = 200;
    protected static final int RCODE_CLIENT_ERROR = 400;
    

    // Add Doc
    private void getRequest(HttpExchange exchange) {
        try {
            File file = new File("ersproject/src/main/java/com/revature/view/employeeLogin.html"); 
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
     * This method processes a <code>POST</code> request from the client to submit
     * a {@link Ticket} using an existing account.
     * </p>
     * 
     * @param exchange the exchange captured by the server
     */
    private void postRequest(HttpExchange exchange) {
        Employee employee = new Employee("test@test.com", "test");
        employee.setEmployeeID(1);

        // Read in login information from client
        InputStream is = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        try (Reader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {

            int c = 0;

            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }

            // Convert client ticket from json to Ticket object
            Ticket newTicket = mapper.readValue(textBuilder.toString(), Ticket.class);
            // Make sure the Ticket is initialized properly
            newTicket = new Ticket(newTicket);
            String jsonString = mapper.writeValueAsString(newTicket);

            TicketService serviceTicket = new TicketService();
            serviceTicket.saveToRepository(jsonString, employee);

            // add ticket to database 
            // Send OK response back to client
            String response = "Ticket Successfully Added";
            exchange.sendResponseHeaders(RCODE_SUCCESSFUL, response.getBytes().length);OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method handles processing the "verbs" from the client.
     * </p>
     * 
     * @param exchange the exchange captured by the server
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
