package com.cheise_proj.newsapp_stage_1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cheise_proj.newsapp_stage_1.R;
import com.cheise_proj.newsapp_stage_1.model.NewsFeed;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class NewsFeedAdapter extends ListAdapter<NewsFeed, NewsFeedAdapter.FeedViewHolder> {
    private ItemClickListener<NewsFeed> itemClickListener;

    public NewsFeedAdapter() {
        super(new diffCallback());
    }

    public void setItemClickListener(ItemClickListener<NewsFeed> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_news_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.bind(getItem(position), itemClickListener);
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mTitle;
        TextView mAuthor;
        TextView mFeed;
        TextView mDate;

        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_thumbnail);
            mTitle = itemView.findViewById(R.id.item_title);
            mAuthor = itemView.findViewById(R.id.item_author);
            mFeed = itemView.findViewById(R.id.item_feed);
            mDate = itemView.findViewById(R.id.item_date);
        }

        void bind(final NewsFeed item, final ItemClickListener<NewsFeed> itemClickListener) {
            mTitle.setText(item.getSectionName());
            String author = "Author:" + item.getAuthor();
            mAuthor.setText(author);
            mFeed.setText(item.getWebTitle());
            mDate.setText(item.getFormattedDate());
            Picasso.get().load(item.getThumbnail()).into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(item);
                }
            });
        }
    }

    static class diffCallback extends DiffUtil.ItemCallback<NewsFeed> {
        @Override
        public boolean areItemsTheSame(@NonNull NewsFeed oldItem, @NonNull NewsFeed newItem) {
            return oldItem.getWebTitle().equals(newItem.getWebTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsFeed oldItem, @NonNull NewsFeed newItem) {
            return Objects.equals(oldItem, newItem);
        }
    }
}
