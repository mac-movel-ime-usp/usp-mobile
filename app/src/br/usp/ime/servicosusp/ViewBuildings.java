package br.usp.ime.servicosusp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import br.usp.ime.servicosusp.model.Local;

public class ViewBuildings extends Activity {

	ListView listBuildings;
	EditText editFilter;
	List<Local> locals;
	ArrayAdapter<Local> aa;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_buildings);
		listBuildings = (ListView) findViewById(R.id.listBuildings);
		editFilter = (EditText) findViewById(R.id.editFilter);
		setupSearchEvent();
		populateServicesList();
		setupSelectionEvent();
		editFilter.clearFocus();
	}

	void populateServicesList() {

		locals = Local.getLocals();
		final ArrayList<Local> al = (ArrayList<Local>) locals;
		aa = new ArrayAdapter<Local>(this,
				android.R.layout.simple_list_item_1, al);
		listBuildings.setAdapter(aa);

		listBuildings.setTextFilterEnabled(true);

		aa.notifyDataSetChanged();

	}

	void setupSearchEvent() {
		editFilter.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				if (s.toString().equals("")) {
					listBuildings.clearTextFilter();
				} else {
					listBuildings.setFilterText(s.toString());
				}
			}
		});
	}  		

	void setupSelectionEvent() {
		listBuildings.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Local l = (Local) arg0.getAdapter().getItem(arg2);
			
				// Ghost building must have CODLOC = 0 !!!
				if(l.getCodloc() == 0){
					Intent i = new Intent(getApplicationContext(), ViewBuildingGhost.class);
					i.putExtra("BuildingID", l.getCodloc());
					startActivity(i);
				}
				else{
					Intent i = new Intent(getApplicationContext(), ViewBuildingDetails.class);
					i.putExtra("BuildingID", l.getCodloc());
					startActivity(i);
				}
				Log.i("ServicosUSP", String.valueOf(l.getCodloc()));
			}

		});
	}
	
	private OptionsMenu optionsMenu;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		optionsMenu = new OptionsMenu(menu, this);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		optionsMenu.OptionsHandler(item);
		return true;
	}
}
