package com.appease.testdroid.viewmodels;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appease.testdroid.R;
import com.appease.testdroid.common.Constant;
import com.appease.testdroid.models.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    private List<NewsItem> newsItems;
    private Context context;

    public NewsAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public int getCount() {
        if (newsItems != null)
            return newsItems.size();
        return 0;
    }

    @Override
    public NewsItem getItem(int position) {
        if (newsItems != null)
            return newsItems.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (newsItems != null)
            return newsItems.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_news, null);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.textNewsTitle);
            viewHolder.date = (TextView) convertView.findViewById(R.id.textNewsDate);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageNews);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final NewsItem newsItem = getItem(position);
        viewHolder.title.setText(newsItem.getTitle());
        viewHolder.date.setText(newsItem.getPubDate());
        viewHolder.link = newsItem.getLink();

        String imageLink = newsItem.getImageLink();
        Picasso.with(context).load(imageLink).into(viewHolder.image);

        return convertView;
    }

    public void setNewsItems(List<NewsItem> newsEntries) {
        if(Constant.Debug) Log.d("NewsAdapter", "setNewsItems");
        this.newsItems = newsEntries;
    }

    static class ViewHolder {
        TextView title;
        String link;
        TextView date;
        ImageView image;
    }
}
