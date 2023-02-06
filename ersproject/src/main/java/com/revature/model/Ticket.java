package com.revature.model;

public class Ticket {
    private int ticketID;
    private int employeeID;
    private float amount;
    private String description;
    private boolean pending;
    private String status;
    
    public Ticket() {
        super();
    }

    public Ticket(Ticket ticket) {
        this.amount = ticket.amount;
        this.description = ticket.description;
        this.pending = true;
        this.status = "pending";
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
}
