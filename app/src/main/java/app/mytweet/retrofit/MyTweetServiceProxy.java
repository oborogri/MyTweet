package app.mytweet.retrofit;

import app.mytweet.models.Tweet;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import static android.os.FileObserver.DELETE;

public interface MyTweetServiceProxy
{
    @POST("/api/tweets")
    Call<Tweet> createTweet(@Body Tweet tweet);

    @DELETE("/api/tweets/{id}")
    Call<String> deleteTweet(@Path("id") String id);

    @GET("/api/tweets")
    Call<List<Tweet>> getResidences();

}