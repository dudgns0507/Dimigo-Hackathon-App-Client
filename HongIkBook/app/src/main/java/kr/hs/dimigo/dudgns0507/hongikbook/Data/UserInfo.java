package kr.hs.dimigo.dudgns0507.hongikbook.Data;

/**
 * Created by pyh42 on 2016-12-09.
 */

public class UserInfo {
    private String dormitory;

    private String Class;

    private String photofile1;

    private String number;

    private String photofile2;

    private String rfcard_uid;

    private String username;

    private String name;

    private String gender;

    private String grade;

    private String user_id;

    private String room_num;

    private String serial;

    public String getDormitory ()
    {
        return dormitory;
    }

    public void setDormitory (String dormitory)
    {
        this.dormitory = dormitory;
    }

    public String getmClass()
    {
        return Class;
    }

    public void setmClass (String Class)
    {
        this.Class = Class;
    }

    public String getPhotofile1 ()
    {
        return photofile1;
    }

    public void setPhotofile1 (String photofile1)
    {
        this.photofile1 = photofile1;
    }

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    public String getPhotofile2 ()
    {
        return photofile2;
    }

    public void setPhotofile2 (String photofile2)
    {
        this.photofile2 = photofile2;
    }

    public String getRfcard_uid ()
    {
        return rfcard_uid;
    }

    public void setRfcard_uid (String rfcard_uid)
    {
        this.rfcard_uid = rfcard_uid;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getGrade ()
    {
        return grade;
    }

    public void setGrade (String grade)
    {
        this.grade = grade;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getRoom_num ()
    {
        return room_num;
    }

    public void setRoom_num (String room_num)
    {
        this.room_num = room_num;
    }

    public String getSerial ()
    {
        return serial;
    }

    public void setSerial (String serial)
    {
        this.serial = serial;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dormitory = "+dormitory+", class = "+Class+", photofile1 = "+photofile1+", number = "+number+", photofile2 = "+photofile2+", rfcard_uid = "+rfcard_uid+", username = "+username+", name = "+name+", gender = "+gender+", grade = "+grade+", user_id = "+user_id+", room_num = "+room_num+", serial = "+serial+"]";
    }
}
