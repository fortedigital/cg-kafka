package no.fortedigital.kafka.bank;

class Transaction {
    String name;
    int amount;
    String time;

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
