package com.ucko.osnovno;

import java.util.ArrayList;
import java.util.List;

import com.ucko.adapteri.MojAdapter;
import com.ucko.baza.DatabaseHandler;
import com.ucko.okviri.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Izlistavanje extends Activity {

	private ListView lista;

	private ArrayList<String> listaImena;

	private List<Lekcija> pitanja = new ArrayList<Lekcija>();
	private List<SkupLekcija> lekcije = new ArrayList<SkupLekcija>();
	private List<Kartica> kartice = new ArrayList<Kartica>();

	DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_izlistavanje);

		lista = (ListView) findViewById(R.id.lista);
		listaImena = new ArrayList<String>();
		
		int a = getIntent().getIntExtra("koji", 0);

		switch (a) {
		case 1:
			db = new DatabaseHandler(this, 1);
			lekcije = db.vratiSveSkupoveLekcija();
			punjenjeListViewa();
			break;
		case 2:
			db = new DatabaseHandler(this, 2);
			pitanja = db.vratiSveLekcije();
			punjenjeListViewa();
			break;
		case 3:
			db = new DatabaseHandler(this, 3);
			kartice = db.vratiSveKartice();
			punjenjeListViewa();
			break;
		default:
			break;
		}
	}

	private void napuniListuImena() {
		switch (db.getTabelaKojaSeOtvara()) {
		case 1:
			for (int i = 0; i < lekcije.size(); i++) {
				listaImena.add(lekcije.get(i).vratiNaslov());
			}
			break;
		case 2:
			for (int i = 0; i < pitanja.size(); i++) {
				listaImena.add(pitanja.get(i).vratiNaslov());
			}
			break;
		case 3:
			for (int i = 0; i < kartice.size(); i++) {
				listaImena.add(kartice.get(i).vratiNaslov());
			}
			break;
		}
	}

	private void punjenjeListViewa() {
		if (lekcije.size() != 0 || pitanja.size() != 0 || kartice.size() != 0) {

			napuniListuImena();

			MojAdapter adapter = new MojAdapter(this,
					android.R.layout.simple_list_item_1, listaImena);

			lista.setAdapter(adapter);

			lista.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int pozicija, long arg3) {
					if (getIntent().getBooleanExtra("radjenje", true)) {
						//da se radi lekcija
						//treba da se proslede parametri lekcije
						int sl = lekcije.get(pozicija).getId();
						Intent i = new Intent(Izlistavanje.this, RadiLekciju.class);
						i.putExtra("lekcija", sl);
						startActivity(i);
					} else {
						popUp1();
					}
				}
			});
		} else {
			Toast.makeText(this, "Baza je prazna", Toast.LENGTH_SHORT).show();
		}
	}

	private void popUp1() {
		new AlertDialog.Builder(Izlistavanje.this)
				.setMessage("Šta želite da uradite?")
				.setCancelable(true)
				.setPositiveButton("Promeni",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								switch (db.getTabelaKojaSeOtvara()) {
								case 1:
									// IDE NA AKTIVNOST UPRAVLJANJE LEKCIJAMA
									intent(UpravljanjeLekcijama.class);
									break;
								case 2:
									// IDE NA AKTIVNOST UPRAVLJANJE PITANJIMA
									intent(UpravljanjePitanjima.class);
									break;
								case 3:
									// IDE NA AKTIVNOST UPRAVLJANJE KARTICAMA
									intent(UpravljanjeKarticama.class);
									break;
								}
							}
						})
				.setNeutralButton("Obriši",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// POP-UP DA LI STE SIGURNI DA ZELITE DA
								// OBRISETE LEKCIJU SA DA-NE
								popUp2();
							}
						}).show();
	}
	
	private void popUp2(){
		new AlertDialog.Builder(Izlistavanje.this)
		.setMessage("Da li ste sigurni?")
		.setCancelable(true)
		.setPositiveButton("DA",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						//BRISANJE
					}
				})
		.setNegativeButton("NE",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						
					}
				}).show();
	}

	private void intent(final Class<?> cls) {
		Intent i = new Intent(Izlistavanje.this, cls);
		i.putExtra("novo", false);
		startActivity(i);
	}
}
