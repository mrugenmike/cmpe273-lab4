package edu.sjsu.cmpe.cache.repository;

import java.util.List;

import com.yammer.dropwizard.jersey.params.LongParam;
import edu.sjsu.cmpe.cache.domain.Entry;

/**
 * Entry repository interface.
 * 
 * What is repository pattern?
 * 
 * @see http://martinfowler.com/eaaCatalog/repository.html
 */
public interface CacheInterface {
    /**
     * Save a new entry in the repository
     * 
     * @param newentry
     *            a entry instance to be create in the repository
     * @return an entry instance
     */
    Entry save(Entry newEntry);

    /**
     * Retrieve an existing entry by key
     * 
     * @param key
     *            a valid key
     * @return a entry instance
     */
    Entry get(Long key);

    /**
     * Retrieve all entries
     * 
     * @return a list of entries
     */
    List<Entry> getAll();

    /**
     * Removes the Entry with for given key
     *
     * @param key
     *          a entry instance to be removed from the repository
     */
    void remove(Long key);
}
