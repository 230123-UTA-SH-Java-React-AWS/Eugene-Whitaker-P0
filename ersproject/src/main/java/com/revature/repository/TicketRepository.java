package com.revature.repository;

import com.revature.model.Employee;
import com.revature.model.Ticket;
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
public class TicketRepository {
    /**
     * <p>
     * This method adds a new {@link Ticket} object to the database.
     * </p>
     * 
     * @param employee the object to be added
     */
    public void saveToRepository(Ticket ticket, Employee employee) {
        String sql = "INSERT INTO ticket (employeeID, amount, description, pending, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            prstmt.setInt(1, employee.getEmployeeID());
            prstmt.setFloat(2, ticket.getAmount());
            prstmt.setString(3, ticket.getDescription());
            prstmt.setBoolean(4, ticket.getPending());
            prstmt.setString(5, ticket.getStatus());

            prstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method gets all employees in the database and returns them as a
     * {@link List} of {@link Ticket} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */

    public List<Ticket> getAllObjects() {
        String sql = "SELECT * FROM ticket";
        List<Ticket> listTickets = new ArrayList<Ticket>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Ticket newTicket = new Ticket();
                newTicket.setTicketID(rs.getInt(1));
                newTicket.setAmount(rs.getFloat(2));
                newTicket.setDescription(rs.getString(3));

                listTickets.add(newTicket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listTickets;
    }

    /**
     * <p>
     * This method gets all entries of a column where the entry is a
     * <code>String</code> in the database and returns them as a
     * {@link List} of {@link String} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    public List<Ticket> getAllObjectsWhere(String clause) {
        String sql = "SELECT * FROM ticket WHERE "+clause;
        List<Ticket> listTickets = new ArrayList<Ticket>();
        
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Ticket newTicket = new Ticket();
                newTicket.setTicketID(rs.getInt(1));
                newTicket.setEmployeeID(rs.getInt(2));
                newTicket.setAmount(rs.getFloat(3));
                newTicket.setDescription(rs.getString(4));
                newTicket.setPending(rs.getBoolean(5));
                newTicket.setStatus(rs.getString(6));

                listTickets.add(newTicket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listTickets;
    }

    public void updateRepository(Ticket ticket) {
        String sql = "UPDATE ticket SET (pending, status) = (?, ?) WHERE ID = (?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            prstmt.setBoolean(1, ticket.getPending());
            prstmt.setString(2, ticket.getStatus());
            prstmt.setInt(3, ticket.getTicketID());

            prstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
