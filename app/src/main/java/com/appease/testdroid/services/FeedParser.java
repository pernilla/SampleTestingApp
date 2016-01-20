package com.appease.testdroid.services;

import android.net.ParseException;
import android.util.Log;
import android.util.Xml;

import com.appease.testdroid.common.Constant;
import com.appease.testdroid.models.Channel;
import com.appease.testdroid.models.NewsItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FeedParser {

    private static final String TAG = FeedParser.class.getSimpleName();

    private static final String ns = null;

    public Channel parse(InputStream inputStream) throws XmlPullParserException, IOException, ParseException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return parseXML(parser);
        } finally {
            inputStream.close();
        }
    }

    private Channel parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {

        Channel channel = new Channel();
        int eventType = parser.getEventType();
        NewsItem newsItem = null;
        boolean channelOccurred = false;

        try {
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equals("item")) {
                            newsItem = new NewsItem();
                        } else if (name.equals("channel")) {
                            channelOccurred = true;
                        } else if (newsItem != null) {
                            if (channelOccurred) {
                                try {
                                    channel.setDescription(parser.nextText());
                                    channelOccurred = false;
                                } catch (XmlPullParserException e) {
                                    if (Constant.Debug) Log.d(TAG, e.getMessage());
                                }
                            } else if (name.equals("guid")) {
                                String value = safeNextText(parser);
                                newsItem.setLink(value);
                                //newsItem.setLink(parser.nextText());
                            } else if (name.equals("title")) {
                                newsItem.setTitle(safeNextText(parser));
                                //newsItem.setTitle(parser.nextText());
                            } else if (name.equals("description")) {
                                newsItem.setImageLink(safeNextUrl(parser));
                                //newsItem.setImageLink(parser.nextText());
                            } else if (name.equals("pubDate")) {
                                newsItem.setPubDate(safeNextText(parser));
                                //newsItem.setPubDate(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equals("item") && newsItem != null) {
                            channel.addNewsItem(newsItem);
                            newsItem = null;
                        }
                }

                eventType = parser.next();
            }

        } catch (Exception e) {
            if (Constant.Debug) Log.d(TAG, e.getMessage());
        }

        return channel;
    }

    private String safeNextUrl(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = parser.nextText();
        String link = getLinkFromUrl(result);
        if (parser.getEventType() != XmlPullParser.END_TAG) {
            parser.nextTag();
        }
        return link;
    }

    private String getLinkFromUrl(String text) {
        int indexOfEqual = text.indexOf("=");
        int indexOfLast = text.indexOf(".JPG");
        if(indexOfLast == -1) {
            indexOfLast = text.indexOf(".jpg");
        }
        return text.substring(indexOfEqual + 2, indexOfLast + 4);
    }

    private String safeNextText(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = parser.nextText();
        if (parser.getEventType() != XmlPullParser.END_TAG) {
            parser.nextTag();
        }
        return result;
    }
}