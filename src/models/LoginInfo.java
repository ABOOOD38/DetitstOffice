package models;

public record LoginInfo(String userName, String password, Integer roleID) {
    // TODO: 11/29/22 adapt the models to model the data base tables 

    public static LoginInfoBuilder getBuilder() {
        return new LoginInfoBuilder();
    }

    public static class LoginInfoBuilder {
        private String userName;
        private String password;
        private Integer roleID;


        public LoginInfoBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public LoginInfoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public LoginInfoBuilder withRoleID(Integer roleID) {
            this.roleID = roleID;
            return this;
        }

        public LoginInfo build() {
            return new LoginInfo(userName, password, roleID);
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
        if (obj == null || obj.getClass() != LoginInfo.class)
            return false;

        LoginInfo other = (LoginInfo) obj;
        return this.userName.equals(other.userName) && this.password.equals(other.password);
    }

    public boolean equalsUserName(Object obj) {
        LoginInfo other = (LoginInfo) obj;
        return this.userName.equals(other.userName);
    }
}
