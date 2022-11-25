package models;

public record Employee(String userName, String password) {

    public static EmployeeBuilder getBuilder() {
        return new EmployeeBuilder();
    }

    public static class EmployeeBuilder {
        private String userName;
        private String password;


        public EmployeeBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public EmployeeBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Employee build() {
            return new Employee(userName, password);
        }
    }

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
