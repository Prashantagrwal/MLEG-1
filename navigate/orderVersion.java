package com.example.dell.navigate;

/**
 * Created by DELL on 23/02/2017.
 */
public class orderVersion
{
    String productName,ProductCode,Call,Design,Delivery,TankYou,CustomerName,email,phone,quantity,
    o_price,d_price,address;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setO_price(String o_price) {
        this.o_price = o_price;
    }

    public void setD_price(String d_price) {
        this.d_price = d_price;
    }

    public String getCustomerName() {

        return CustomerName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getO_price() {
        return o_price;
    }

    public String getD_price() {
        return d_price;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public String getCall() {
        return Call;
    }

    public String getDesign() {
        return Design;
    }

    public String getDelivery() {
        return Delivery;
    }

    public String getTankYou() {
        return TankYou;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public void setCall(String call) {
        Call = call;
    }

    public void setDesign(String design) {
        Design = design;
    }

    public void setDelivery(String delivery) {
        Delivery = delivery;
    }

    public void setTankYou(String tankYou) {
        TankYou = tankYou;
    }
}
