package br.usp.ime.servicosusp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class ViewBuildingsHight extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_view_buildings);
		
		//FragmentTransaction ft=	getSupportFragmentManager().beginTransaction();
		//ft.replace(R.id.viewServiceDetails_fragment, new FragNoService());
		//ft.commit();
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
