package br.usp.ime.servicosusp.model;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.usp.ime.servicosusp.servicosUsp;

public class Local {

	@Override
	public String toString() {
		return nomeloc;
	}

	private int codloc;
	private String sglloc;
	private String nomeloc;
	private String endloc;
	private String cep;
	private String urlPhoto1;
	private String urlPhoto2;
	private String urlPhoto3;
	private Integer posLat;
	private Integer posLong;

	public Local(int codloc, String sglloc, String nomeloc, String endloc
			, String cep, String urlPhoto1, String urlPhoto2,
			String urlPhoto3,Integer posLat,Integer posLong) {
		super();
		this.codloc = codloc;
		this.sglloc = sglloc;
		this.nomeloc = nomeloc;
		this.endloc = endloc;
		this.cep = cep;
		this.urlPhoto1 = urlPhoto1;
		this.urlPhoto2 = urlPhoto2;
		this.urlPhoto3 = urlPhoto3;
		this.posLat = posLat;
		this.posLong = posLong;
	}

	public Local(int codloc) {
		super();
		this.codloc = codloc;
	}

	public int getCodloc() {
		return codloc;
	}

	public void setCodloc(int codloc) {
		this.codloc = codloc;
	}

	public String getAbbrevloc() {
		return sglloc;
	}

	public void setAbbrevloc(String sglloc) {
		this.sglloc = sglloc;
	}

	public String getNameloc() {
		return nomeloc;
	}

	public void setNameloc(String nomeloc) {
		this.nomeloc = nomeloc;
	}

	public String getEndloc() {
		return endloc;
	}

	public void setEndloc(String endloc) {
		this.endloc = endloc;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUrlPhoto1() {
		return urlPhoto1;
	}

	public String getUrlPhoto2() {
		return urlPhoto2;
	}

	public String getUrlPhoto3() {
		return urlPhoto3;
	}

	public void setUrlPhoto1(String urlPhoto1) {
		this.urlPhoto1 = urlPhoto1;
	}

	public void setUrlPhoto2(String urlPhoto2) {
		this.urlPhoto2 = urlPhoto2;
	}

	public void setUrlPhoto3(String urlPhoto3) {
		this.urlPhoto3 = urlPhoto3;
	}

	public static Local getLocalById(int codloc) {
		Local local = null;
		String[] args = { String.valueOf(codloc) };

		Cursor banco = servicosUsp.dbHelper.getReadableDatabase().rawQuery(
				"Select * from local where codloc=?", args);
		if (banco.moveToFirst()) {
			local = new Local(banco.getInt(0), banco.getString(1),
					banco.getString(2), banco.getString(3), banco.getString(4),
					banco.getString(5), banco.getString(6),
					banco.getString(7),banco.getInt(8),banco.getInt(9));
			if (local.getUrlPhoto1() == null) {
				local.setUrlPhoto1("");
			}
			if (local.getUrlPhoto2() == null) {
				local.setUrlPhoto2("");
			}
			if (local.getUrlPhoto3() == null) {
				local.setUrlPhoto3("");
			}

		}
		banco.close();
		return local;
	}

	public static List<Local> getLocals() {

		ArrayList<Local> locals = new ArrayList<Local>();

		Cursor banco = servicosUsp.dbHelper.getReadableDatabase().rawQuery(
				"Select * from local order by nomloc", null);
		if (banco.moveToFirst()) {
			do {
				Local local = new Local(banco.getInt(0), banco.getString(1),
						banco.getString(2), banco.getString(3), banco.getString(4),
						banco.getString(5), banco.getString(6),
						banco.getString(7),banco.getInt(8),banco.getInt(9));
				locals.add(local);
			}while(banco.moveToNext());
		}
		banco.close();
		return locals;
	}

	public Integer getPosLat() {
		return posLat;
	}

	public void setPosLat(Integer posLat) {
		this.posLat = posLat;
	}

	public Integer getPosLong() {
		return posLong;
	}

	public void setPosLong(Integer posLong) {
		this.posLong = posLong;
	}

	
	
}
