package sample;

public class Staff {
    protected String firstName;
    protected String lastName;
    protected long employeeID;
    protected String password;
    protected String employeeType;
    protected String employeeEmail;


    public Staff(String firstName, String lastName, long employeeID, String password, String employeeType, String employeeEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeID = employeeID;
        this.password = password;
        this.employeeType = employeeType;
        this.employeeEmail = employeeEmail;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public long getEmployeeID() {
        return employeeID;
    }

    public String getPassword() {
        return password;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }
}
