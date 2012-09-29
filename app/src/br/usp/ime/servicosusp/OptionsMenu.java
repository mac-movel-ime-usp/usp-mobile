package br.usp.ime.servicosusp;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OptionsMenu {

	private Activity myContext;

	public OptionsMenu(Menu menu, Activity context) {
		myContext = context;
		MenuInflater inflater = myContext.getMenuInflater();
		inflater.inflate(R.menu.main_options, menu);
	}

	public void OptionsHandler(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.main_options_item1:
			Intent i1 = new Intent(myContext, ViewSettings.class);
			myContext.startActivity(i1);
			break;

		case R.id.main_options_item2:
			Intent i = new Intent(myContext, ViewUpdateDatabase.class);
			myContext.startActivity(i);

			break;

		case R.id.main_options_item3:
			Intent i3 = new Intent(myContext, MainActivity.class);
			i3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			myContext.startActivity(i3);
			break;

		case R.id.main_options_item4:
			Intent i4 = new Intent(myContext, ViewAbout.class);
			myContext.startActivity(i4);
			break;

		default:
			break;
		}

	}
}
