package com.popland.pop.playlistyoutubeapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hai on 01/07/2016.
 */
public class CustomListAdapter extends ArrayAdapter<VideoList> {
    public CustomListAdapter(Context context, int resource, List<VideoList> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
        ImageView imageView;
        TextView tvTitle;
    }
    public View getView(int position,View convertView,ViewGroup parent){
        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if(row==null){
            row = inflater.inflate(R.layout.custom_layout,null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)row.findViewById(R.id.imageView);
            viewHolder.tvTitle = (TextView)row.findViewById(R.id.TVtitle);
            row.setTag(viewHolder);
        }
        VideoList p = getItem(position);
        if(p!=null) {
            ViewHolder holder = (ViewHolder) row.getTag();
            holder.tvTitle.setText(p.title);
            Picasso.with(getContext()).load(p.hinh).into(holder.imageView);
        }
        return row;
    }
}
