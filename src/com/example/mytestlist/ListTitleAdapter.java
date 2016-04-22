package com.example.mytestlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListTitleAdapter extends BaseAdapter {

	Context context;
	String text;
	BaseAdapter parentAdapter;
	private LayoutInflater mInflater;

	public ListTitleAdapter(Context c, String textToShow) {
		this(c, textToShow, null);
	}

	public ListTitleAdapter(Context c, String textToShow,
			BaseAdapter dependentAdapter) {
		super();
		context = c;
		text = textToShow;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (dependentAdapter != null) {
			parentAdapter = dependentAdapter;
		}
	}

	public int getCount() {
		if (parentAdapter != null) {
			if (parentAdapter.getCount() == 0) {
				return 0;
			}
		}
		return 1;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			view = mInflater.inflate(R.layout.title, parent,
					false);
		} else {
			view = convertView;
		}
		
        TextView  label = (TextView) view.findViewById(R.id.title);
        label.setText(text);


		return view;
	}
}