package com.example.tasklist;

import java.util.List;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewTaskAdapter extends ArrayAdapter<Task> {
	private final Activity context;
	private final List<Task> tasks;
	
	static class ViewHolder {
		public TextView title;
		public TextView description;
		public TextView date;
		public TextView priority;
		public TextView categories;
	}

	/**
	 * Constructor
	 */
	public ListViewTaskAdapter(Activity context, List<Task> tasks) {
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
			viewHolder.date = (TextView) rowView.findViewById(R.id.listelement_date);
			viewHolder.priority = (TextView) rowView.findViewById(R.id.listelement_priority);
			viewHolder.categories = (TextView) rowView.findViewById(R.id.listelement_categories);
			
			// Set font sizes from preferences
			float textSizeHead = Float.parseFloat(prefs.getString(SettingsActivity.FONTSIZE_HEAD, SettingsActivity.DEFAULT_HEAD_SIZE));
			viewHolder.title.setTextSize(textSizeHead);
			float textSizeTail = Float.parseFloat(prefs.getString(SettingsActivity.FONTSIZE_TAIL, SettingsActivity.DEFAULT_TAIL_SIZE));
			viewHolder.description.setTextSize(textSizeTail);
			viewHolder.date.setTextSize(textSizeTail);
			viewHolder.priority.setTextSize(textSizeTail);
			viewHolder.categories.setTextSize(textSizeTail);
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.title.setText(tasks.get(position).getTitle());
		holder.description.setText(tasks.get(position).getDescription());
		int month = tasks.get(position).getAblaufMonat() + 1;
		holder.date.setText(tasks.get(position).getAblaufTag()+context.getString(R.string.dot)+month+context.getString(R.string.dot)+tasks.get(position).getAblaufJahr());
		holder.priority.setText(tasks.get(position).getPriority().getName());
		holder.categories.setText(tasks.get(position).getCategory().getName());
		
		if (tasks.get(position).getPriority().getName() == null) holder.priority.setText("priority");
		if (tasks.get(position).getCategory().getName() == null) holder.categories.setText("categories");
		
		return rowView;
	}
}