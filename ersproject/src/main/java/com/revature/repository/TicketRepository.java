package com.revature.repository;

import com.revature.App;
import com.revature.model.Employee;
import com.revature.model.Ticket;
import com.revature.repository.dao.DAOgetAllObjectsWhere;
import com.revature.repository.dao.DAOsaveToRepositoryFK;
import com.revature.repository.dao.DAOupdateRepository;
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
public class TicketRepository implements DAOsaveToRepositoryFK<Ticket, Employee>, DAOgetAllObjectsWhere<Ticket>, DAOupdateRepository<Ticket> {
    /**
     * <p>
     * This method saves an object to the database
     * </p>
     * 
     * @param ticket the object to be saved
     * @param employee the object containing the foreign key
     */
    @Override
    public void saveToRepositoryFK(Ticket ticket, Employee employee) {
        String sql = "INSERT INTO ticket (employeeID, amount, description, pending, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            prstmt.setInt(1, employee.getEmployeeID());
            prstmt.setFloat(2, ticket.getAmount());
            prstmt.setString(3, ticket.getDescription());
            prstmt.setBoolean(4, ticket.getPending());
            prstmt.setString(5, ticket.getStatus());

            prstmt.execute();

            App.logger.info("Number of rows updated: "+prstmt.getUpdateCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Ticket getObjectsWhere(String clause) {
        String sql = "SELECT * FROM ticket WHERE "+clause;
        Ticket ticket = new Ticket();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ticket.setTicketID(rs.getInt(1));
                ticket.setEmployeeID(rs.getInt(2));
                ticket.setAmount(rs.getFloat(3));
                ticket.setDescription(rs.getString(4));
                ticket.setPending(rs.getBoolean(5));
                ticket.setStatus(rs.getString(6));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticket;
    }

    /**
     * <p>
     * This method gets an <code>ListM/code> of items from the database 
     * specified by the clause parameter
     * </p>
     * 
     * @param clause the WHERE clause meant to select the items
     * @return The items selected
     */
    @Override
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

    /**
     * <p>
     * This method updates an object to the database
     * </p>
     * 
     * @param ticket the object to be updated
     */
    @Override
    public void updateRepository(Ticket ticket) {
        String sql = "UPDATE ticket SET (pending, status) = (?, ?) WHERE ID = (?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            prstmt.setBoolean(1, ticket.getPending());
            prstmt.setString(2, ticket.getStatus());
            prstmt.setInt(3, ticket.getTicketID());

            prstmt.execute();

            App.logger.info("Number of rows updated: "+prstmt.getUpdateCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
