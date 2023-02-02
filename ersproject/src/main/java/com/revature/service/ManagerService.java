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
 *         and the database for the managers.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeService}</li>
 *         <li>{@link ManagerIDService}</li>
 *         </ul>
 *         for more information on other services.
 */
public class ManagerService {

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
        System.out.println(managerJson);
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
    public List<Manager> getAllManagers() {
        ManagerRepository repository = new ManagerRepository();
        return repository.getAllManagers();
    }

    /**
     * <p>
     * This method converts a {@link List} of {@link Manager} objects to a
     * <code>JSON</code> formated string.
     * </p>
     * 
     * @param the {@link List} of objects
     * @return the <code>JSON</code> formated string
     */
    public String listToJSON(List<Manager> listManagers) {
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