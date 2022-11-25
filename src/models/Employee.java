package models;

public record Employee(String userName, String password) {

    @Override
    public String toString() {
        return "Employee{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Employee.class)
            return false;

        Employee other = (Employee) obj;
        return this.userName.equals(other.userName) && this.password.equals(other.password);
    }
}
