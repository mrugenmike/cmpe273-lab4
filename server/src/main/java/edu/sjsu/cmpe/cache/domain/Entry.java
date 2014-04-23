package edu.sjsu.cmpe.cache.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

public class Entry {

    @NotNull
    private long key;

    @NotEmpty
    private String value;

    @NotEmpty
    private DateTime createdAt = new DateTime();

    /**
     * @return the key
     */
    public long getKey() {
	return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(long key) {
	this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
	return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
	this.value = value;
    }

    /**
     * @return the createdAt
     */
    public DateTime getCreatedAt() {
	return createdAt;
    }

    /**
     * @param createdAt
     *            the createdAt to set
     */
    public void setCreatedAt(DateTime createdAt) {
	this.createdAt = createdAt;
    }
}
