package Controllers;

public class OrderStatus {
    private String order;
    private String orderAdress;
    private String orderPayment;
    private String orderTotalPrice;
    private String orderStatus;
    private String orderNote;
    private String orderID;

    public OrderStatus(String order,String orderAdress,String orderPayment,String orderTotalPrice,String orderStatus,String orderNote){
        this.order = order;
        this.orderAdress = orderAdress;
        this.orderPayment = orderPayment;
        this.orderTotalPrice = orderTotalPrice;
        this.orderStatus = orderStatus;
        this.orderNote = orderNote;
    }
    public OrderStatus(String orderID,String order,String orderAdress,String orderPayment,String orderTotalPrice,String orderStatus,String orderNote){
        this.order = order;
        this.orderAdress = orderAdress;
        this.orderPayment = orderPayment;
        this.orderTotalPrice = orderTotalPrice;
        this.orderStatus = orderStatus;
        this.orderNote = orderNote;
        this.orderID = orderID;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderAdress() {
        return orderAdress;
    }

    public void setOrderAdress(String orderAdress) {
        this.orderAdress = orderAdress;
    }

    public String getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(String orderPayment) {
        this.orderPayment = orderPayment;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
