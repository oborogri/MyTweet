package app.mytweet.models;

/**
 * Created by Grigore on 07/10/2016.
 */

public class Tweeter
{
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public Tweeter(String firstName, String lastName, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
