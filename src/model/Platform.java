package model;

/**
 *
 * @author anartzmugika
 */
public class Platform {
    
    public Platform(String id, String slug, String name)
    {
        setId(id);
        setSlug(slug);
        setName(name);
    }
    private String slug, name, id;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
