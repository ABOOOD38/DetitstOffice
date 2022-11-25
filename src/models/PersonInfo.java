package models;

public record PersonInfo(String name, String phoneNumber, String email) {
    private static final String DEFAULT_EMAIL_STRING = "No email";

    public PersonInfo(final String name, final String phoneNumber) {
        this(name, phoneNumber, DEFAULT_EMAIL_STRING);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != PersonInfo.class)
            return false;

        PersonInfo other = (PersonInfo) obj;
        return this.name.equals(other.name) && this.email.equals(other.email) && this.phoneNumber.equals(other.phoneNumber);
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
