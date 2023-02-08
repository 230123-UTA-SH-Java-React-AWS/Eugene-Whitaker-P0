package com.revature.controllers;

import com.revature.model.Ticket;
import com.revature.repository.TicketRepository;
import com.revature.service.TicketService;
import com.revature.utils.StringBuilderUtil;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class uses {@link HttpHandler} to create a context for our
 *         backend server that allows manager to process {@link Ticket} 
 *         using an account that is in the the database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeLogin}</li>
 *         <li>{@link EmployeeRegister}</li>
 *         <li>{@link ManagerLogin}</li>
 *         <li>{@link ManagerRegister}</li>
 *         <li>{@link SubmitTicket}</li>
 *         </ul>
 *         for more information on other contexts.
 */
public class ProcessTicket implements HttpHandler {

    // Global variable for the Return Codes
    private static final int RCODE_SUCCESSFUL = 200;
    private static final int RCODE_CLIENT_ERROR = 400;

    private static final String UPDATED = "UPDATED";
    private static final String NOTUPDATED = "NOTUPDATED";
    
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
            File file = new File("ersproject/src/main/java/com/revature/view/processTicket.html"); 
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
            ObjectMapper mapper = new ObjectMapper();

            // Get ticket information from the database
            TicketService serviceTicket = new TicketService(new TicketRepository());
            String clause = "pending = true";
            List<Ticket> tickets = serviceTicket.getAllObjectsWhere(clause);
            String response = mapper.writeValueAsString(tickets);

            // Send it to the client
            exchange.sendResponseHeaders(RCODE_SUCCESSFUL, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * <p>
    * This method process HTTP <code>PUT</code> Requests
    * </p>
    * 
    * @param exchange the exchange object the request information is sent 
    * through
    */
    private void putRequest(HttpExchange exchange) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Get ticket information from the client
            StringBuilder textBuilder = StringBuilderUtil.buildString(exchange);

            Ticket newTicket = mapper.readValue(textBuilder.toString(), Ticket.class);

            // If ticket has not been processed yet then
            // Update the database with the new information
            TicketService serviceTicket = new TicketService(new TicketRepository());
            String clause = "ID ="+"\'"+newTicket.getTicketID()+"\'";
            Ticket ticket = serviceTicket.getObjectsWhere(clause);

            OutputStream os = exchange.getResponseBody();
            String response;
            if (ticket.getPending() == true) {
                serviceTicket.updateRepository(textBuilder.toString());
                response = UPDATED;
                exchange.sendResponseHeaders(RCODE_SUCCESSFUL, response.getBytes().length);
                os.write(response.getBytes());
            } else {
                response = NOTUPDATED;
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
            case "PUT":
                putRequest(exchange);
                break;
            default:
                break;
        }
    }
}
