package br.usp.ime.servicosusp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

public class Utils {
	public static String convertStreamToString(InputStream is)
			throws IOException {

		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"ISO-8859-1"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	
	public static AlertDialog createNoConnectionDialog(Activity a){
		AlertDialog.Builder adb = new Builder(a);
		adb.setMessage(a.getResources().getString(
				R.string.noConnectionDetails));
		adb.setTitle(a.getResources().getString(R.string.noConnection));
		adb.setCancelable(true);
		AlertDialog ad = adb.create();
		ad.setCanceledOnTouchOutside(true);
		return ad;
	}
	
	public static AlertDialog createDialog(Activity a, String title, String message){
		AlertDialog.Builder adb = new Builder(a);
		adb.setMessage(message);
		adb.setTitle(title);
		adb.setCancelable(false);
		AlertDialog ad = adb.create();
		ad.setCanceledOnTouchOutside(false);
		return ad;
	}
	

}
