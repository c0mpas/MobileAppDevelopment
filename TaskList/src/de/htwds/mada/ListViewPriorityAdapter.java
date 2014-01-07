package de.htwds.mada;

import java.util.List;

import com.example.tasklist.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListViewPriorityAdapter extends ArrayAdapter<Priority> {
	private final Activity context;
	private final List<Priority> priorities;
	
	static class ViewHolder {
		public TextView name;
		public TextView value;
	}

	/**
	 * Constructor
	 */
	public ListViewPriorityAdapter(Activity context, List<Priority> priorities) {
		super(context, R.layout.listview_element_priority, priorities);
		this.context = context;
		this.priorities = priorities;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.listview_element_priority, null);
			ViewHolder viewHolder = new ViewHolder();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			
			// Reference Views
			viewHolder.name = (TextView) rowView.findViewById(R.id.listelement_name);
			viewHolder.value = (TextView) rowView.findViewById(R.id.listelement_value);
			
			// Set font sizes from preferences
			viewHolder.name.setTextSize(Float.parseFloat(
					prefs.getString(SettingsActivity.FONTSIZE_HEAD, SettingsActivity.DEFAULT_HEAD_SIZE)));
			viewHolder.value.setTextSize(Float.parseFloat(
					prefs.getString(SettingsActivity.FONTSIZE_TAIL, SettingsActivity.DEFAULT_TAIL_SIZE)));
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.name.setText(priorities.get(position).getName());
		holder.value.setText(String.valueOf(priorities.get(position).getValue()));

		return rowView;
	}
}