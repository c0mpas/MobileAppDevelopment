package com.example.mada_ueb03;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapterKitty extends ArrayAdapter<ToDoTask> {
	private final Activity context;
	private final ArrayList<ToDoTask> tasks;
	private int imgID;

	static class ViewHolder {
		public TextView title;
		public TextView description;
		public ImageView prioImage;


	}

	public ListViewAdapterKitty(Activity context,  ArrayList<ToDoTask> tasks) {
		super(context, R.layout.listview_element_kitty, tasks);
		
		this.context = context;
		this.tasks = tasks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.listview_element_kitty, null);
			
			ViewHolder viewHolder = new ViewHolder();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			viewHolder.title = (TextView) rowView
					.findViewById(R.id.listelement_title);
			viewHolder.description = (TextView) rowView
					.findViewById(R.id.listelement_description);
			viewHolder.prioImage = (ImageView) rowView
					.findViewById(R.id.listelement_img);
			viewHolder.title.setTextSize(Float.parseFloat(prefs.getString(SettingsActivity.FONTSIZE_HEAD, SettingsActivity.DEFAULT_HEAD_SIZE)));
			viewHolder.description.setTextSize(Float.parseFloat(prefs.getString(SettingsActivity.FONTSIZE_TAIL, SettingsActivity.DEFAULT_TAIL_SIZE)));

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.title.setText(tasks.get(position).getTitle());
		holder.description.setText(tasks.get(position).getDescription());

		switch (tasks.get(position).getPriority()) {
		case 1:
			imgID = R.drawable.kitty_high;
			break;
		case 2:
			imgID = R.drawable.kitty_mid;
			break;
		case 3:
			imgID = R.drawable.kitty_low;
			break;

		default:
			imgID = R.drawable.prio_low;
			break;
		}
		holder.prioImage.setImageResource(imgID);

		return rowView;
	}
}