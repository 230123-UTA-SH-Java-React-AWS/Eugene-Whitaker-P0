package com.revature.repository;

import java.util.List;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This generic interface handles the interactions the
 *         database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeRepository}</li>
 *         <li>{@link ManagerIDRepository}</li>
 *         <li>{@link ManagerRepository}</li>
 *         </ul>
 *         for more information on its implementations.
 */
public interface Repository<T> {
    /**
     * <p>
     * This generic method saves an object to a file.
     * </p>
     * 
     * @param object the object to be saved
     */
    public abstract void saveToFile(T object);

    /**
     * <p>
     * This generic method adds a new object to the database.
     * </p>
     * 
     * @param object the object to be added
     */
    public abstract void saveToRepository(T object);

    /**
     * <p>
     * This method gets all entries of a table in the database and 
     * returns them as a {@link List} of {@link Employee} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    public abstract List<T> getAllObjects();

    /**
     * <p>
     * This method gets all entries of a column where the entry is a
     * <code>String</code> in the database and returns them as a 
     * {@link List} of {@link String} objects.
     * </p>
     * 
     * @return the {@link List} of {@link String} objects
     */
    public List<String> getAllColumnString(String column);

    /**
     * <p>
     * This method gets all entries of a column where the entry is a
     * <code>String</code> in the database and returns them as a 
     * {@link List} of {@link Integer} objects.
     * </p>
     * 
     * @return the {@link List} of {@link String} objects
     */
    public List<Integer> getAllColumnInteger(String column);
}
