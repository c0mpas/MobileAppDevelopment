package com.example.mada_ueb03;

import com.example.mada_ueb03.R.id;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PrioSpinnerAdapter extends ArrayAdapter<Integer> {

	private Activity context;
	private Integer[] prios;

	public PrioSpinnerAdapter(Activity context, int textViewResourceId,
			Integer[] prios) {
		super(context, textViewResourceId, prios);
		this.context = context;
		this.prios = prios;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View row = inflater.inflate(R.layout.prio_spinner_std, parent, false);
		TextView prio = (TextView) row.findViewById(R.id.textSpinnerPrio);
		ImageView img = (ImageView) row.findViewById(R.id.imgSpinnerPrio);
		
		if(prios[position].equals(1)){			
			img.setImageResource(R.drawable.prio_high);	
			prio.setText(R.string.prio_high);
		}
		if(prios[position].equals(2)){			
			img.setImageResource(R.drawable.prio_mid);	
			prio.setText(R.string.prio_mid);
		}
		if(prios[position].equals(3)){			
			img.setImageResource(R.drawable.prio_low);	
			prio.setText(R.string.prio_low);
		}


		return row;
	}
}
