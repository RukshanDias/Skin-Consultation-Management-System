package Console;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public abstract class Person implements Serializable {
    private String name;
    private String surname;
    private LocalDate DOB;
    private String mobileNo;

    public Person(String name, String surname, LocalDate DOB, String mobileNo) {
        this.name = name;
        this.surname = surname;
        this.DOB = DOB;
        this.mobileNo = mobileNo;
    }

    // getters
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public LocalDate getDOB() {
        return DOB;
    }
    public String getMobileNo() {
        return mobileNo;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
