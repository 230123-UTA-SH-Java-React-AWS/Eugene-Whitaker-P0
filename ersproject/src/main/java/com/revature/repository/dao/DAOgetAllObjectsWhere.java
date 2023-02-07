package com.revature.repository.dao;

import java.util.List;

public interface DAOgetAllObjectsWhere<T> {
    /**
     * <p></p>
     * 
     * @param clause
     * @return
     */
    public List<T> getAllObjectsWhere(String clause);
}
