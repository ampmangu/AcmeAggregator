package com.amp.acmeaggregator.domain;

import java.util.Objects;

public class DataRow {
    private String deliveryDate;
    private String product;
    private String category;
    private Double declaredAmount;
    private Double fee;
    private Double feeAmount;

    public DataRow(String deliveryDate, String product, String category, Double declaredAmount, Double fee, Double feeAmount) {
        this.deliveryDate = deliveryDate;
        this.product = product;
        this.category = category;
        this.declaredAmount = declaredAmount;
        this.fee = fee;
        this.feeAmount = feeAmount;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getDeclaredAmount() {
        return declaredAmount;
    }

    public void setDeclaredAmount(Double declaredAmount) {
        this.declaredAmount = declaredAmount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataRow dataRow = (DataRow) o;
        return deliveryDate.equals(dataRow.deliveryDate) &&
            product.equals(dataRow.product) &&
            category.equals(dataRow.category) &&
            declaredAmount.equals(dataRow.declaredAmount) &&
            fee.equals(dataRow.fee) &&
            feeAmount.equals(dataRow.feeAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryDate, product, category, declaredAmount, fee, feeAmount);
    }
}
