package com.ucko.osnovno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NapraviNovo extends Activity {

	private Button napraviLekciju;
	private Button napraviPitanje;
	private Button napraviKarticu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_napravi_novo);

		napraviLekciju = (Button) findViewById(R.id.btnNapraviLekciju);
		napraviPitanje = (Button) findViewById(R.id.btnNapraviPitanje);
		napraviKarticu = (Button) findViewById(R.id.btnNapraviKarticu);

		napraviLekciju.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent(UpravljanjeLekcijama.class);
			}
		});

		napraviPitanje.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent(UpravljanjePitanjima.class);
			}
		});

		napraviKarticu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent(UpravljanjeKarticama.class);
			}
		});
	}

	private void intent(final Class<?> cls) {
		Intent i = new Intent(NapraviNovo.this, cls);
		i.putExtra("novo", true);
		startActivity(i);
	}

}
