package com.ironhack.edgeservice.controller.dto;


import javax.validation.constraints.Min;
import java.util.Objects;

public class OpportunityDTO {
    private String product;
    @Min(value = 1,message = "The quantity of trucks has to be higher than 0")
    private int quantity;

    private Long decisionMakerId;


    private Long accountId;

    private Long salesRepId;

    public OpportunityDTO() {
    }

    public OpportunityDTO(String product, int quantity,
                          Long decisionMakerId, Long accountId, Long salesRepId) {
        this.product = product;
        this.quantity = quantity;
        this.decisionMakerId = decisionMakerId;
        this.accountId = accountId;
        this.salesRepId = salesRepId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpportunityDTO that = (OpportunityDTO) o;
        return quantity == that.quantity && product.equals(that.product) && decisionMakerId.equals(that.decisionMakerId) && accountId.equals(that.accountId) && salesRepId.equals(that.salesRepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, decisionMakerId, accountId, salesRepId);
    }
}
