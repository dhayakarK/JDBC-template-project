package com.assignment.springassignment.entities;

import org.hibernate.annotations.Generated;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



public class Employee {

    @GeneratedValue
    private int id;
    @NotEmpty
    @Size(min=2,message = "First Name should not be blank")
    private String firstname;

      @NotEmpty
     @Size(min=2,message = "last name should not be blank")
    private String lastname;

    @Size(min = 1,max = 100)
    @Email(message = "should be in format",regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    private long phone;
   public Employee()
   {
       super();
   }
    public Employee(int id, String fname, String lname, String email, long phone) {
        this.id=id;
        this.firstname=fname;
        this.lastname=lname;
        this.email=email;
        this.phone=phone;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return this.phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
