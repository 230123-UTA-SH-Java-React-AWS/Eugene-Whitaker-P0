package com.revature.model;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This abstract class houses the information relative to all accounts.
 *         It implements the {@link Ticket} interface.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link Employee}</li>
 *         <li>{@link Manager}</li>
 *         </ul>
 *         for more information on specific account models.
 */
public abstract class Account implements Ticket {
    private String email;
    private String password;

    // Default constructor
    public Account() {
        super();
    }

    // Main constructor
    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
