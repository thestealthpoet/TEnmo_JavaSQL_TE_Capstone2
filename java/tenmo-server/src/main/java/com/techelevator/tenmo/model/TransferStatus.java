package com.techelevator.tenmo.model;

public class TransferStatus {

    private int transferId;
    private String transferStatusDescription;

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferStatusDescription() {
        return transferStatusDescription;
    }

    public void setTransferStatusDescription(String transferStatusDescription) {
        this.transferStatusDescription = transferStatusDescription;
    }

    public TransferStatus(int transferId, String transferStatusDescription) {
        this.transferId = transferId;
        this.transferStatusDescription = transferStatusDescription;


    }
}
