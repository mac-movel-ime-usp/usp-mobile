package br.usp.ime.uspmap;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import br.usp.ime.servicosusp.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MarkOnMap extends ItemizedOverlay<OverlayItem> {
	
	private Context myContext;
	private ArrayList<OverlayItem> myOverlays = new ArrayList<OverlayItem>();
	
	public MarkOnMap(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}
	
	public MarkOnMap(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  myContext = context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return myOverlays.get(i);
	}

	@Override
	public int size() {
		return myOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay) {
		myOverlays.add(overlay);
	}

	public void populateNow()
	{
	    populate(); 
	}
	
	@Override
	public void draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow){
		super.draw(canvas, mapView, shadow);

        if (shadow == false)
        {
            //cycle through all overlays
            for (int index = 0; index < myOverlays.size(); index++)
            {
                OverlayItem item = myOverlays.get(index);
                int textSize = 20;
                
                // Converts lat/lng-Point to coordinates on the screen
                GeoPoint point = item.getPoint();
                Point ptScreenCoord = new Point() ;
                mapView.getProjection().toPixels(point, ptScreenCoord);

                //Paint
                Paint paint = new Paint();
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setFakeBoldText(true);
                paint.setTextSize(textSize);
                paint.setARGB(150, 0, 0, 0); // alpha, r, g, b (Black, semi see-through)

                //show text to the right of the icon
                canvas.drawText(item.getTitle(), ptScreenCoord.x, ptScreenCoord.y-33, paint);
            }
        }
	}
	
	// Add nice dialog window when marker is tapped
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = myOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(myContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.setPositiveButton(R.string.goTo, new GoToOnClickListener(index));
	  dialog.setNegativeButton(R.string.rtrn, new RtrnOnClickListener());
	  dialog.show();
	  return true;
	}
	
	private final class GoToOnClickListener implements DialogInterface.OnClickListener {
		int index;
		
		public GoToOnClickListener(int i){
			this.index = i;
		}
				
		public void onClick(DialogInterface dialog, int which) {
			OverlayItem destination = myOverlays.get(index);
			GeoPoint destGP = destination.getPoint();
			double destLat =  destGP.getLatitudeE6() / 1e6;
			double destLong = destGP.getLongitudeE6() / 1e6;
			
			String url = "google.navigation:q=" + destLat + "," + destLong;
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));            
			myContext.startActivity(i);
		}
	}
	
	private final class RtrnOnClickListener implements DialogInterface.OnClickListener {
		
		public void onClick(DialogInterface dialog, int which) {
			
		}
	}
}
