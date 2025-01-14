package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Inventaire {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private String resourceName;
    private Integer resourceQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return resourceName;
    }

    public void setName(String name) {
        this.resourceName = name;
    }

    public Integer getQuantity() {
        return resourceQuantity;
    }

    public void setQuantity(Integer quantity) {
        this.resourceQuantity = quantity;
    }
}
