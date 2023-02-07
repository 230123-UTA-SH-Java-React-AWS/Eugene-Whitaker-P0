package com.revature.service;

import com.revature.model.Employee;
import com.revature.repository.EmployeeRepository;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the 
 *         controller and the database for the employees.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link ManagerIDService}</li>
 *         <li>{@link ManagerService}</li>
 *         <li>{@link TIcketService}<li>
 *         </ul>
 *         for more information on other services.
 */
public class EmployeeService  {
    public EmployeeRepository repository;

    /**
     * <p>
     * Dependency Injection for {@link EmployeeRepository}
     * </p>
     * 
     * @param repository an instance of EmployeeRepository
     */
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

     /**
     * <p>
     * This method saves an object to the database
     * </p>
     * 
     * @param employeeJson the object to be saved
     */
    public void saveToRepository(String employeeJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Employee newEmployee = mapper.readValue(employeeJson, Employee.class);

            this.repository.saveToRepository(newEmployee);
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
    public Employee getObjectsWhere(String clause) {
        return this.repository.getObjectsWhere(clause);
    }
}
