package com.example.dennis.medical;

public class UserProfile {
    private String userFullName;
    private String userEmail;
    private String userPassword;
    private String userStaffID, userAge, userHandphone, userHomeAddress, userPosition, userDepartment, userHospital;
    private String userImageurl;
    private String userId;



    private String userstatus;


    public UserProfile(){

    }

    public UserProfile(String FullName, String Email, String Password, String StaffID, String Age,
                       String Handphone, String HomeAddress, String Position, String Department,
                       String Hospital, String userId) {
        this.userFullName = FullName;
        this.userEmail = Email;
        this.userPassword = Password;
        this.userStaffID = StaffID;
        this.userAge = Age;
        this.userHandphone = Handphone;
        this.userHomeAddress = HomeAddress;
        this.userPosition = Position;
        this.userDepartment = Department;
        this.userHospital = Hospital;

//        this.userImageurl = userImageurl;
    }


    public String getuserPassword() {
        return userPassword;
    }

    public String getuserFullName() {
        return userFullName;
    }

    public String getuserEmail() {
        return userEmail;
    }

    public String getuserStaffID() {
        return userStaffID;
    }

    public String getuserAge() {
        return userAge;
    }

    public String getuserHandphone() {
        return userHandphone;
    }

    public String getuserHomeAddress() {
        return userHomeAddress;
    }

    public String getuserPosition() {
        return userPosition;
    }

    public String getuserDepartment() {
        return userDepartment;
    }

    public String getuserHospital() {
        return userHospital;
    }

    public String getUserImageurl() {
        return userImageurl;
    }

    public String getUserId() {
        return userId;
    }

    public String getuserStatus() {
        return userstatus;
    }
}
