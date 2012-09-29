package br.usp.ime.uspmap;

import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Xml;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class CircularMapOverlay extends Overlay{
	private Projection projection;
	private ArrayList<GeoPoint> points = null;
	private int color;
	private String xmlPath;
	private Context myContext;
	
    public CircularMapOverlay(Context context, Projection proj, String p, int c){
    	projection = proj;
    	xmlPath = p;
    	color = c;
    	myContext = context;
    }   

    public void draw(Canvas canvas, MapView mapv, boolean shadow){
        super.draw(canvas, mapv, shadow);

        Paint   mPaint = new Paint();
       // mPaint.setDither(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        
        points = getPointsFromXML();
        
        if (points != null && points.size() > 1){
        	Point p = new Point();
            Path path = new Path();
        	
            // move to the beginning
            GeoPoint gP = points.get(0);
    		projection.toPixels(gP, p);
    		path.moveTo(p.x, p.y);
        	
    		// draw path trough all the points from the xml file 
    		for(int i=1; i < points.size(); i++){
    			gP = points.get(i);
        		projection.toPixels(gP, p);
        		path.lineTo(p.x,p.y);
        	}
        	canvas.drawPath(path, mPaint);
        }
    }
    
    private ArrayList<GeoPoint> getPointsFromXML(){
    	ArrayList<GeoPoint> pnts = null;
    	GeoPoint currentPoint = null;
	    boolean done = false;
    	XmlPullParser parser = Xml.newPullParser();

    	try {
		  //  FileInputStream fIn = new FileInputStream("assets\\" + xmlPath);
		    InputStreamReader isr = new InputStreamReader(myContext.getAssets().open(xmlPath));
		    
		    parser.setInput(isr);
		    int eventType = parser.getEventType();
		    
		    while (eventType != XmlPullParser.END_DOCUMENT && !done){
		        String name = null;
		        switch (eventType){
		            case XmlPullParser.START_DOCUMENT:
		                break;
		            case XmlPullParser.START_TAG:
		                name = parser.getName();
		                if (name.equalsIgnoreCase("points")){
		                	pnts = new ArrayList<GeoPoint>();
		                } else if (pnts != null){
		                    if (name.equalsIgnoreCase("point")){
		                    	currentPoint = new GeoPoint(Integer.parseInt(parser.getAttributeValue(0)),
		                    								Integer.parseInt(parser.getAttributeValue(1)));
		                    }
		                }
		                break;
		            case XmlPullParser.END_TAG:
		                name = parser.getName();
		                if (name.equalsIgnoreCase("point") && currentPoint != null){
		                    pnts.add(currentPoint);
		                } else if (name.equalsIgnoreCase("points")){
		                    done = true;
		                }
		                break;
	            }
		        eventType = parser.next();
	        }
    	} catch (Exception e){}
		    
    	return pnts;
    }
}
