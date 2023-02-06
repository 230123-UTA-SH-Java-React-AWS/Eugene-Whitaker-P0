package com.revature.controllers;

import com.revature.model.Employee;
import com.revature.model.Ticket;
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
    private static final int RCODE_SUCCESSFUL = 200;
    // private static final int RCODE_CLIENT_ERROR = 400;

    private static final String ADD = "ADD";

    private Employee employee = new Employee();
    
    // Add Doc
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

    // Add doc
    private void postRequest(HttpExchange exchange) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            StringBuilder textBuilder = StringBuilderUtil.buildString(exchange);

            Employee findEmployee = mapper.readValue(textBuilder.toString(), Employee.class);

            EmployeeService service = new EmployeeService();
            String clause = "email = "+"\'"+findEmployee.getEmail()+"\'";
            Employee newEmployee = service.getObjectsWhere(clause);

            this.employee.setEmployeeID(newEmployee.getEmployeeID());
            this.employee.setEmail(newEmployee.getEmail());
            this.employee.setPassword(newEmployee.getPassword());

            TicketService serviceTicket = new TicketService();
            clause = "employeeID = "+"\'"+employee.getEmployeeID()+"\'";
            List<Ticket> tickets = serviceTicket.getAllObjectsWhere(clause);
            String response = mapper.writeValueAsString(tickets);

            exchange.sendResponseHeaders(RCODE_SUCCESSFUL, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
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
    private void putRequest(HttpExchange exchange) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            StringBuilder textBuilder = StringBuilderUtil.buildString(exchange);
 
            Ticket newTicket = mapper.readValue(textBuilder.toString(), Ticket.class);

            newTicket = new Ticket(newTicket);
            String jsonString = mapper.writeValueAsString(newTicket);

            TicketService serviceTicket = new TicketService();
            serviceTicket.saveToRepository(jsonString, this.employee);

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
            case "PUT":
                putRequest(exchange);
                break;
            default:
                break;
        }
    }
}
