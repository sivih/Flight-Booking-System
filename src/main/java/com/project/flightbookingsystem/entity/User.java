package com.project.flightbookingsystem.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

    @Entity
	@Table(name = "users")
	public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @Column(name = "full_name", length = 100)
	    private String fullName;

	    @Column(name = "username", unique = true, length = 50)
	    private String username;

	    @Column(name = "email", unique = true, length = 100)
	    private String email;

	    @Column(name = "password", length = 100)
	    private String password;

	    @Column(name = "confirm_password", length = 100)
	    private String confirmPassword;

	    @Column(name = "country", length = 50)
	    private String country;

	    @Column(name = "mobile", length = 15)
	    private String mobile;

	    @Column(name = "gender", length = 10)
	    private String gender;

	    @Column(name = "dob")
	    private LocalDate dob;

	   
	    public User() {}

	    public User(String fullName, String username, String email, String password, String confirmPassword,
	                String country, String mobile, String gender, LocalDate dob) {
	        this.fullName = fullName;
	        this.username = username;
	        this.email = email;
	        this.password = password;
	        this.confirmPassword = confirmPassword;
	        this.country = country;
	        this.mobile = mobile;
	        this.gender = gender;
	        this.dob = dob;
	    }

	  

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getConfirmPassword() {
	        return confirmPassword;
	    }

	    public void setConfirmPassword(String confirmPassword) {
	        this.confirmPassword = confirmPassword;
	    }

	    public String getCountry() {
	        return country;
	    }

	    public void setCountry(String country) {
	        this.country = country;
	    }

	    public String getMobile() {
	        return mobile;
	    }

	    public void setMobile(String mobile) {
	        this.mobile = mobile;
	    }

	    public String getGender() {
	        return gender;
	    }

	    public void setGender(String gender) {
	        this.gender = gender;
	    }

	    public LocalDate getDob() {
	        return dob;
	    }

	    public void setDob(LocalDate dob) {
	        this.dob = dob;
	    }
	}


