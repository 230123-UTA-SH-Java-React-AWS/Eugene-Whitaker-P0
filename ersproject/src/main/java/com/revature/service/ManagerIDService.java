package com.revature.service;

import com.revature.repository.ManagerIDRepository;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the 
 *         controller and the database for the managerIDs.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeService}</li>
 *         <li>{@link ManagerService}</li>
 *         <li>{@link TicketService}</li>
 *         </ul>
 *         for more information on other services.
 */
public class ManagerIDService {
    public ManagerIDRepository repository;

    /**
     * <p>
     * Dependency Injection for {@link ManagerIDRepository}
     * </p>
     * 
     * @param repository an instance of ManagerIDRepository
     */
    public ManagerIDService(ManagerIDRepository repository) {
        this.repository = repository;
    }
    
    /**
     * <p>
     * This method gets an unique item from the database specified by the 
     * clause parameter
     * </p>
     * 
     * @param clause the WHERE clause meant to select a unique item
     * @return The unique item selected
     */
    public Integer getObjectsWhere(String clause) {
        return this.repository.getObjectsWhere(clause);
    }
}