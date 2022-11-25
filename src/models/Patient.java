package models;

import java.math.BigDecimal;
import java.util.List;

public record Patient(PersonInfo personInfo, BigDecimal owedBalance, BigDecimal payedBalance, List<Visit> visits) {
    @Override
    public String toString() {
        return personInfo.toString() + "\n" +
                "Patient{" +
                "owedBalance=" + owedBalance +
                ", payedBalance=" + payedBalance +
                ", visits=" + visits +
                '}';
    }
}
