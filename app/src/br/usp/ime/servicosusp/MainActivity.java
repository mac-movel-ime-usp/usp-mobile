package br.usp.ime.servicosusp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.uspmap.ShowMap;

public class MainActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_dashboard);
	 
	    //attach event handler to dash-board buttons
	    DashboardClickListener dBClickListener = new DashboardClickListener();
	    findViewById(R.id.dashboard_buildings).setOnClickListener(dBClickListener);
	    findViewById(R.id.dashboard_categories).setOnClickListener(dBClickListener);
	    findViewById(R.id.dashboard_map).setOnClickListener(dBClickListener);
	    findViewById(R.id.dashboard_services).setOnClickListener(dBClickListener);
    	
//    	float width = getResources().getDisplayMetrics().widthPixels;
 //   	Toast.makeText(getApplicationContext(), String.valueOf(width), Toast.LENGTH_LONG).show();
	    
	}

	private class DashboardClickListener implements OnClickListener {
	    public void onClick(View v) {
	    	float width = getResources().getDisplayMetrics().widthPixels;
	    	float height = getResources().getDisplayMetrics().heightPixels;

	    	
	        Intent i = null;
	        switch (v.getId()) {
	            case R.id.dashboard_services:
	            	if (width >=500 && height>=500 ){
	            		i= new Intent(MainActivity.this,ViewServicesHight.class);
	            	}else{
	            		i= new Intent(MainActivity.this,ViewServices.class);
	            	}
	            	break;
	            case R.id.dashboard_buildings:
	            	if (width >=500 && height>=500){
	            		i = new Intent(MainActivity.this,ViewBuildingsHight.class);
	            	}else{
	            		i = new Intent(MainActivity.this,ViewBuildings.class);
	            	}
	            	break;
	            case R.id.dashboard_categories:
	                
	            	i = new Intent(MainActivity.this,ViewCategories.class);
	                
	                break;
	            case R.id.dashboard_map:
	                i  = new Intent(MainActivity.this, ShowMap.class);
					i.putExtra("centerOnUser", true);
					i.putExtra("displayCircular1Route", false);
					i.putExtra("displayCircular2Route", false);
	                break;
	            default:
	                break;
	        }
	        if(i != null) {
	            startActivity(i);
	        }
	    }
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
};