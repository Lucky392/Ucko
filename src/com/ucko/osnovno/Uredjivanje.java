package com.ucko.osnovno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Uredjivanje extends Activity {

	Button urediLekciju;
	Button urediPitanje;
	Button urediKarticu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uredjivanje);

		urediLekciju = (Button) findViewById(R.id.btnUrediLekciju);
		urediPitanje = (Button) findViewById(R.id.btnUrediPitanje);
		urediKarticu = (Button) findViewById(R.id.btnUrediKarticu);
		
		urediLekciju.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent(1);
			}
		});
		
		urediPitanje.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent(2);
			}
		});
		
		urediKarticu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent(3);
			}
		});
	}

	private void intent(int s) {
		Intent i = new Intent(Uredjivanje.this, Izlistavanje.class);
		i.putExtra("koji", s);
		if (s == 1) {
			i.putExtra("radjenje", false);
		}
		startActivity(i);
	}
}
