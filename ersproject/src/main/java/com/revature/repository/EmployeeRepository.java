package com.revature.repository;

import com.revature.model.Employee;
import com.revature.utils.ConnectionUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the interactions with the employee table of the
 *         database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link ManagerIDRepository}</li>
 *         <li>{@link ManagerRepository}</li>
 *         </ul>
 *         for more information on other repositories.
 */
public class EmployeeRepository {
    private static final String FILEPATH = "ersproject/src/main/java/com/revature/repository/employee.json";

    /**
     * <p>
     * This method saves a {@link Employee} object to a file at FILEPATH.
     * </p>
     * 
     * @param manager the object to be saved
     */
    public void saveToFile(Employee employee) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = "";

        try {
            jsonObject = mapper.writeValueAsString(employee);
            File employeeFile = new File(FILEPATH);
            FileWriter writer;
            // If the file doesn't exist then create it and write to it
            // Otherwise append it and write to it
            if (employeeFile.createNewFile()) writer = new FileWriter(FILEPATH);
            else writer = new FileWriter(FILEPATH, true);

            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(jsonObject);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method adds a new {@link Employee} object to the database.
     * </p>
     * 
     * @param manager the object to be added
     */
    public void saveToRepository(Employee employee) {
        String sql = "INSERT INTO employee (email, pass) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            prstmt.setString(1, employee.getEmail());
            prstmt.setString(2, employee.getPassword());

            prstmt.execute();
            System.out.println("Number of Rows updated: " + prstmt.getUpdateCount());
            
        } catch (SQLException e) {
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
        String sql = "SELECT * FROM employee";
        List<Employee> listEmployees = new ArrayList<Employee>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Employee newEmployee = new Employee();
                newEmployee.setEmail(rs.getString(2));
                newEmployee.setPassword(rs.getString(3));
                
                listEmployees.add(newEmployee);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listEmployees;
    }
}
