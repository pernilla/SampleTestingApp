package com.appease.testdroid.views;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.appease.testdroid.R;
import com.appease.testdroid.common.Constant;
import com.appease.testdroid.models.Channel;
import com.appease.testdroid.models.NewsItem;
import com.appease.testdroid.services.FeedParser;
import com.appease.testdroid.viewmodels.ImagesAdapter;
import com.appease.testdroid.viewmodels.NewsAdapter;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private static final String TAG = NewsFragment.class.getSimpleName();

    private NewsAdapter newsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(Constant.Debug) Log.d(TAG, "onCreateView()");
        super.onViewCreated(view, savedInstanceState);

        ListView listViewNews = (ListView)getActivity().findViewById(R.id.listViewNews);
        newsAdapter = new NewsAdapter(getActivity(), R.layout.item_news);
        listViewNews.setAdapter(newsAdapter);

        String url = "http://www.aftonbladet.se/sportbladet/rss.xml";
        new ReadFromStream(getActivity()).execute(url);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((Welcome) activity).onSectionAttached(<
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public void print() {
        Toast.makeText(getActivity(), "Done parsing... ", Toast.LENGTH_LONG).show();
    }

    public class ReadFromStream extends AsyncTask<String, Void, String> {

        private Context context;

        public ReadFromStream(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... sectionsUrl) {
            if(Constant.Debug) Log.d(TAG, "doInBackground()");

            String json = null;

            try {
                String url = sectionsUrl[0];
                InputStream inputStream = new URL(url).openStream();

                FeedParser feedParser = new FeedParser();
                Channel channel = feedParser.parse(inputStream);

                Gson gson = new Gson();
                json = gson.toJson(channel);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return json;
        }

        @Override
        protected void onCancelled() {
            if(Constant.Debug) Log.d(TAG, "onCancelled()");

            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String content) {
            if(Constant.Debug) Log.d(TAG, "onPostExecute()");

            Gson gson = new Gson();
            Channel channel = gson.fromJson(content, Channel.class);

            newsAdapter.setNewsItems(channel.getNewsItems());
            newsAdapter.notifyDataSetChanged();
        }
    }
}