package com.cheise_proj.newsapp_stage_1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.cheise_proj.newsapp_stage_1.model.NewsFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryImpl implements IQuery {
    private static QueryImpl INSTANCE = null;

    public static QueryImpl sInstance() {
        if (INSTANCE == null) {
            synchronized (QueryImpl.class) {
                INSTANCE = new QueryImpl();
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public List<NewsFeed> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);
        List<NewsFeed> newsList;
        String jsonResponse;
        try {
            jsonResponse = makeHttpRequest(url);
            newsList = extractResponseFromJson(jsonResponse);
            return newsList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(Constants.READ_TIMEOUT);
            urlConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
            urlConnection.setRequestMethod(Constants.REQUEST_METHOD);
            urlConnection.connect();

            // check code == 200
            if (urlConnection.getResponseCode() == Constants.HTTP_RESPONSE_CODE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readStreamResponse(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    @Override
    public String readStreamResponse(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    @Override
    public List<NewsFeed> extractResponseFromJson(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) return null;
        List<NewsFeed> newsList = new ArrayList<>();

        try {
            String webTitle;
            String sectionName;
            String webPublicationDate;
            String webUrl;
            String thumbnail = "";
            String author = "";
            // Create a JSONObject from the JSON response string.
            JSONObject baseJsonResponse = new JSONObject(jsonString);
            // Extract the JSONObject associated with the key string "response"
            JSONObject responseJsonObject = baseJsonResponse
                    .getJSONObject(Constants.JSON_KEY_RESPONSE);
            // Extract the JSONArray associated with the key string "results"
            JSONArray resultsArray = responseJsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentNews = resultsArray.getJSONObject(i);
                // Extract webTitle
                webTitle = currentNews.getString(Constants.JSON_KEY_WEB_TITLE);
                // Extract sectionName
                sectionName = currentNews.getString(Constants.JSON_KEY_SECTION_NAME);
                // Extract webPublicationDate
                webPublicationDate = currentNews.getString(Constants.JSON_KEY_WEB_PUBLICATION_DATE);
                // Extract webUrl
                webUrl = currentNews.getString(Constants.JSON_KEY_WEB_URL);
                // Extract fields JSONObject
                JSONObject thumbObj = currentNews.getJSONObject(Constants.JSON_KEY_FIELDS);
                // Extract thumbnail
                try {
                    thumbnail = thumbObj.getString(Constants.JSON_KEY_THUMBNAIL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Extract the JSONArray associated with the key "tags"
                JSONArray tagsArray = currentNews.getJSONArray(Constants.JSON_KEY_TAGS);
                if (tagsArray.length() > 0) {
                    for (int j = 0; j < 1; j++) {
                        // extract author object
                        JSONObject authorObj = tagsArray.getJSONObject(j);
                        try {
                            author = authorObj.getString(Constants.JSON_KEY_AUTHOR);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                NewsFeed news = new NewsFeed();
                news.setSectionName(sectionName);
                news.setWebTitle(webTitle);
                news.setWebPublicationDate(webPublicationDate);
                news.setWebUrl(webUrl);
                news.setAuthor(author);
                news.setThumbnail(thumbnail);
                newsList.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    public String getApiUrl() {
        Uri baseUri = Uri.parse(Constants.BASE_URL);
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendQueryParameter(Constants.KEY_SHOW_TAGS, Constants.KEY_CONTRIBUTOR);
        builder.appendQueryParameter(Constants.KEY_SHOW_FIELD, Constants.KEY_ALL);
        builder.appendQueryParameter(Constants.KEY_PAGE_SIZE, Constants.MAX_NEWS);
        builder.appendQueryParameter(Constants.API_KEY, Constants.API_KEY_VALUE);
        return builder.toString();
    }


}
