package no.fortedigital.kafka.producer;

public class Value {

    private String f1;
    private Integer f2;
    private boolean f3;

    public void setF2(Integer f2) {
        this.f2 = f2;
    }

    public boolean isF3() {
        return f3;
    }

    public void setF3(boolean f3) {
        this.f3 = f3;
    }

    public Integer getF2() {
        return f2;
    }

    public void setF2(int f2) {
        this.f2 = f2;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }
}
