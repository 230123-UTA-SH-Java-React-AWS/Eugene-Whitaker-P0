package com.revature.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class houses the information relative to employees. It extends
 *         the {@link Account} object. 
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link Manager}</li>
 *         </ul>
 *         for more information on other models.
 */
public class Employee extends Account {
    private int employeeID;
    private List<Ticket> tickets;

    // Default constructor
    public Employee() {
        super();
    }

    // Main constructor
    public Employee(String email, String password) {
        super(email, password);
        this.tickets = new ArrayList<Ticket>();
    }

    public int getEmployeeID() {
        return this.employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public List<Ticket> getTickets() {
        return this.tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public int removeTicket(Ticket ticket) {
        for (int i = 0; i < this.tickets.size(); i++) {
            if (this.tickets.get(i).getTicketID() == ticket.getTicketID()) {
                this.tickets.remove(i);
                return i;
            }
        }
        return -1;
    }

    public void createTicket(float amount, String description) {
        this.addTicket(new Ticket(amount, description));
    }
}
