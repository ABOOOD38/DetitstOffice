package models;

public record PersonalInfo(String name, String phoneNumber, String email) {

    public static PersonInfoBuilder getBuilder() {
        return new PersonInfoBuilder();
    }

    public static class PersonInfoBuilder {
        private String name;
        private String phoneNumber;
        private String email = "No Email";

        public PersonInfoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PersonInfoBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PersonInfoBuilder withEmail(String email) {
            if (!email.isEmpty())
                this.email = email;
            return this;
        }

        public PersonalInfo build() {
            return new PersonalInfo(name, phoneNumber, email);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != PersonalInfo.class)
            return false;

        PersonalInfo other = (PersonalInfo) obj;
        return this.name.equals(other.name) && this.email.equals(other.email) && this.phoneNumber.equals(other.phoneNumber);
    }

    public boolean equalsEmailOrPhoneNum(Object obj) {
        PersonalInfo other = (PersonalInfo) obj;
        return email.equals(other.email) || phoneNumber.equals(other.phoneNumber);
    }

    public boolean equalsNameAndPhoneNum(Object obj) {
        PersonalInfo other = (PersonalInfo) obj;
        return name.equalsIgnoreCase(other.name) && phoneNumber.equals(other.phoneNumber);
    }

    @Override
    public String toString() {
        return "Person{" +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
