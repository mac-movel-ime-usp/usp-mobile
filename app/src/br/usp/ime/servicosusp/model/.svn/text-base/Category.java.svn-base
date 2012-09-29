package br.usp.ime.servicosusp.model;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.usp.ime.servicosusp.servicosUsp;

public class Category {
	
	private int codctg;
	private String nomectg;
	
	public Category(int codctg, String nomectg) {
		super();
		this.codctg = codctg;
		this.nomectg = nomectg;
	}
	
	@Override
	public String toString() {
		return nomectg;
	}
	
	public int getCodctg() {
		return codctg;
	}
	public void setCodctg(int codctg) {
		this.codctg = codctg;
	}
	public String getNomectg() {
		return nomectg;
	}
	public void setNomectg(String nomectg) {
		this.nomectg = nomectg;
	}
	
	public static List<Category> getCategories() {

		ArrayList<Category> Categories = new ArrayList<Category>();

		Cursor banco = servicosUsp.dbHelper.getReadableDatabase().rawQuery(
				"Select * from categoria order by nomctg", null);
		if (banco.moveToFirst()) {
			do {
				Category category = new Category(banco.getInt(0), banco.getString(1));
				Categories.add(category);
			}while(banco.moveToNext());
		}

		return Categories;
	}
}
