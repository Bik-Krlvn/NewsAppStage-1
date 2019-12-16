package com.cheise_proj.newsapp_stage_1.utils;

import android.content.Context;

import com.cheise_proj.newsapp_stage_1.model.NewsFeed;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public interface IQuery {
    boolean isConnected(Context context);

    List<NewsFeed> fetchNewsData(String requestUrl);

    URL createUrl(String requestUrl);

    String makeHttpRequest(URL url) throws IOException;

    String readStreamResponse(InputStream inputStream) throws IOException;

    List<NewsFeed> extractResponseFromJson(String jsonString);

    String getApiUrl();
}
