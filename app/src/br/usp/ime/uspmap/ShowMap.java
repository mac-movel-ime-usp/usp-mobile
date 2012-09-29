package br.usp.ime.uspmap;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import br.usp.ime.servicosusp.MainActivity;
import br.usp.ime.servicosusp.R;
import br.usp.ime.servicosusp.model.Local;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class ShowMap extends MapActivity {

	private MapController mapController;
	private MapView mapView;
	private MyLocationOverlay myLocationOverlay;
	private List<Overlay> mapOverlays;
	private boolean showCircular1;
	private boolean showCircular2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.showmap);
	    
	    // Configure the map
	    mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    mapController = mapView.getController();
		mapController.setZoom(17); // Zoom 1 is world view
    
		showCircular1 = getIntent().getExtras().getBoolean("displayCircular1Route");
		showCircular2 = getIntent().getExtras().getBoolean("displayCircular2Route");
		
		mapOverlays = mapView.getOverlays();
		displayLocationMarkers();
		displayCircularRoutes();
		displayUserMarker();
		
		if(getIntent().getExtras().getBoolean("centerOnUser")){
			myLocationOverlay.runOnFirstFix(new Runnable() { public void run() {
				mapController.animateTo(myLocationOverlay.getMyLocation());
			}});
		}
		else {
			GeoPoint centerPoint = new GeoPoint(getIntent().getExtras().getInt("Latitude"), getIntent().getExtras().getInt("Longitute"));
			mapController.setZoom(19);
			mapController.animateTo(centerPoint);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private void displayLocationMarkers(){
		// Creating new overlay of locations
		   Drawable locationIcon = this.getResources().getDrawable(R.drawable.location);
		   MarkOnMap locations = new MarkOnMap(locationIcon, this);
		   
		   int lat = 0;
		   int lon = 0;
		   OverlayItem local;
		   
		   // adding points to the custom overlay
		   List<Local> locals = Local.getLocals();
		   for(int i = 0; i < locals.size(); i++){
		    // skipping the ghost building
		    if(locals.get(i).getCodloc() == 0){
		    continue;
		    }
		   
		    lat = locals.get(i).getPosLat();
		    lon = locals.get(i).getPosLong();
		   
		    local = new OverlayItem(new GeoPoint(lat, lon), locals.get(i).getAbbrevloc(), locals.get(i).getNameloc());
		   locations.addOverlay(local);
		   }
		        
		   // Adding the new overlay of locations
		   locations.populateNow();
		   mapOverlays.add(locations);
		}
	
	private void displayCircularRoutes(){
		if(showCircular1){	
			mapOverlays.add(new CircularMapOverlay(this, mapView.getProjection(), "circular801210.xml", Color.RED));
		}
		if(showCircular2){ 
			mapOverlays.add(new CircularMapOverlay(this, mapView.getProjection(), "circular802210.xml", Color.BLUE));
		}
		mapView.postInvalidate();
	}
	
	private void displayUserMarker(){
		// Marking current user position on the map 
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapOverlays.add(myLocationOverlay);
		mapView.postInvalidate();
			
		myLocationOverlay.enableMyLocation();
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_options, menu);
		return true;
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item1 = menu.findItem(R.id.map_options_item1);
        MenuItem item2 = menu.findItem(R.id.map_options_item2);
        
        if (showCircular1) {
        	item1.setTitle(R.string.mapHideCircular1);
        }
        else{
        	item1.setTitle(R.string.mapShowCircular1);
        }
        if (showCircular2) {
        	item2.setTitle(R.string.mapHideCircular2);
        }
        else{
        	item2.setTitle(R.string.mapShowCircular2);
        }
        
        return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.map_options_item1:
				if(showCircular1){
					mapOverlays.clear();
					showCircular1 = false;
					mapOverlays.add(myLocationOverlay);
					displayLocationMarkers();
					displayCircularRoutes();
				}
				else{
					showCircular1 = true;
					displayCircularRoutes();
				}
				break;				
			case R.id.map_options_item2:
				if(showCircular2){
					mapOverlays.clear();
					showCircular2 = false;
					mapOverlays.add(myLocationOverlay);
					displayLocationMarkers();
					displayCircularRoutes();
				}
				else{
					showCircular2 = true;
					displayCircularRoutes();
				}
				break;
			case R.id.map_options_item3:
				Intent i3 = new Intent(this, MainActivity.class);
				i3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				this.startActivity(i3);
				break;

			default:
				break;

		}
		return true;
	}
}
