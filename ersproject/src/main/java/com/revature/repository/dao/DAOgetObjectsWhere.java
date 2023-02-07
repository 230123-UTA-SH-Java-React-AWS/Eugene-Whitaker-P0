package com.revature.repository.dao;

public interface DAOgetObjectsWhere<T> {
    /**
     * <p></p>
     * 
     * @param clause
     * @return
     */
    public abstract T getObjectsWhere(String clause);
}
