package com.cheise_proj.newsapp_stage_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheise_proj.newsapp_stage_1.adapter.ItemClickListener;
import com.cheise_proj.newsapp_stage_1.adapter.NewsFeedAdapter;
import com.cheise_proj.newsapp_stage_1.loader.FeedLoader;
import com.cheise_proj.newsapp_stage_1.model.NewsFeed;
import com.cheise_proj.newsapp_stage_1.utils.Constants;
import com.cheise_proj.newsapp_stage_1.utils.QueryImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<NewsFeed>> {
    private ProgressBar loadingIndicator;
    private TextView mViewMessage;
    private NewsFeedAdapter newsFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingIndicator = findViewById(R.id.progressBar);
        mViewMessage = findViewById(R.id.tv_view_message);
        newsFeedAdapter = new NewsFeedAdapter();
        initRecyclerView();
        fetchNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refresh_action) {
            fetchNews();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnectivity();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.hasFixedSize();
        newsFeedAdapter.setItemClickListener(new ItemClickListener<NewsFeed>() {
            @Override
            public void onClick(NewsFeed data) {
                Uri webPage = Uri.parse(data.getWebUrl());
                Intent i = new Intent(Intent.ACTION_VIEW, webPage);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });
        recyclerView.setAdapter(newsFeedAdapter);
    }

    @SuppressWarnings("deprecation")
    private void fetchNews() {
        if (QueryImpl.sInstance().isConnected(this)) {
            mViewMessage.setText("");
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(Constants.Feed_LOADER_ID, null, this);
            return;
        }
        setViewMessage();
    }

    @NonNull
    @Override
    public Loader<List<NewsFeed>> onCreateLoader(int id, @Nullable Bundle args) {
        return new FeedLoader(this, QueryImpl.sInstance().getApiUrl());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsFeed>> loader, List<NewsFeed> data) {
        if (data != null && !data.isEmpty()) {
            hideProgressIndicator();
            newsFeedAdapter.submitList(data);
        } else {
            setViewMessage();
        }
    }

    private void setViewMessage() {
        hideProgressIndicator();
        mViewMessage.setText(R.string.no_content_available);
        checkInternetConnectivity();
    }

    private void checkInternetConnectivity() {
        if (QueryImpl.sInstance().isConnected(this)) {
            mViewMessage.setText("");
            return;
        }
        mViewMessage.setText(R.string.no_internet_connection);
    }

    private void hideProgressIndicator() {

        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsFeed>> loader) {
    }
}
