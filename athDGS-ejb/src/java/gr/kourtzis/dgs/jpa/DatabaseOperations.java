/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.jpa;

import java.util.List;

/**
 *
 * @author akourtzis
 * @param <T>
 */
public interface DatabaseOperations<T> {
    List<T> readEntries();
    T readEntry(int id);
    
    void create(final T object);
    void update(final T object);
    void delete(final T object);
    
    Long count();
}
