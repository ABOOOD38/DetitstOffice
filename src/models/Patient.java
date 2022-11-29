package models;

import java.math.BigDecimal;

public record Patient(Integer patient_ID, PersonalInfo personalInfo, BigDecimal owedBalance, BigDecimal payedBalance,
                      Integer visits_ID,
                      Integer appointment_ID) {

    public static PatientBuilder getBuilder() {
        return new PatientBuilder();
    }

    public static class PatientBuilder {
        private BigDecimal owedBalance = new BigDecimal(0);
        private BigDecimal payedBalance = new BigDecimal(0);
        private Integer visitsID;
        private Integer appointment_ID;
        private Integer Patient_ID;
        private final PersonalInfo.PersonInfoBuilder personInfoBuilder = PersonalInfo.getBuilder();

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

        public PatientBuilder withVisitID(Integer visitsID) {
            this.visitsID = visitsID;
            return this;
        }

        public PatientBuilder withAppointmentID(Integer appointment_ID) {
            this.appointment_ID = appointment_ID;
            return this;
        }

        public PatientBuilder withPatientID(Integer Patient_ID) {
            this.Patient_ID = Patient_ID;
            return this;
        }

        public Patient build() {
            return new Patient(Patient_ID, personInfoBuilder.build(), owedBalance, payedBalance, visitsID, appointment_ID);
        }
    }

    @Override
    public String toString() {
        return personalInfo.toString() + "\n" +
                "Patient{" +
                "owedBalance=" + owedBalance +
                ", payedBalance=" + payedBalance +
                ", visits=" + visits_ID +
                ", app_id=" + appointment_ID +
                '}';
    }
}
