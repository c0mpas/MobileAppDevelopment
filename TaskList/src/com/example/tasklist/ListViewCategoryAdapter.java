package com.example.tasklist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListViewCategoryAdapter extends ArrayAdapter<Category> {
	private final Activity context;
	private final List<Category> categories;
	
	static class ViewHolder {
		public TextView name;
	}

	/**
	 * Constructor
	 */
	public ListViewCategoryAdapter(Activity context, List<Category> categories) {
		super(context, R.layout.listview_element_category, categories);
		this.context = context;
		this.categories = categories;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.listview_element_category, null);
			ViewHolder viewHolder = new ViewHolder();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			
			// Reference Views
			viewHolder.name = (TextView) rowView.findViewById(R.id.listelement_name);
			
			// Set font sizes from preferences
			viewHolder.name.setTextSize(Float.parseFloat(
					prefs.getString(SettingsActivity.FONTSIZE_HEAD, SettingsActivity.DEFAULT_HEAD_SIZE)));
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.name.setText(categories.get(position).getName());
		
		return rowView;
	}
}