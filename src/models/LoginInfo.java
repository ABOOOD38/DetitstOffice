package models;

public record LoginInfo(Integer ID, String userName, String password, Integer roleID) {
    public static LoginInfoBuilder getBuilder() {
        return new LoginInfoBuilder();
    }

    public static class LoginInfoBuilder {
        private Integer ID;
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

        public LoginInfoBuilder withLoginID(Integer ID) {
            this.ID = ID;
            return this;
        }

        public LoginInfo build() {
            return new LoginInfo(ID, userName, password, roleID);
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
