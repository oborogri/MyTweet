package app.mytweet.models;

import java.util.Date;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import app.mytweet.R;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static app.mytweet.R.id.text_tweet;


public class Tweet extends AppCompatActivity{

    public Long id;
    public Long date;
    public int max_count = 140;

    public String sender;
    public String text;

    public static final String JSON_ID = "id";
    public static final String JSON_DATE = "date";
    public static final String JSON_SENDER = "sender";

    public Tweet() {

        id = unsignedLong();
        date = new Date().getTime();
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

    public String getText() {
        return text;
    }

    public String getDateString() {
        return "Sent:" + dateString();
    }

    private String dateString() {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }

    /**
     * Generate a long greater than zero
     * @return Unsigned Long value grater than zero
     */
    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <=0);
        return rndVal;
    }

}
