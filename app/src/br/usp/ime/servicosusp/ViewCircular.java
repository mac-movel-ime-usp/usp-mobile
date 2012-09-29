package br.usp.ime.servicosusp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import br.usp.ime.uspmap.ShowMap;

public class ViewCircular extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_circular);
	
		Button showMap = (Button) findViewById(R.id.btnShowPaths);
        showMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ShowMap.class);
                myIntent.putExtra("centerOnUser", false);
                myIntent.putExtra("Latitude", -23571481);
                myIntent.putExtra("Longitute", -46708739);
                myIntent.putExtra("displayCircular1Route", true);
                myIntent.putExtra("displayCircular2Route", true);
                startActivityForResult(myIntent, 0);
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
