package com.revature.service;

import com.revature.model.Employee;
import com.revature.model.Ticket;
import com.revature.repository.TicketRepository;
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
public class TicketService  {

    public void saveToRepository(String ticketJson, Employee employee) {
        TicketRepository repository = new TicketRepository();
        ObjectMapper mapper = new ObjectMapper();
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
    
    public List<Ticket> getAllObjects() {
        TicketRepository repository = new TicketRepository();
        return repository.getAllObjects();
    }

    public List<Ticket> getAllObjectsWhere(String clause) {
        TicketRepository repository = new TicketRepository();
        return repository.getAllObjectsWhere(clause);
    }

    public void updateRepository(String ticketJson) {
        TicketRepository repository = new TicketRepository();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Ticket newTicket = mapper.readValue(ticketJson, Ticket.class);

            repository.updateRepository(newTicket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
