package kr.hs.dimigo.dudgns0507.hongikbook;

/**
 * Created by pyh42 on 2016-12-09.
 */

public class LoginInfo {
    private String user_type;

    private String status;

    private String password_hash;

    private String photofile1;

    private String photofile2;

    private String id;

    private String username;

    private String updated_at;

    private String nick;

    private String email;

    private String name;

    private String birthdate;

    private String sso_token;

    private String gender;

    private String created_at;

    public String getUser_type ()
    {
        return user_type;
    }

    public void setUser_type (String user_type)
    {
        this.user_type = user_type;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getPassword_hash ()
    {
        return password_hash;
    }

    public void setPassword_hash (String password_hash)
    {
        this.password_hash = password_hash;
    }

    public String getPhotofile1 ()
    {
        return photofile1;
    }

    public void setPhotofile1 (String photofile1)
    {
        this.photofile1 = photofile1;
    }

    public String getPhotofile2 ()
    {
        return photofile2;
    }

    public void setPhotofile2 (String photofile2)
    {
        this.photofile2 = photofile2;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getNick ()
    {
        return nick;
    }

    public void setNick (String nick)
    {
        this.nick = nick;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getBirthdate ()
    {
        return birthdate;
    }

    public void setBirthdate (String birthdate)
    {
        this.birthdate = birthdate;
    }

    public String getSso_token ()
    {
        return sso_token;
    }

    public void setSso_token (String sso_token)
    {
        this.sso_token = sso_token;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    @Override
    public String toString()
    {
        return "LoginInfo [user_type = "+user_type+", status = "+status+", password_hash = "+password_hash+", photofile1 = "+photofile1+", photofile2 = "+photofile2+", id = "+id+", username = "+username+", updated_at = "+updated_at+", nick = "+nick+", email = "+email+", name = "+name+", birthdate = "+birthdate+", sso_token = "+sso_token+", gender = "+gender+", created_at = "+created_at+"]";
    }
}
