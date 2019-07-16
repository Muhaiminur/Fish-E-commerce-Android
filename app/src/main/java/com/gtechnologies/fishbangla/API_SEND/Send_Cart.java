package com.gtechnologies.fishbangla.API_SEND;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Send_Cart implements Serializable {

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("serviceCharge")
    @Expose
    private String serviceCharge;
    @SerializedName("vat")
    @Expose
    private String vat;
    @SerializedName("deliveryCharge")
    @Expose
    private String deliveryCharge;
    @SerializedName("discountAmount")
    @Expose
    private String discountAmount;
    @SerializedName("totalAfterDiscount")
    @Expose
    private String totalAfterDiscount;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("pointUsed")
    @Expose
    private Integer pointUsed;
    @SerializedName("discountId")
    @Expose
    private Integer discountId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("addressId")
    @Expose
    private Integer addressId;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("status")
    @Expose
    private String status;

    public Send_Cart(String total, String serviceCharge, String vat, String deliveryCharge, String discountAmount, String totalAfterDiscount, String subtotal, Integer pointUsed, Integer discountId, Integer userId, Integer addressId, String paymentType, String paymentStatus, String message, String transactionId, String invoice, String status, List<Item> items) {
        this.total = total;
        this.serviceCharge = serviceCharge;
        this.vat = vat;
        this.deliveryCharge = deliveryCharge;
        this.discountAmount = discountAmount;
        this.totalAfterDiscount = totalAfterDiscount;
        this.subtotal = subtotal;
        this.pointUsed = pointUsed;
        this.discountId = discountId;
        this.userId = userId;
        this.addressId = addressId;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.message = message;
        this.transactionId = transactionId;
        this.invoice = invoice;
        this.status = status;
        this.items = items;
    }

    public Send_Cart() {
    }

    @SerializedName("items")
    @Expose


    private List<Item> items = null;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public void setTotalAfterDiscount(String totalAfterDiscount) {
        this.totalAfterDiscount = totalAfterDiscount;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getPointUsed() {
        return pointUsed;
    }

    public void setPointUsed(Integer pointUsed) {
        this.pointUsed = pointUsed;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Send_Cart{" +
                "total='" + total + '\'' +
                ", serviceCharge='" + serviceCharge + '\'' +
                ", vat='" + vat + '\'' +
                ", deliveryCharge='" + deliveryCharge + '\'' +
                ", discountAmount='" + discountAmount + '\'' +
                ", totalAfterDiscount='" + totalAfterDiscount + '\'' +
                ", subtotal='" + subtotal + '\'' +
                ", pointUsed=" + pointUsed +
                ", discountId=" + discountId +
                ", userId=" + userId +
                ", addressId=" + addressId +
                ", paymentType='" + paymentType + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", message='" + message + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", invoice='" + invoice + '\'' +
                ", status='" + status + '\'' +
                ", items=" + items +
                '}';
    }
}