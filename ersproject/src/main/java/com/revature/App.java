package com.revature;

import com.revature.controllers.EmployeeLogin;
import com.revature.controllers.EmployeeRegister;
import com.revature.controllers.ManagerLogin;
import com.revature.controllers.ManagerRegister;
import com.revature.controllers.ProcessTicket;
import com.revature.controllers.SubmitTicket;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class houses the main method of the project. The main method runs
 *         a server using {@link HttpServer}.
 *         This server will function as the local backend for this project.
 *         </p>
 *         See:
 *         <ul>
 *         <li>{@link EmployeeLogin}</li>
 *         <li>{@link EmployeeRegister}</li>
 *         <li>{@link ManagerLogin}</li>
 *         <li>{@link ManagerRegister}</li>
 *         </ul>
 *         for more information on each context.
 */
public final class App {
    private static final int PORT = 8000;

    //Logger
    public static Logger logger = Logger.getLogger("AppLogger");

    // Private constructor so no instances of this class can be created
    private App() {
    }

    /**
     * This method runs server for this project
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        try {
            // Initialize server on port:8000
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

            // Define each context
            // server.createContext("/", new EmployeeLogin());
            server.createContext("/employeeLogin", new EmployeeLogin());
            server.createContext("/employeeRegister", new EmployeeRegister());
            server.createContext("/managerLogin", new ManagerLogin());
            server.createContext("/managerRegister", new ManagerRegister());
            server.createContext("/submitTicket", new SubmitTicket());
            server.createContext("/processTicket", new ProcessTicket());

            // Start the server
            server.setExecutor(null);
            server.start();
            logger.info("Server started on Port:"+PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
