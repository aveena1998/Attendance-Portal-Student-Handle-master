package com.example.studentauotmaticattendance.Message_Section;

public class viewobject {
    String Name;
    String message;
    public viewobject(String Name,String message)
    {
        this.Name=Name;
        this.message=message;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
