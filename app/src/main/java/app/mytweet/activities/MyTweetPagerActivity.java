package app.mytweet.activities;

import app.mytweet.R;
import app.mytweet.app.MyTweetApp;
import app.mytweet.models.Portfolio;
import app.mytweet.models.Tweet;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;


import java.util.ArrayList;

public class MyTweetPagerActivity extends AppCompatActivity
{
    private ViewPager viewPager;
    private ArrayList<Tweet> tweets;
    private Portfolio portfolio;
    private PagerAdapter pagerAdapter;
    ActionBar actionBar;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);
        setTweetList();

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tweets);
        viewPager.setAdapter(pagerAdapter);
        setCurrentItem();
        actionBar = getSupportActionBar();
    }

    private void setTweetList()
    {
        MyTweetApp app = (MyTweetApp) getApplication();
        portfolio = app.portfolio;
        tweets = portfolio.tweets;
    }

    /*
* Ensure selected residence is shown in details view
*/
    private void setCurrentItem() {
        Long tweetId = (Long) getIntent().getSerializableExtra(TweetFragment.EXTRA_TWEET_ID);
        for (int i = 0; i < tweets.size(); i++) {
            if (tweets.get(i).id.equals(tweetId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    class PagerAdapter extends FragmentStatePagerAdapter
    {
        private ArrayList<Tweet>  tweets;

        public PagerAdapter(FragmentManager fm, ArrayList<Tweet> tweets)
        {
            super(fm);
            this.tweets = tweets;
        }

        @Override
        public int getCount()
        {
            return tweets.size();
        }

        @Override
        public Fragment getItem(int pos)
        {
            Tweet residence = tweets.get(pos);
            Bundle args = new Bundle();
            args.putSerializable(TweetFragment.EXTRA_TWEET_ID, residence.id);
            TweetFragment fragment = new TweetFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }
}

   /* public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new TweetFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }*/



