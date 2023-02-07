package com.revature.controllers;

import com.revature.model.Employee;
import com.revature.model.Ticket;
import com.revature.repository.EmployeeRepository;
import com.revature.repository.TicketRepository;
import com.revature.service.EmployeeService;
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
 *         backend server that allows employees to submit {@link Ticket} 
 *         using an account that is in the the database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeLogin}</li>
 *         <li>{@link EmployeeRegister}</li>
 *         <li>{@link ManagerLogin}</li>
 *         <li>{@link ManagerRegister}</li>
 *         <li>{@link ProcessTicket}</li>
 *         </ul>
 *         for more information on other contexts.
 */
public class SubmitTicket implements HttpHandler {

    // Global variable for the Return Codes
    private static final int RCODE_SUCCESSFUL = 200;

    private static final String ADD = "ADD";

    // Maintain the information on the employee in the backend 
    // so that the client doesn't have to send it repeatedly
    private Employee employee = new Employee();
    
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
            File file = new File("ersproject/src/main/java/com/revature/view/submitTicket.html"); 
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
            // Get employee information from the client
            ObjectMapper mapper = new ObjectMapper();
            
            StringBuilder textBuilder = StringBuilderUtil.buildString(exchange);

            Employee findEmployee = mapper.readValue(textBuilder.toString(), Employee.class);

            // Verify it by sending a query to the database
            EmployeeService service = new EmployeeService(new EmployeeRepository());
            String clause = "email = "+"\'"+findEmployee.getEmail()+"\'";
            Employee newEmployee = service.getObjectsWhere(clause);

            // Save the employe information to 
            // this instance of the controller
            // for later use
            this.employee.setEmployeeID(newEmployee.getEmployeeID());
            this.employee.setEmail(newEmployee.getEmail());
            this.employee.setPassword(newEmployee.getPassword());

            // Get ticket information from the database
            TicketService serviceTicket = new TicketService(new TicketRepository());
            clause = "employeeID = "+"\'"+employee.getEmployeeID()+"\'";
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
    * This method process HTTP <code>POST</code> Requests
    * </p>
    * 
    * @param exchange the exchange object the request information is sent 
    * through
    */
    private void putRequest(HttpExchange exchange) {
        try {
            // Get ticket information from the client
            ObjectMapper mapper = new ObjectMapper();
            
            StringBuilder textBuilder = StringBuilderUtil.buildString(exchange);
 
            Ticket newTicket = mapper.readValue(textBuilder.toString(), Ticket.class);

            // Make sure the ticket is initialized properly
            newTicket = new Ticket(newTicket);
            String jsonString = mapper.writeValueAsString(newTicket);

            // Send it to the database
            TicketService serviceTicket = new TicketService(new TicketRepository());
            serviceTicket.saveToRepositoryFK(jsonString, this.employee);

            OutputStream os = exchange.getResponseBody();
            String response = ADD;
            exchange.sendResponseHeaders(RCODE_SUCCESSFUL, response.getBytes().length);
            os.write(response.getBytes());
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
    private void deleteRequest(HttpExchange exchange) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            StringBuilder textBuilder = StringBuilderUtil.buildString(exchange);
 
            Ticket newTicket = new Ticket();
            newTicket.setStatus(textBuilder.toString().replace('\"', ' ').trim());

            TicketService serviceTicket = new TicketService(new TicketRepository());
            String clause = "employeeID = "+"\'"+this.employee.getEmployeeID()+"\' AND status ="+"\'"+newTicket.getStatus()+"\' ORDER BY employeeID ASC";
            List<Ticket> tickets = serviceTicket.getAllObjectsWhere(clause);

            String ticketsJson = mapper.writeValueAsString(tickets);

            OutputStream os = exchange.getResponseBody();
            String response = ticketsJson.toString();
            exchange.sendResponseHeaders(RCODE_SUCCESSFUL, response.getBytes().length);
            os.write(response.getBytes());
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
            case "DELETE":
                deleteRequest(exchange);
                break;
            default:
                break;
        }
    }
}
