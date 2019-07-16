package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GET_BUYHISTORY{

    @SerializedName("receiversName")
    @Expose
    private String receiversName;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("deliveryCharge")
    @Expose
    private String deliveryCharge;
    @SerializedName("discountAmount")
    @Expose
    private Integer discountAmount;
    @SerializedName("totalAfterDiscount")
    @Expose
    private String totalAfterDiscount;
    @SerializedName("nearbyLandmark")
    @Expose
    private String nearbyLandmark;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("receiversContact")
    @Expose
    private String receiversContact;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("serviceCharge")
    @Expose
    private String serviceCharge;
    @SerializedName("discountName")
    @Expose
    private String discountName;
    @SerializedName("order_discountAmount")
    @Expose
    private String orderDiscountAmount;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("vat")
    @Expose
    private String vat;
    @SerializedName("pointUsed")
    @Expose
    private Integer pointUsed;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("carts")
    @Expose
    private List<Cart> carts = null;
    @SerializedName("discountCode")
    @Expose
    private String discountCode;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("discountId")
    @Expose
    private Integer discountId;
    @SerializedName("upazilla")
    @Expose
    private String upazilla;
    @SerializedName("payementType")
    @Expose
    private String payementType;
    @SerializedName("status")
    @Expose
    private String status;

    public String getReceiversName() {
        return receiversName;
    }

    public void setReceiversName(String receiversName) {
        this.receiversName = receiversName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public void setTotalAfterDiscount(String totalAfterDiscount) {
        this.totalAfterDiscount = totalAfterDiscount;
    }

    public String getNearbyLandmark() {
        return nearbyLandmark;
    }

    public void setNearbyLandmark(String nearbyLandmark) {
        this.nearbyLandmark = nearbyLandmark;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getReceiversContact() {
        return receiversContact;
    }

    public void setReceiversContact(String receiversContact) {
        this.receiversContact = receiversContact;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

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

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getOrderDiscountAmount() {
        return orderDiscountAmount;
    }

    public void setOrderDiscountAmount(String orderDiscountAmount) {
        this.orderDiscountAmount = orderDiscountAmount;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public Integer getPointUsed() {
        return pointUsed;
    }

    public void setPointUsed(Integer pointUsed) {
        this.pointUsed = pointUsed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public String getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(String upazilla) {
        this.upazilla = upazilla;
    }

    public String getPayementType() {
        return payementType;
    }

    public void setPayementType(String payementType) {
        this.payementType = payementType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}