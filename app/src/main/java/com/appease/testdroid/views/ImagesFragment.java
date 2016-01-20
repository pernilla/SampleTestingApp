package com.appease.testdroid.views;

import android.app.Activity;
import android.app.Fragment;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.appease.testdroid.R;
import com.appease.testdroid.common.Constant;
import com.appease.testdroid.models.Channel;
import com.appease.testdroid.models.FlickrImages;
import com.appease.testdroid.models.ImageItem;
import com.appease.testdroid.viewmodels.ImagesAdapter;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

public class ImagesFragment extends Fragment {

    private static final String TAG = ImagesFragment.class.getSimpleName();

    //private static String url = "http://www.flickr.com/services/feeds/photos_public.gne?tags=coding&format=json&jsoncallback=?";
    private static String url = "http://www.flickr.com/services/feeds/photos_public.gne?tags=coding&format=json&nojsoncallback=1";

    private ImagesAdapter imagesAdapter;

    public ImagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_images, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ListView listViewNews = (ListView)getActivity().findViewById(R.id.listViewContacts);
        imagesAdapter = new ImagesAdapter(getActivity(), R.layout.item_image);
        listViewNews.setAdapter(imagesAdapter);

        FlickrImageGetterAsyncTask sectionTask = new FlickrImageGetterAsyncTask();
        if(Constant.Debug) Log.d(TAG, "url: " + url);
        sectionTask.execute(url);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    public class FlickrImageGetterAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... sectionsUrl) {
            HttpURLConnection connection = null;
            String content = null;
            try {
                java.net.URL url = new URL(sectionsUrl[0]);
                if (Constant.Debug) Log.d(TAG, "Download from: " + sectionsUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);
                connection.connect();

                // HTTP 200 OK
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream input = connection.getInputStream();
                    content = CharStreams.toString(new InputStreamReader(input, Charsets.UTF_8));
                    Closeables.closeQuietly(input);
                }
            } catch (UnknownHostException e) {
                if (Constant.Debug) Log.d(TAG, "error: " + e.getMessage());
            } catch (Exception e) {
                if (Constant.Debug) Log.d(TAG, "error: " + e.getMessage());
                getActivity().setProgressBarIndeterminateVisibility(false);
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return content;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);
            if (TextUtils.isEmpty(content)) {
                return;
            }

            FlickrImages receivedSectionDetail = getImages(content);
            if (receivedSectionDetail == null || !receivedSectionDetail.hasImageItems()) {
                if (Constant.Debug) Log.d(TAG, "No images found");
                return;
            }

            if (Constant.Debug) Log.d(TAG, "images found");

            imagesAdapter.setImageItems(receivedSectionDetail.getImages());
            imagesAdapter.notifyDataSetChanged();

            getActivity().setProgressBarIndeterminateVisibility(false);
        }

        private FlickrImages getImages(String content) {
            if (Constant.Debug) Log.d(TAG, "getImages(); content is: " + content);
            FlickrImages flickrImages = new FlickrImages();
            try {
                Gson gson = new Gson();
                flickrImages = gson.fromJson(content, FlickrImages.class);

            } catch (Exception e) {
                if (e == null) {
                    if (Constant.Debug) Log.d(TAG, "error when getting images: e is null");
                    return null;
                }
                if (Constant.Debug) Log.d(TAG, "error when getting images: " + e.getMessage());
                e.printStackTrace();
            }
            return flickrImages;
        }
    }
}