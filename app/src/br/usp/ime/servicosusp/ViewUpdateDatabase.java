package br.usp.ime.servicosusp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewUpdateDatabase extends Activity {

	ProgressBar pb;
	Button btUpateButton;
	TextView tvDownloadStatus;

	Handler h;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_updatedatabase);

		pb = (ProgressBar) findViewById(R.id.pbDownloadStatus);
		btUpateButton = (Button) findViewById(R.id.btDownloadStatus);
		tvDownloadStatus = (TextView) findViewById(R.id.tvDownloadStatus);

		h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				tvDownloadStatus.setText((String) msg.obj);
				pb.setProgress(msg.arg1);

				if (msg.arg1 == 100) {
					final Intent i = new Intent(ViewUpdateDatabase.this,
							MainActivity.class);
					final AlertDialog ad = Utils.createDialog(
							ViewUpdateDatabase.this,
							getResources().getString(R.string.updateSucess),
							getResources().getString(R.string.updateSucessDetails));
					ad.setCancelable(true);
					ad.setCanceledOnTouchOutside(true);
					ad.setButton("Ok", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							try {
								servicosUsp.dbHelper.openDataBase();
							} catch (SQLException e) {
								e.printStackTrace();

							}
							ad.dismiss();
							btUpateButton.setEnabled(true);
							startActivity(i);

						}
					});
					ad.setOnCancelListener(new OnCancelListener() {
						public void onCancel(DialogInterface arg0) {
							try {
								servicosUsp.dbHelper.openDataBase();
							} catch (SQLException e) {
								e.printStackTrace();

							}
							ad.dismiss();
							btUpateButton.setEnabled(true);
							startActivity(i);
						}
					});
					ad.show();
				} else if (msg.arg1 == -1) {
					final AlertDialog ad = Utils.createDialog(
							ViewUpdateDatabase.this,
							getResources().getString(R.string.updateFail),
							getResources().getString(R.string.updateFailDetails));
					ad.setCancelable(true);
					ad.setCanceledOnTouchOutside(true);
					ad.setButton("Ok", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							try {
								servicosUsp.dbHelper.openDataBase();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							ad.dismiss();
							btUpateButton.setEnabled(true);
						}
					});
					ad.setOnCancelListener(new OnCancelListener() {
						public void onCancel(DialogInterface arg0) {
							try {
								servicosUsp.dbHelper.openDataBase();
							} catch (SQLException e) {
								e.printStackTrace();

							}
							ad.dismiss();
							btUpateButton.setEnabled(true);
						}
					});
					ad.show();
				}
				
			}
		};

		setupDownloadButton();

	}

	void setupDownloadButton() {
		btUpateButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				btUpateButton.setEnabled(false);
				startDownload();

			}
		});
	}

	void startDownload() {

		new Thread(new Runnable() {

			public void run() {
				servicosUsp.dbHelper.close();

				DownloadFromUrl("http://www.ime.usp.br/~gwachs/EP2012",
						"/data/data/br.usp.ime.servicosusp/databases/EP2012");

			}
		}).start();

	}

	public void DownloadFromUrl(String URL, String fileName) { // this is the
																// downloader
																// method
		try {

			URL req = new URL(URL);
			HttpURLConnection conection = (HttpURLConnection) req
					.openConnection();
			int size = conection.getContentLength();
			int downloaded = 0;
			conection.connect();

			InputStream is = conection.getInputStream();

			// ByteArrayOutputStream os = new ByteArrayOutputStream(size);

			OutputStream os = new FileOutputStream(fileName);

			pb.setMax(100);
			pb.setIndeterminate(false);

			Integer percentage = new Integer(0);

			while (true) {

				byte buffer[];

				if (size - downloaded > 50) {
					buffer = new byte[50];
				} else {
					buffer = new byte[(int) (size - downloaded)];
				}

				int ok = is.read(buffer);

				if (ok == -1) {
					break;
				}
				// writing to buffer
				os.write(buffer, 0, ok);
				downloaded += ok;
				percentage = new Integer(
						(int) (((double) downloaded / (double) size) * 100));

				Message m = new Message();
				m.obj = new String("Fazendo o download de EP2012");
				m.arg1 = percentage.intValue();
				h.sendMessage(m);

			}

			Message m = new Message();
			m.obj = new String("Fazendo o download de EP2012");
			m.arg1 = percentage.intValue();
			h.sendMessage(m);

		} catch (IOException e) {
			Message m = new Message();
			m.obj = new String("Fazendo o download de EP2012");
			m.arg1 = -1;
			h.sendMessage(m);
		}

	}
}
