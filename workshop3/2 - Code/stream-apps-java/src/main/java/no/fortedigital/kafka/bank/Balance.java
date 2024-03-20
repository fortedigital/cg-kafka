package no.fortedigital.kafka.bank;

import java.time.Instant;

public class Balance {
    private int count;
    private int balance;
    private String time;

    public Balance() {
        this.time = Instant.now().toString();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public int getBalance() {
        return balance;
    }

    public String getTime() {
        return time;
    }
}
