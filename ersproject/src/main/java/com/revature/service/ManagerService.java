package com.revature.service;

import com.revature.model.Manager;
import com.revature.repository.ManagerRepository;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the controller
 *         and the database for the managers. It implements the generic
 *         <code>DOA</code> interface {@link Service}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeService}</li>
 *         <li>{@link ManagerIDService}</li>
 *         </ul>
 *         for more information on other services.
 */
public class ManagerService  {
    /**
     * <p>
     * This method save a {@link Manager} object formated in <code>JSON</code> to
     * the database.
     * </p>
     * 
     * @param managerJson the <code>JSON</code> object to be added to the database
     */
    public void saveToRepository(String managerJson) {
        ManagerRepository repository = new ManagerRepository();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Manager newManager = mapper.readValue(managerJson, Manager.class);

            repository.saveToRepository(newManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method gets all managers in the database and returns them as a
     * {@link List} of {@link Manager} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    public List<Manager> getAllObjects() {
        ManagerRepository repository = new ManagerRepository();
        return repository.getAllObjects();
    }

    public Manager getObjectsWhere(String clause) {
        ManagerRepository repository = new ManagerRepository();
        return repository.getObjectsWhere(clause);
    }
}