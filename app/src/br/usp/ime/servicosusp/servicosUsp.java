package br.usp.ime.servicosusp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import br.usp.ime.servicosusp.database.DataBaseHelper;

public class servicosUsp extends Application {

	public static DataBaseHelper dbHelper;
	public static servicosUsp instance;
	public HashMap<String, Drawable> loadedImagesBuffer;

	public static servicosUsp getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		servicosUsp.instance = this;
		createDbHelper();
		//createDbHelperTestEnviroment();
		loadedImagesBuffer = new HashMap<String, Drawable>();
	}

	@SuppressWarnings("unused")
	private void createDbHelperTestEnviroment() {
		//this.getBaseContext().deleteDatabase("EP2012");
		dbHelper = new DataBaseHelper(this.getBaseContext());
		//Tests.insertData();
	}

	private void createDbHelper() {
		dbHelper = null;
		// Configure Database;
		this.getBaseContext().deleteDatabase("EP2012");
		dbHelper = new DataBaseHelper(this.getBaseContext());

		try {
			dbHelper.createDataBase();
			dbHelper.openDataBase();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	boolean hasInternetConnection(Activity a) {
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
			return true;
		} else {
					

			return false;
		}

	}

}
