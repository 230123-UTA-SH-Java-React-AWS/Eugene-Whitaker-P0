package com.revature.service;

import com.revature.model.Employee;
import com.revature.model.Ticket;
import com.revature.repository.TicketRepository;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the 
 *         controller and the database for the employees.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeService}</li>
 *         <li>{@link ManagerIDService}</li>
 *         <li>{@link ManagerService}</li>
 *         </ul>
 *         for more information on other services.
 */
public class TicketService  {
    public TicketRepository repository;

    /**
     * <p>
     * Dependency Injection for {@link TicketRepository}
     * </p>
     * 
     * @param repository an instance of TicketRepository
     */
    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    /**
     * <p>
     * This method saves an object to the database
     * </p>
     * 
     * @param ticketJson the object to be saved
     * @param employee the object containing the foreign key
     */
    public void saveToRepositoryFK(String ticketJson, Employee employee) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Ticket newTicket = mapper.readValue(ticketJson, Ticket.class);

            this.repository.saveToRepositoryFK(newTicket, employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method gets an <code>ListM/code> of items from the database 
     * specified by the clause parameter
     * </p>
     * 
     * @param clause the WHERE clause meant to select the items
     * @return The items selected
     */
    public List<Ticket> getAllObjectsWhere(String clause) {
        return this.repository.getAllObjectsWhere(clause);
    }

    /**
     * <p>
     * This method updates an object to the database
     * </p>
     * 
     * @param ticketJson the object to be updated
     */
    public void updateRepository(String ticketJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Ticket newTicket = mapper.readValue(ticketJson, Ticket.class);

            this.repository.updateRepository(newTicket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
