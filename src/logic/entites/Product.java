/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.entites;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import logic.IEntity;

/**
 *
 * @author D
 */
@Entity
public class Product implements Serializable, IEntity {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * raktárkészlet kezdetben mindig 0 legyen
     */
    public Product() {
        stock = 0;
        price = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String manufacturer;
    private String type;
    private String description;

    @OneToOne
    private Category category;
    private Integer price;
    private Integer stock;
    private boolean active;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.entites.Product[ id=" + id + " ]";
    }

    public static String[] getPropertyNames() {
        return new String[]{"gyártó", "típus", "leírás", "kategória", "ár", "raktárkészlet", "aktív-e"};
    }   

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return this.manufacturer;
            case 1:
                return this.type;
            case 2:
                return this.description;
            case 3:                
                return this.category;
            case 4:
                return this.price;
            case 5:
                return this.stock;
            case 6:
                return this.active;
            default:
                System.out.println(columnIndex);
                return null;
        }
    }

    //Termékek: gyártó, típus, leírás, kategória, ár, raktárkészlet, aktív-e;
    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                this.manufacturer = (String) value;
                break;
            case 1:
                this.type = (String) value;
                break;
            case 2:
                this.description = (String) value;
                break;
            case 3:
                this.category = (Category) value;
                break;
            case 4:
                this.price = (Integer) value;
                break;
            case 5:
                this.stock = (Integer) value;
                break;
            case 6:
                this.active = (boolean) value;
                break;
        }
    }

}
