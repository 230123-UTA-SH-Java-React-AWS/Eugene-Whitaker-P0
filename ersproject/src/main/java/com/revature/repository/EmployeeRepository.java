package com.revature.repository;

import com.revature.model.Employee;
import com.revature.repository.dao.DAOgetObjectsWhere;
import com.revature.repository.dao.DAOsaveToRepository;
import com.revature.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the interactions with the employee table of 
 *         the database. It implements the generic <code>DOA</code>         
 *         interface(s) {@link Repository}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link ManagerIDRepository}</li>
 *         <li>{@link ManagerRepository}</li>
 *         <li>{@link TicketRepository}</li>
 *         </ul>
 *         for more information on other repositories.
 */
public class EmployeeRepository implements DAOsaveToRepository<Employee>, DAOgetObjectsWhere<Employee> {
    /**
     * <p>
     * This method saves an object to the database
     * </p>
     * 
     * @param employee the object to be saved
     */
    @Override
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
     * This method gets an unique item from the database specified by the 
     * clause parameter
     * </p>
     * 
     * @param clause the WHERE clause meant to select a unique item
     * @return The unique item selected
     */
    @Override
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
