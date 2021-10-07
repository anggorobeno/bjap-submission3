package com.example.myviewmodel.view.activity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.example.myviewmodel.R;
import com.example.myviewmodel.data.source.remote.response.movie.MovieResult;
import com.example.myviewmodel.data.source.remote.response.tv.TvResult;
import com.example.myviewmodel.utils.Espresso;
import com.example.myviewmodel.utils.FakeDataDummy;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

public class MainActivityTest {
    private final ArrayList<MovieResult> dummyMovies = FakeDataDummy.generatePopularMovie();
    private final ArrayList<TvResult> dummyTv = FakeDataDummy.generatePopularTv();

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(Espresso.getEspressoIdlingResource());
    }

    @After
    public void tearDown(){
        IdlingRegistry.getInstance().unregister(Espresso.getEspressoIdlingResource());
    }

    @Test
    public void viewPager(){
        onView((withId(R.id.view_pager))).check(matches(isDisplayed()));
        onView((withId(R.id.view_pager))).perform(swipeLeft());
        onView(withId(R.id.view_pager)).perform(swipeRight());

    }
    @Test
    public void loadPopularMovie(){
        onView(withId(R.id.rvMovie)).check(matches(isDisplayed()));
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.scrollToPosition(dummyMovies.size()));
    }
    @Test
    public void loadDetailMovie(){
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.detailDesc)).check(matches(isDisplayed()));
        onView(withId(R.id.detailPoster)).check(matches(isDisplayed()));
    }
    @Test
    public void loadPopularTv(){
        onView(withText("TV SHOW")).perform(click());
        onView(withId(R.id.rvTv)).check(matches(isDisplayed()));
        onView(withId(R.id.rvTv)).perform(RecyclerViewActions.scrollToPosition(dummyTv.size()));
    }
    @Test
    public void loadDetailTv(){
        onView(withText("TV SHOW")).perform(click());
        onView(withId(R.id.rvTv)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.detailDesc)).check(matches(isDisplayed()));
        onView(withId(R.id.detailPoster)).check(matches(isDisplayed()));
    }
}