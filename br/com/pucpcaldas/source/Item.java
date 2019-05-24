/**
 * @author Elton Fonseca
 */

import java.io.Serializable;

public class Item implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private int id;
    private String description;

    /**
     * @param id
     * @param description
     */
    public Item(int id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }
}