package com.revature.service;

import com.revature.model.Employee;
import com.revature.model.Ticket;
import com.revature.repository.TicketRepository;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;



/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the controller
 *         and the database for the employees. It implements the generic
 *         <code>DOA</code> interface {@link Service}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link ManagerIDService}</li>
 *         <li>{@link ManagerService}</li>
 *         </ul>
 *         for more information on other services.
 */
public class TicketService implements Service<Ticket> {

     /**
     * <p>
     * This method save a {@link Ticket} object formated in <code>JSON</code> to
     * the database.
     * </p>
     * 
     * @param ticketJson the <code>JSON</code> object to be added to the database
     */
    @Override
    public void saveToRepository(String ticketJson) {
        // TicketRepository repository = new TicketRepository();
        // ObjectMapper mapper = new ObjectMapper();
        // System.out.println(ticketJson);
        // try {
        //     Ticket newTicket = mapper.readValue(ticketJson, Ticket.class);

        //     repository.saveToRepository(newTicket);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    public void saveToRepository(String ticketJson, Employee employee) {
        TicketRepository repository = new TicketRepository();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(ticketJson);
        try {
            Ticket newTicket = mapper.readValue(ticketJson, Ticket.class);

            repository.saveToRepository(newTicket, employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method gets all employees in the database and returns them as a
     * {@link List} of {@link Employee} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    @Override
    public List<Ticket> getAllObjects() {
        TicketRepository repository = new TicketRepository();
        return repository.getAllObjects();
    }

    /**
     * <p>
     * This method gets all entries of a column where the entry is a
     * <code>String</code> in the database and returns them as a
     * {@link List} of {@link String} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    @Override
    public List<String> getAllColumnString(String column) {
        // EmployeeRepository repository = new EmployeeRepository();
        // return repository.getAllColumnString(column);
        return null;
    }

    /**
     * <p>
     * This method gets all entries of a column where the entry is a
     * <code>Integer</code> in the database and returns them as a
     * {@link List} of {@link Integer} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    @Override
    public List<Integer> getAllColumnInteger(String column) {
        // EmployeeRepository repository = new EmployeeRepository();
        // return repository.getAllColumnInteger(column);
        return null;
    }

    /**
     * <p>
     * This method converts a {@link List} of {@link Employee} objects to a
     * <code>JSON</code> formated string.
     * </p>
     * 
     * @param the {@link List} of objects
     * @return the <code>JSON</code> formated string
     */
    @Override
    public String listToJSON(List<Ticket> listTickets) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(listTickets);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return jsonString;
    }
}
