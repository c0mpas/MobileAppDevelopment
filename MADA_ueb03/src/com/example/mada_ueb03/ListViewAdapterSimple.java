package com.example.mada_ueb03;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapterSimple extends ArrayAdapter<ToDoTask> {
	private final Activity context;
	private final ArrayList<ToDoTask> tasks;
	private int imgID;
	private int color;
	
	private static final int RED = Color.parseColor("#FF0000");
	private static final int ORANGE = Color.parseColor("#FF8000");
	private static final int GREEN = Color.parseColor("#00FF00");

	static class ViewHolder {
		public TextView title;
		public TextView description;
		public ImageView prioImage;
	}

	public ListViewAdapterSimple(Activity context,  ArrayList<ToDoTask> tasks) {
		super(context, R.layout.listview_element_simple, tasks);
		
		this.context = context;
		this.tasks = tasks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.listview_element_simple, null);
			
			ViewHolder viewHolder = new ViewHolder();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			viewHolder.title = (TextView) rowView.findViewById(R.id.listelement_title);
			viewHolder.description = (TextView) rowView.findViewById(R.id.listelement_description);
			viewHolder.prioImage = (ImageView) rowView.findViewById(R.id.listelement_img);
			
			viewHolder.title.setTextSize(Float.parseFloat(
					prefs.getString(SettingsActivity.FONTSIZE_HEAD, SettingsActivity.DEFAULT_HEAD_SIZE)));
			viewHolder.description.setTextSize(Float.parseFloat(
					prefs.getString(SettingsActivity.FONTSIZE_TAIL, SettingsActivity.DEFAULT_TAIL_SIZE)));

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.title.setText(tasks.get(position).getTitle());
		holder.description.setText(tasks.get(position).getDescription());

		switch (tasks.get(position).getPriority()) {
			case 1:
				imgID = R.drawable.prio_high;
				color = RED;
				break;
			case 2:
				imgID = R.drawable.prio_mid;
				color = ORANGE;
				break;
			case 3:
				imgID = R.drawable.prio_low;
				color = GREEN;
				break;
	
			default:
				imgID = R.drawable.prio_mid;
				color = ORANGE;
				break;
		}
		
		holder.prioImage.setImageResource(imgID);
		holder.title.setTextColor(color);
		
		return rowView;
	}
}