package com.revature.service;

import com.revature.repository.ManagerIDRepository;
import java.util.List;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the controller
 *         and the database for the managerIDs. It implements the generic
 *         <code>DOA</code> interface {@link Service}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeService}</li>
 *         <li>{@link ManagerService}</li>
 *         </ul>
 *         for more information on other services.
 */
public class ManagerIDService {
    /**
     * <p>
     * This method gets all managerIDs in the database and returns them as a
     * {@link List} of {@link Integer} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    
    public List<Integer> getAllObjects() {
        ManagerIDRepository repository = new ManagerIDRepository();
        return repository.getAllObjects();
    }

    public int getObjectsWhere(String clause) {
        ManagerIDRepository repository = new ManagerIDRepository();
        return repository.getObjectsWhere(clause);
    }
}