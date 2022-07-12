package com.ironhack.edgeservice.model;


import com.ironhack.edgeservice.enums.Product;
import com.ironhack.edgeservice.enums.Status;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Opportunity {
 // indicate that is auto increment column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Product product;
    private int quantity;

    private Long decisionMakerId;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long accountId;

    private Long salesRepId;

    //constructors
    public Opportunity() {
    }

    public Opportunity(Product product, int quantity, Long decisionMakerId,
                       Long accountId, Long salesRepId) {
        this.product = product;
        this.quantity = quantity;
        this.decisionMakerId = decisionMakerId;
        this.status = Status.OPEN;
        this.accountId = accountId;
        this.salesRepId = salesRepId;
    }

    //getters and setters needed

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getDecisionMakerId() {
        return decisionMakerId;
    }

    public void setDecisionMakerId(Long decisionMakerId) {
        this.decisionMakerId = decisionMakerId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(Long salesRepId) {
        this.salesRepId = salesRepId;
    }

    @Override
    public String toString() {
        return "=== Opportunity " + id + " ===\n"+
                "- product: " + product + "\n"+
                "- trucks quantity: " + quantity + "\n"+
                "- decision maker: " + decisionMakerId +
                "- status: " + status + "\n"+
                "- salesRep id: " + salesRepId +"\n";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Opportunity that = (Opportunity) o;
        return quantity == that.quantity && Objects.equals(id, that.id)
                && product == that.product && Objects.equals(decisionMakerId, that.decisionMakerId)
                && Objects.equals(status, that.status) && Objects.equals(accountId, that.accountId)
                && Objects.equals(salesRepId, that.salesRepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, decisionMakerId, status, accountId, salesRepId);
    }
}
