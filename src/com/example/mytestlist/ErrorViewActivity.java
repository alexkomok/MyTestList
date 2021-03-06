package com.example.mytestlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ErrorViewActivity extends Activity {
	private TextView Textv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.error);
		Textv = (TextView) findViewById(R.id.error);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		if (bundle != null) {
			String error = (String) bundle.get("error");
			Textv.setText(error);
		}
	}
}
