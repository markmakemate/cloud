package model;

public class Developer {
    private String user;

    private String host;

    private String password;

    private Integer DEFAULT_SSH_PORT;

    private String checkValue;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDEFAULT_SSH_PORT() {
        return DEFAULT_SSH_PORT;
    }

    public void setDEFAULT_SSH_PORT(Integer DEFAULT_SSH_PORT) {
        this.DEFAULT_SSH_PORT = DEFAULT_SSH_PORT;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }
}
