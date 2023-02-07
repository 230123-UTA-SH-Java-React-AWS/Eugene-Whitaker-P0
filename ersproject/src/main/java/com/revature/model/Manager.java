package com.revature.model;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class houses the information relative to managers. It 
 *         extends the {@link Account} object.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link Employee}</li>
 *         <li>{@link Ticket}</li>
 *         </ul>
 *         for more information on other models.
 */
public class Manager extends Account {
    private int managerID;

    // Default constructor
    public Manager() {
        super();
    }

    // Main constructor
    public Manager(String email, String password, int managerID) {
        super(email, password);
        this.managerID = managerID;
    }

    public int getManagerID() {
        return this.managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }
}
