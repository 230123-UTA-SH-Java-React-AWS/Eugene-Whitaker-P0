package com.revature.controllers;

import com.revature.model.Employee;
import com.revature.service.EmployeeService;
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
 *         backend server that allows employees to register an account in the
 *         database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeLogin}</li>
 *         <li>{@link ManagerLogin}</li>
 *         <li>{@link ManagerRegister}</li>
 *         </ul>
 *         for more information on other contexts.
 */
public class EmployeeRegister implements HttpHandler {
    
    // Global variable for the Return Codes
    private static final int RCODE_SUCCESSFUL = 200;
    private static final int RCODE_REDIRECT = 301;
    private static final int RCODE_CLIENT_ERROR = 400;

    private static final String BADEMAIL = "BADEMAIL";

    private void getRequest(HttpExchange exchange) {
        try {
            File file = new File("ersproject/src/main/java/com/revature/view/employeeRegister.html"); 
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
     * <p></p>
     * 
     * @param exchange
     */
    private void postRequest(HttpExchange exchange) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            StringBuilder textBuilder = StringBuilderUtil.buildString(exchange);

            Employee newEmployee = mapper.readValue(textBuilder.toString(), Employee.class);

            EmployeeService service = new EmployeeService();
            String clause = "email = "+"\'"+newEmployee.getEmail()+"\'";
            Employee employee = service.getObjectsWhere(clause);

            OutputStream os = exchange.getResponseBody();
            String response;
            if (employee.getEmail() == null) {
                service.saveToRepository(textBuilder.toString());
                exchange.getResponseHeaders().add("Location", "http://localhost:8000/employeeLogin");
                exchange.sendResponseHeaders(RCODE_REDIRECT, -1);
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
