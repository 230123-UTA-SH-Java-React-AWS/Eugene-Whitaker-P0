package com.revature.repository;

import com.revature.model.Employee;
import com.revature.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the interactions with the employee table of the
 *         database. It implements the generic <code>DOA</code> interface
 *         {@link Repository}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link ManagerIDRepository}</li>
 *         <li>{@link ManagerRepository}</li>
 *         </ul>
 *         for more information on other repositories.
 */
public class EmployeeRepository {
    /**
     * <p>
     * This method adds a new {@link Employee} object to the database.
     * </p>
     * 
     * @param employee the object to be added
     */
    public void saveToRepository(Employee employee) {
        String sql = "INSERT INTO employee (email, pass) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            prstmt.setString(1, employee.getEmail());
            prstmt.setString(2, employee.getPassword());

            prstmt.execute();

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
    public List<Employee> getAllObjects() {
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

    public Employee getObjectsWhere(String clause) {
        String sql = "SELECT * FROM employee WHERE "+clause;
        Employee employee = new Employee();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                employee.setEmployeeID(rs.getInt(1));
                employee.setEmail(rs.getString(2));
                employee.setPassword(rs.getString(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }
}
