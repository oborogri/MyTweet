package app.mytweet.models;

/**
 * Created by Grigore on 26/10/2016.
 */

public class User
{
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String _id;

    public User(String firstName, String lastName, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}