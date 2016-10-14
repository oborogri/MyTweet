package app.mytweet.helpers;

import android.util.Log;

/**
 * Created by Grigore on 14/10/2016.
 */

public class LogHelpers {

    public static void info(Object parent, String message)
    {
        Log.i(parent.getClass().getSimpleName(), message);
    }
}
