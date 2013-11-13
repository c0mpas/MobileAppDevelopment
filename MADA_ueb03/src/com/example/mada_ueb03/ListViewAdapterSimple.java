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
	private boolean iconVisibility;
	
	// Colors
	private static final int RED = Color.parseColor("#DF0101");
	private static final int ORANGE = Color.parseColor("#B45F04");
	private static final int GREEN = Color.parseColor("#088A08");
	public static final int BACK = Color.parseColor("#424242");

	static class ViewHolder {
		public TextView title;
		public TextView description;
		public ImageView prioImage;
	}

	/**
	 * Constructor
	 */
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
			
			// Reference Views
			viewHolder.title = (TextView) rowView.findViewById(R.id.listelement_title);
			viewHolder.description = (TextView) rowView.findViewById(R.id.listelement_description);
			viewHolder.prioImage = (ImageView) rowView.findViewById(R.id.listelement_img);
			
			// Set font sizes from preferences
			viewHolder.title.setTextSize(Float.parseFloat(
					prefs.getString(SettingsActivity.FONTSIZE_HEAD, SettingsActivity.DEFAULT_HEAD_SIZE)));
			viewHolder.description.setTextSize(Float.parseFloat(
					prefs.getString(SettingsActivity.FONTSIZE_TAIL, SettingsActivity.DEFAULT_TAIL_SIZE)));
			
			// Set icon visibility
			iconVisibility = prefs.getBoolean(SettingsActivity.ICON, true);
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.title.setText(tasks.get(position).getTitle());
		holder.description.setText(tasks.get(position).getDescription());

		// Select icon for priority
		switch (tasks.get(position).getPriority()) {
			case 1:
				imgID = R.drawable.simple_high;
				color = RED;
				break;
			case 2:
				imgID = R.drawable.simple_med;
				color = ORANGE;
				break;
			case 3:
				imgID = R.drawable.simple_low;
				color = GREEN;
				break;
	
			default:
				imgID = R.drawable.simple_med;
				color = ORANGE;
				break;
		}

		holder.title.setTextColor(color);
		
		if (iconVisibility) {
			holder.prioImage.setVisibility(ImageView.VISIBLE);
			holder.prioImage.setImageResource(imgID);
		}
		
		return rowView;
	}
}