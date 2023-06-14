package com.example.expensemanager;

public class Transactions {
    String tName, tAmount, tAccount, tType, tReason, balance;
    long createdAt;

    public Transactions() {}

    public Transactions(String tName, String tAmount, String tAccount, String tType, String tReason, String balance, long createdAt) {
        this.tName = tName;
        this.tAmount = tAmount;
        this.tAccount = tAccount;
        this.tType = tType;
        this.tReason = tReason;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettAmount() {
        return tAmount;
    }

    public void settAmount(String tAmount) {
        this.tAmount = tAmount;
    }

    public String gettAccount() {
        return tAccount;
    }

    public void settAccount(String tAccount) {
        this.tAccount = tAccount;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    public String gettReason() {
        return tReason;
    }

    public void settReason(String tReason) {
        this.tReason = tReason;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
