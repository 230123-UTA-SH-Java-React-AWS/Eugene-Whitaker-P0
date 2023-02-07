package com.revature.model;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class houses the information relative to tickets.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link Account}</li>
 *         <li>{@link Employee}</li>
 *         <li>{@link Manager}</li>
 *         </ul>
 *         for more information on other models.
 */
public class Ticket {
    private int ticketID;
    private int employeeID;
    private float amount;
    private String description;
    private boolean pending;
    private String status;
    // private String type;
    
    // Default constructor
    public Ticket() {
        super();
    }

    // Main constructors
    public Ticket(Ticket ticket) {
        this.ticketID = ticket.ticketID;
        this.employeeID = ticket.employeeID;
        this.amount = ticket.amount;
        this.description = ticket.description;
        this.pending = true;
        this.status = "pending";
        // this.type = ticket.type;
    }

    public Ticket(float amount, String description) {
        this.amount = amount;
        this.description = description;
        this.pending = true;
        this.status = "pending";
    }

    public int getTicketID() {
        return this.ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getEmployeeID() {
        return this.employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // public String getType() {
    //     return this.type;
    // }

    // public void setType(String type) {
    //     this.type = type;
    // }
}
