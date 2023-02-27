package com.example.studentauotmaticattendance.Teacher_Section.View_Teacher;

public class viewteacherobject {

    private String profilepic;
    private String name;
    private String department;
    private  String phone;
    private String email;

    public viewteacherobject(String profilepic, String name, String department, String phone,String email){

       this.department=department;
        this.name=name;
        this.profilepic=profilepic;
        this.phone=phone;
        this.email=email;

    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}

