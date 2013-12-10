package com.example.tasklist;

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

public class ListViewAdapter extends ArrayAdapter<ToDoTask> {
	private final Activity context;
	private final ArrayList<ToDoTask> tasks;
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
	public ListViewAdapter(Activity context,  ArrayList<ToDoTask> tasks) {
		super(context, R.layout.listview_element_task, tasks);
		this.context = context;
		this.tasks = tasks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.listview_element_task, null);
			ViewHolder viewHolder = new ViewHolder();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			
			// Reference Views
			viewHolder.title = (TextView) rowView.findViewById(R.id.listelement_title);
			viewHolder.description = (TextView) rowView.findViewById(R.id.listelement_description);
			
			// Set font sizes from preferences
			viewHolder.title.setTextSize(Float.parseFloat(
					prefs.getString(SettingsActivity.FONTSIZE_HEAD, SettingsActivity.DEFAULT_HEAD_SIZE)));
			viewHolder.description.setTextSize(Float.parseFloat(
					prefs.getString(SettingsActivity.FONTSIZE_TAIL, SettingsActivity.DEFAULT_TAIL_SIZE)));
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.title.setText(tasks.get(position).getTitle());
		holder.description.setText(tasks.get(position).getDescription());

		holder.title.setTextColor(ORANGE);
		
		return rowView;
	}
}