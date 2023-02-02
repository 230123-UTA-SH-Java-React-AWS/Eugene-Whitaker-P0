package com.revature.service;

import com.revature.model.Employee;
import com.revature.repository.EmployeeRepository;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;



/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the controller
 *         and the database for the employees.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link ManagerIDService}</li>
 *         <li>{@link ManagerService}</li>
 *         </ul>
 *         for more information on other services.
 */
public class EmployeeService {

     /**
     * <p>
     * This method save a {@link Employee} object formated in <code>JSON</code> to
     * the database.
     * </p>
     * 
     * @param employeeJson the <code>JSON</code> object to be added to the database
     */
    public void saveToRepository(String employeeJson) {
        EmployeeRepository repository = new EmployeeRepository();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(employeeJson);
        try {
            Employee newEmployee = mapper.readValue(employeeJson, Employee.class);

            repository.saveToRepository(newEmployee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method gets all employees in the database and returns them as a
     * {@link List} of {@link Employee} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    public List<Employee> getAllEmployees() {
        EmployeeRepository repository = new EmployeeRepository();
        return repository.getAllEmployees();
    }

    /**
     * <p>
     * This method converts a {@link List} of {@link Employee} objects to a
     * <code>JSON</code> formated string.
     * </p>
     * 
     * @param the {@link List} of objects
     * @return the <code>JSON</code> formated string
     */
    public String listToJSON(List<Employee> listEmployees) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(listEmployees);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return jsonString;
    }
}
