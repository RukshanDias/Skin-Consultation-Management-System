package Console;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Person implements Serializable {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


}
