package com.cheise_proj.newsapp_stage_1.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.cheise_proj.newsapp_stage_1.model.NewsFeed;
import com.cheise_proj.newsapp_stage_1.utils.QueryImpl;

import java.util.List;

public class FeedLoader extends AsyncTaskLoader<List<NewsFeed>> {
    private String url;

    public FeedLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }

    @Nullable
    @Override
    public List<NewsFeed> loadInBackground() {
        if (url == null) {
            return null;
        }
        List<NewsFeed> newsData;
        newsData = QueryImpl.sInstance().fetchNewsData(url);
        return newsData;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


}
