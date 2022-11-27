package models;

public record EmployeeInfo(String userName, String password, Integer roleID) {

    public static EmployeeBuilder getBuilder() {
        return new EmployeeBuilder();
    }

    public static class EmployeeBuilder {
        private String userName;
        private String password;
        private Integer roleID;


        public EmployeeBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public EmployeeBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public EmployeeBuilder withRoleID(Integer roleID) {
            this.roleID = roleID;
            return this;
        }

        public EmployeeInfo build() {
            return new EmployeeInfo(userName, password, roleID);
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
        if (obj == null || obj.getClass() != EmployeeInfo.class)
            return false;

        EmployeeInfo other = (EmployeeInfo) obj;
        return this.userName.equals(other.userName) && this.password.equals(other.password);
    }

    public boolean equalsUserName(Object obj) {
        EmployeeInfo other = (EmployeeInfo) obj;
        return this.userName.equals(other.userName);
    }
}
