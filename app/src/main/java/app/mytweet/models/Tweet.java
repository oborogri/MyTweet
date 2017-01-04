package app.mytweet.models;

import java.util.Date;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import app.mytweet.R;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Tweet extends AppCompatActivity{

    public String id;
    public String date;
    public  int max_count;

    public String sender;
    public String text;

    public static final String JSON_ID = "id";
    public static final String JSON_DATE = "date";
    public static final String JSON_CONTENT = "text";
    public static final String JSON_SENDER = "sender";

    public Tweet() {

        id = getId();
        date = getDateString();
        sender = "";
        text = "";
    }

    public Tweet(JSONObject json) throws JSONException {
        id = json.getString(JSON_ID);
        date = json.getString(JSON_DATE);
        sender = json.getString(JSON_SENDER);
        text = json.getString(JSON_CONTENT);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id);
        json.put(JSON_DATE, date);
        json.put(JSON_SENDER, sender);
        json.put(JSON_CONTENT, text);
        return json;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getDateString() {
        return dateString();
    }

    private String dateString() {
        Long now  = new Date().getTime();
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, now).toString();
    }


    /**+
     * Generate a long greater than zero
     * @return Unsigned Long value grater than zero
     */
  private String getId() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <=0);
        return Long.toString(rndVal);
    }

}
