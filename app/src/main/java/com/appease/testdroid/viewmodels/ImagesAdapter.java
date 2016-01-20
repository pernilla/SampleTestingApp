package com.appease.testdroid.viewmodels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appease.testdroid.R;
import com.appease.testdroid.common.Constant;
import com.appease.testdroid.models.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesAdapter extends ArrayAdapter<ImageItem> {

    private static final String TAG = ImagesAdapter.class.getSimpleName();
    private Context context;
    private List<ImageItem> imageItems;

    public ImagesAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public int getCount() {
        if (imageItems != null)
            return imageItems.size();
        return 0;
    }

    @Override
    public ImageItem getItem(int position) {
        if (imageItems != null)
            return imageItems.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (imageItems != null)
            return imageItems.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_image, null);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.textImageTitle);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.tags = (TextView) convertView.findViewById(R.id.textImageTags);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ImageItem imageItem = getItem(position);
        viewHolder.title.setText(imageItem.getAuthor());
        viewHolder.tags.setText(imageItem.getDateTaken());

        if(hasMedia(imageItem)) {
            String image = imageItem.getMedia().get("m");
            if(!TextUtils.isEmpty(image)) {
                Picasso.with(context).load(image).into(viewHolder.image);
            }
        }

        return convertView;
    }

    private boolean hasMedia(ImageItem imageItem) {
        return imageItem.getMedia() != null && imageItem.getMedia().containsKey("m");
    }

    public void setImageItems(List<ImageItem> imageItems) {
        if(Constant.Debug) Log.d(TAG, "setImageItems");
        this.imageItems = imageItems;
    }

    static class ViewHolder {
        TextView title;
        TextView tags;
        ImageView image;
    }
}
