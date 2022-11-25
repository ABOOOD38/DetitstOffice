package models;

import java.math.BigDecimal;
import java.util.List;

public record Patient(PersonInfo personInfo, BigDecimal owedBalance, BigDecimal payedBalance, List<Visit> visits) {

    public static PatientBuilder getBuilder() {
        return new PatientBuilder();
    }

    public static class PatientBuilder {
        private BigDecimal owedBalance = new BigDecimal(0);
        private BigDecimal payedBalance = new BigDecimal(0);
        private List<Visit> visits;

        private final PersonInfo.PersonInfoBuilder personInfoBuilder = PersonInfo.getBuilder();

        public PatientBuilder withName(String name) {
            personInfoBuilder.withName(name);
            return this;
        }

        public PatientBuilder withPhoneNumber(String phoneNumber) {
            personInfoBuilder.withPhoneNumber(phoneNumber);
            return this;
        }

        public PatientBuilder withEmail(String email) {
            personInfoBuilder.withEmail(email);
            return this;
        }

        public PatientBuilder withOwedBalance(BigDecimal owedBalance) {
            this.owedBalance = owedBalance;
            return this;
        }

        public PatientBuilder withPayedBalance(BigDecimal payedBalance) {
            this.payedBalance = payedBalance;
            return this;
        }

        public PatientBuilder withVisits(List<Visit> visits) {
            this.visits = visits;
            return this;
        }

        public Patient build() {
            return new Patient(personInfoBuilder.build(), owedBalance, payedBalance, visits);
        }
    }

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
