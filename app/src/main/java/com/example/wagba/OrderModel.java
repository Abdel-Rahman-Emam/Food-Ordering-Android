package com.example.wagba;

public class OrderModel {
    private String gateTime;
    private String resName;
    public enum orderState {WaitingForConfirmation, Preparing, Delivering, Delivered, Cancelled}
    orderState currOrderState;

    public orderState getCurrOrderState() {
        return currOrderState;
    }

    public void setCurrOrderState(orderState currOrderState) {
        this.currOrderState = currOrderState;
    }
    public OrderModel(){}
    public OrderModel(String gateTime, String resName, orderState currOrderState) {
        this.gateTime = gateTime;
        this.resName = resName;
        this.currOrderState = currOrderState;
    }
    public void setGateTime(String gateTime) {
        this.gateTime = gateTime;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getGateTime() {
        return gateTime;
    }

    public String getResName() {
        return resName;
    }


}
