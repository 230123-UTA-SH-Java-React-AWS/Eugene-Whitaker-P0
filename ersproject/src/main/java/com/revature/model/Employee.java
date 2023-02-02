package com.revature.model;

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
public class Employee extends Account{

    // Default constructor
    public Employee() {
        super();
    }

    // Main constructor
    public Employee(String email, String password) {
        super(email, password);
    }
}
