package app.mytweet.models;

import java.util.Date;
import java.util.Random;

/**
 * Created by Grigore on 08/10/2016.
 */

public class Tweet {

    public Long id;
    public Long date;
    public String tweeter;
    public String text;

    public Tweet() {

        id = new Random().nextLong();
        date = new Date().getTime();
        tweeter = ": none presently";

    }

    public void setText(String text) {
        this.text = text;
    }
}
