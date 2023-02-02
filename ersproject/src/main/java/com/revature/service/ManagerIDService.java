package com.revature.service;

import com.revature.repository.ManagerIDRepository;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the controller
 *         and the database for the managerIDs.
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
    public List<Integer> getAllManagerIDs() {
        ManagerIDRepository repository = new ManagerIDRepository();
        return repository.getAllManagerIDs();
    }

    /**
     * <p>
     * This method converts a {@link List} of {@link Integer} objects to a
     * <code>JSON</code> formated string.
     * </p>
     * 
     * @param the {@link List} of objects
     * @return the <code>JSON</code> formated string
     */
    public String listToJSON(List<Integer> listManagers) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(listManagers);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}