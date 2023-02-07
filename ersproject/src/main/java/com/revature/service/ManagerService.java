package com.revature.service;

import com.revature.model.Manager;
import com.revature.repository.ManagerRepository;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the 
 *         controller and the database for the managers.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeService}</li>
 *         <li>{@link ManagerIDService}</li>
 *         <li>{@link TicketSerivce}</li>
 *         </ul>
 *         for more information on other services.
 */
public class ManagerService  {
    public ManagerRepository repository;

    /**
     * <p>
     * Dependency Injection for {@link ManagerRepository}
     * </p>
     * 
     * @param repository an instance of ManagerRepository
     */
    public ManagerService(ManagerRepository repository) {
        this.repository = repository;
    }
    
    /**
     * <p>
     * This method saves an object to the database
     * </p>
     * 
     * @param managerJson the object to be saved
     */
    public void saveToRepository(String managerJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Manager newManager = mapper.readValue(managerJson, Manager.class);

            this.repository.saveToRepository(newManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public Manager getObjectsWhere(String clause) {
        return this.repository.getObjectsWhere(clause);
    }
}