/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.entites;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import logic.db.DataSource;
import logic.IEntity;
import logic.Logger;

/**
 * Order is reserved keyword
 *
 */
@Entity
@Table(name = "Order_")
public class Order implements Serializable, IEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order() {
        this.fullfilled = false;
    }

    private String name;
    private String address;
    private String number;
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_FIELD")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFullfilled() {
        return fullfilled;
    }

    public void setFullfilled(boolean fullfilled) {
        this.fullfilled = fullfilled;
    }

    @ElementCollection
    @JoinTable(name = "PRODUCT_ORDER", joinColumns = @JoinColumn(name = "ID"))
    @MapKeyColumn(name = "NUMBER_OF_PRODUCT")
    @Column(name = "AMOUNT")
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private boolean fullfilled;

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
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
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.entites.Order[ id=" + id + " ]";
    }

    @Override
    public String[] getPropertyNames() {
        return new String[]{"dátum", "név", "cím", "telefonszám", "email cím", "összérték", "teljesíthető-e"};
    }

    //Rendelések: név, cím, telefonszám, email cím, termékek listája, teljesített-e.
    /**
     * (dátum, név, cím, telefonszám, email cím, összérték), és mindegyiknél
     * jelezze, hogy a rendelés teljesíthető-e -
     *
     * @param columnIndex
     * @return
     */
    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                if (date == null) {
                    return "";
                }
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
                String dateString = dateFormatter.format(date);
                return dateString;
            case 1:
                return this.name;
            case 2:
                return this.address;
            case 3:
                return this.number;
            case 4:
                return this.email;
            case 5:
                return sumPrices();
            case 6:
                return isFulfillAble();
            default:
                return null;
        }
    }

    private int sumPrices() {
        int sum = 0;       
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            
            for (int i = 0; i < entry.getValue(); i++) {
                sum += entry.getKey().getPrice();
            }
     
        }
        return sum;
    }

    public boolean isFulfillAble() {

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {

            int requestedAmount = entry.getValue();
            int stockAmount = ((Product) DataSource.getInstance().getController("Product").read(((Product) entry.getKey()).getId().intValue())).getStock();

            if (stockAmount < requestedAmount) {
                return false;
            }

        }

        return true;

    }

    @Override
    public void set(int columnIndex, Object value) {
        //nem szerkeszthető
    }

}
