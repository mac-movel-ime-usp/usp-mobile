package br.usp.ime.servicosusp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.usp.ime.servicosusp.model.Category;

public class ViewCategories extends Activity {

	ListView listCategories;
	List<Category> categories;
	ArrayAdapter<Category> aa;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_categories);
		listCategories = (ListView) findViewById(R.id.listCategories);
		
		populateServicesList();
		setupSelectionEvent();
	}

	void populateServicesList() {
		categories = Category.getCategories();
		final ArrayList<Category> al = (ArrayList<Category>) categories;
		aa = new ArrayAdapter<Category>(this,
				android.R.layout.simple_list_item_1, al);
		listCategories.setAdapter(aa);

		aa.notifyDataSetChanged();
	}
	
	void setupSelectionEvent() {
		listCategories.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				float width = getResources().getDisplayMetrics().widthPixels;
		    	float height = getResources().getDisplayMetrics().heightPixels;
		    	
				Category c = (Category) arg0.getAdapter().getItem(arg2);
				Intent i;
				if (width >=500 && height>=500){
					 i = new Intent(getApplicationContext(), ViewServicesHight.class);
				}else{
					i = new Intent(getApplicationContext(), ViewServices.class);	
				}
				i.putExtra("CategoryID", c.getCodctg());
				startActivity(i);
				
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
