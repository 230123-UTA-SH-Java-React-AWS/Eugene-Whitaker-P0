package com.revature.controllers;

import com.revature.model.Employee;
import com.revature.service.EmployeeService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class uses {@link HttpHandler} to create a context for our
 *         backend server that allows employees to login using an account that
 *         is in the the database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeRegister}</li>
 *         <li>{@link ManagerLogin}</li>
 *         <li>{@link ManagerRegister}</li>
 *         </ul>
 *         for more information on other contexts.
 */
public class EmployeeLogin implements HttpHandler {

    // Global variable for the Return Codes
    protected static final int RCODE_SUCCESSFUL = 200;
    protected static final int RCODE_CLIENT_ERROR = 400;

    /**
     * <p>
     * This method processes a <code>POST</code> request from the client to login
     * using an existing account.
     * </p>
     * 
     * @param exchange the exchange captured by the server
     */
    private void getRequest(HttpExchange exchange) {
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

            // Convert client login from json to Employee object
            Employee newEmployee = mapper.readValue(textBuilder.toString(), Employee.class);

            // Get all Employees currently in database
            EmployeeService service = new EmployeeService();
            List<Employee> listEmployees = service.getAllEmployees();

            // Search if client login email is in database
            // If it is send a Back Request RCODE back to client
            List<String> listEmails = new ArrayList<String>();
            for (Employee e : listEmployees) {
                listEmails.add(e.getEmail());
            }
            int index = Collections.binarySearch(listEmails, newEmployee.getEmail());

            // Check if password match to what is in database
            // Send OK response back to client
            OutputStream os = exchange.getResponseBody();
            String response;
            if (index < 0) {
                response = "Inncorrect Email";
                exchange.sendResponseHeaders(RCODE_CLIENT_ERROR, response.getBytes().length);
                os.write(response.getBytes());
            } else {
                if ((listEmployees.get(index).getPassword()).equals(newEmployee.getPassword())) {
                    response = "Login Successful";
                    exchange.sendResponseHeaders(RCODE_SUCCESSFUL, response.getBytes().length);
                    os.write(response.getBytes());
                } else {
                    response = "Inncorrect Password";
                    exchange.sendResponseHeaders(RCODE_CLIENT_ERROR, response.getBytes().length);
                    os.write(response.getBytes());
                }
            }
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
            default:
                break;
        }
    }
}