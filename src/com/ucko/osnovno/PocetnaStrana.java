package com.ucko.osnovno;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class PocetnaStrana extends Activity {

	Button radiLekcije;
	Button upravljanjeSadrzajem;
	Button opcije;
	Button izlaz;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetna_strana);
        
        radiLekcije = (Button) findViewById(R.id.btnRadiLekcije);
        upravljanjeSadrzajem = (Button) findViewById(R.id.btnUpravljanjeSadrzajem);
        opcije = (Button) findViewById(R.id.btnOpcije);
        izlaz = (Button) findViewById(R.id.btnIzlaz);
        
        radiLekcije.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PocetnaStrana.this, Izlistavanje.class);
				i.putExtra("koji", 1);
				i.putExtra("radjenje", true);
				startActivity(i);
			}
		});
        
        upravljanjeSadrzajem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(PocetnaStrana.this)
				.setMessage("Šta želite da uradite sa sadržajem?")
				.setCancelable(true)
				.setPositiveButton("Uredjivanje",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent i = new Intent(PocetnaStrana.this, Uredjivanje.class);
								startActivity(i);
							}
						})
				.setNeutralButton("Napravi novo",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								Intent i = new Intent(PocetnaStrana.this, NapraviNovo.class);
								startActivity(i);
							}
						})
				.show();

			}
		});
        
        opcije.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PocetnaStrana.this, Opcije.class));
			}
		});
        
        izlaz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }

    
}
