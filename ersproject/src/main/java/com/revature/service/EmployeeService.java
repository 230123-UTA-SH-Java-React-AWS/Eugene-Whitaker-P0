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
 *         and the database for the employees. It implements the generic
 *         <code>DOA</code> interface {@link Service}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link ManagerIDService}</li>
 *         <li>{@link ManagerService}</li>
 *         </ul>
 *         for more information on other services.
 */
public class EmployeeService  {
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
    public List<Employee> getAllObjects() {
        EmployeeRepository repository = new EmployeeRepository();
        return repository.getAllObjects();
    }

    public Employee getObjectsWhere(String clause) {
        EmployeeRepository repository = new EmployeeRepository();
        return repository.getObjectsWhere(clause);
    }
}
