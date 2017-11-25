package com.stefancouture.programmerdevtools;


public class ListItems {
    private String studId;
    private String firstName;
    private String lastName;
    private String gpa;
    private boolean isValid; //used to determine whether or not the search was successful


    public ListItems(){
    }

    public void setStudId(String id){
        studId = id;
    }

    public void setFirstName(String fName){
        firstName = fName;
    }

    public void setLastName(String lName){
        lastName = lName;
    }

    public void setGpa(String gpa){
        this.gpa = gpa;
    }

    public void setIsValid(boolean isValid){
        this.isValid = isValid;
    }

    public String getStudId() {
        return studId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGpa() {
        return gpa;
    }

    public boolean getIsValid() {return isValid;}

}
