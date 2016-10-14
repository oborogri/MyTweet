package app.mytweet.models;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import app.mytweet.R;
import android.content.Context;


public class Tweet {

    public Long id;
    public Long date;

    public String sender;
    public String text;

    public static final String JSON_ID = "id";
    public static final String JSON_DATE = "date";
    public static final String JSON_SENDER = "sender";

    public Tweet() {


        id = unsignedLong();
        date = new Date().getTime();
        sender = "none presently";
    }

    public Tweet(JSONObject json) throws JSONException {
        id = json.getLong(JSON_ID);
        date = json.getLong(JSON_DATE);
        sender = json.getString(JSON_SENDER);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, Long.toString(id));
        json.put(JSON_DATE, date);
        json.put(JSON_SENDER, sender);
        return json;
    }


    public void setText(String text) {
        this.text = text;
    }

    private String dateString() {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }

    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <=0);
        return rndVal;
    }

}
