package com.ucko.baza;

import java.util.ArrayList;
import java.util.List;

import com.ucko.okviri.SkupLekcija;

import com.ucko.okviri.Lekcija;

import com.ucko.okviri.Kartica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "Ucko";

	private static final String KARTICE = "kartice";
	private static final String PITANJA = "pitanja";
	private static final String LEKCIJE = "lekcije";

	private static final String KEY_ID = "id";
	private static final String NASLOV = "naslov";
	private static final String SLIKA = "slika";
	private static final String ZVUK = "zvuk";
	private static final String PITANJE = "pitanje";
	private static final String TACAN_ODGOVOR = "tacan_odgovor";
	private static final String NETACNI_ODGOVORI = "netacni_odgovori";
	private static final String LEKCIJA = "lekcija";

	private static final String NAZIV_LEKCIJE = "naziv_lekcije";

	private int tabelaKojaSeOtvara;

	public int getTabelaKojaSeOtvara() {
		return tabelaKojaSeOtvara;
	}

	public DatabaseHandler(Context context, int tabelaKojaSeOtvara) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.tabelaKojaSeOtvara = tabelaKojaSeOtvara;
	}

	private void napraviTabeluKartica(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + KARTICE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NASLOV
				+ " TEXT NOT NULL," + SLIKA + " TEXT NOT NULL," + ZVUK
				+ " TEXT NOT NULL)";
		db.execSQL(CREATE_TABLE);
	}

	private void napraviTabeluPitanja(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + PITANJA + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + PITANJE
				+ " TEXT NOT NULL," + ZVUK + " TEXT NOT NULL," + TACAN_ODGOVOR
				+ " INTEGER NOT NULL," + NETACNI_ODGOVORI + " TEXT NOT NULL)";
		db.execSQL(CREATE_TABLE);
	}

	private void napraviTabeluLekcija(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + LEKCIJE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NAZIV_LEKCIJE
				+ " TEXT NOT NULL," + LEKCIJA + " TEXT NOT NULL)";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		napraviTabeluLekcija(db);
		napraviTabeluPitanja(db);
		napraviTabeluKartica(db);
	}

	private void azurirajTabeluKartica(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + KARTICE);
	}

	private void azurirajTabeluPitanja(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + PITANJA);
	}

	private void azurirajTabeluLekcija(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + LEKCIJE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		azurirajTabeluLekcija(db);
		azurirajTabeluPitanja(db);
		azurirajTabeluKartica(db);
		onCreate(db);
	}

	public void dodajKarticu(Kartica k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NASLOV, k.getNaslov());
		values.put(SLIKA, k.getSlika());
		values.put(ZVUK, k.getZvuk());

		db.insert(KARTICE, null, values);
		db.close();
	}

	public Kartica vratiKarticu(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(KARTICE, new String[] { KEY_ID, NASLOV, SLIKA,
				ZVUK }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Kartica k = new Kartica(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3));
		return k;
	}

	public List<Kartica> vratiSveKartice() {
		List<Kartica> listaKartica = new ArrayList<Kartica>();

		String selectQuery = "SELECT  * FROM " + KARTICE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Kartica k = new Kartica(cursor.getInt(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3));
				listaKartica.add(k);
			} while (cursor.moveToNext());
		}
		return listaKartica;
	}

	public int azurirajKarticu(Kartica k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NASLOV, k.getNaslov());
		values.put(SLIKA, k.getSlika());
		values.put(ZVUK, k.getZvuk());

		return db.update(KARTICE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
	}

	public void obrisiKarticu(Kartica k) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(KARTICE, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
		db.close();
	}

	public void dodajLekciju(Lekcija k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PITANJE, k.getPitanje());
		values.put(ZVUK, k.getZvuk());
		values.put(TACAN_ODGOVOR, k.getTacanOdgovor());
		values.put(NETACNI_ODGOVORI, k.toString());

		db.insert(PITANJA, null, values);
		db.close();
	}

	public Lekcija vratiLekciju(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(PITANJA, new String[] { KEY_ID, PITANJE, ZVUK,
				TACAN_ODGOVOR, NETACNI_ODGOVORI }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Lekcija k = new Lekcija(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
		return k;
	}

	public List<Lekcija> vratiSveLekcije() {
		List<Lekcija> listaLekcija = new ArrayList<Lekcija>();

		String selectQuery = "SELECT  * FROM " + PITANJA;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Lekcija k = new Lekcija(cursor.getInt(0), cursor.getString(1),
						cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
				listaLekcija.add(k);
			} while (cursor.moveToNext());
		}
		return listaLekcija;
	}

	public int azurirajLekciju(Lekcija k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PITANJE, k.getPitanje());
		values.put(ZVUK, k.getZvuk());
		values.put(TACAN_ODGOVOR, k.getTacanOdgovor());
		values.put(NETACNI_ODGOVORI, k.toString());
		return db.update(PITANJA, values, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
	}

	public void obrisiLekciju(Lekcija k) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(PITANJA, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
		db.close();
	}

	public void dodajSkupLekcija(SkupLekcija sk) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NAZIV_LEKCIJE, sk.getNaziv());
		values.put(LEKCIJA, sk.toString());

		db.insert(LEKCIJE, null, values);
		db.close();
	}

	public SkupLekcija vratiSkupLekcija(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(LEKCIJE, new String[] { KEY_ID, NAZIV_LEKCIJE,
				LEKCIJA }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		SkupLekcija sk = new SkupLekcija(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2));
		return sk;
	}

	public List<SkupLekcija> vratiSveSkupoveLekcija() {
		List<SkupLekcija> skupLekcija = new ArrayList<SkupLekcija>();

		String selectQuery = "SELECT  * FROM " + LEKCIJE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				SkupLekcija k = new SkupLekcija(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2));
				skupLekcija.add(k);
			} while (cursor.moveToNext());
		}
		return skupLekcija;
	}

	public int azurirajSkupLekcija(SkupLekcija k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NAZIV_LEKCIJE, k.getNaziv());
		values.put(LEKCIJA, k.toString());
		return db.update(LEKCIJE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
	}

	public void obrisiSkupLekcija(SkupLekcija k) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(LEKCIJE, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
		db.close();
	}

}
