package br.usp.ime.servicosusp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class ViewJanus extends Activity {
	WebView web;
	int Phase;
	String user;
	String pass;
	AlertDialog ad;
	TextView tvHttp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_janus);

		web = (WebView) findViewById(R.id.WebBrowser);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setSaveFormData(false);
		web.getSettings().setSavePassword(false);
		Phase = 1;
		web.clearCache(true);
		web.clearFormData();
		web.clearHistory();

		user = ViewSettings.loadSetting("UserJanus");
		pass = ViewSettings.loadSetting("PasswordJanus");

		if (user == null || user.equals("") || pass == null || pass.equals("")) {
			final AlertDialog d = Utils.createDialog(this, getResources()
					.getString(R.string.noJanusData),
					getResources().getString(R.string.noJanusDataDetails));
			d.setCancelable(true);
			d.setCanceledOnTouchOutside(true);
			d.setOnCancelListener(new OnCancelListener() {

				public void onCancel(DialogInterface dialog) {
					d.hide();
					d.dismiss();
					Intent i = new Intent(ViewJanus.this, ViewSettings.class);
					startActivity(i);
				}
			});

			d.show();

		}

		tvHttp = (TextView) findViewById(R.id.tvHttp);

		web.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				afterLoaded();
			}

		});

		ad = Utils.createDialog(this,
				getResources().getString(R.string.acessingJanus),
				getResources().getString(R.string.acessingJanusDetails));
		// ad.show();
		web.loadUrl("https://sistemas.usp.br/janus/comum/entrada.jsf");
		// connect();
	}

	private void afterLoaded() {
		if (Phase == 1) {
			web.loadUrl("javascript:document.getElementById('loginForm:usuario').value='"
					+ user + "';");
			web.loadUrl("javascript:document.getElementById('loginForm:senha').value='"
					+ pass + "';");
			web.loadUrl("javascript:document.getElementById('loginForm:login').click();");

		} else if (Phase == 2) {
			web.loadUrl("javascript:gotoAction('FichaDoAluno');");
			ad.hide();
			ad.dismiss();
		}

		Phase++;
		// connect();
	}

	void connect() {

		final Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				tvHttp.setText((String) msg.obj);
				Toast.makeText(ViewJanus.this, "Terminei", Toast.LENGTH_LONG)
						.show();

				ad.hide();
				ad.dismiss();

			}
		};

		ad.show();

		new Thread(new Runnable() {

			public void run() {
				try {

					DefaultHttpClient c = new DefaultHttpClient();
					HttpContext context = new BasicHttpContext();
					CookieStore cookie = new BasicCookieStore();

					context.setAttribute(ClientContext.COOKIE_STORE, cookie);
					HttpPost post = new HttpPost(
							"https://sistemas.usp.br/janus/comum/entrada.jsf");

					post.addHeader("Content-Type",
							"application/x-www-form-urlencoded");
					/*
					 * List<NameValuePair> nameValuePairs = new
					 * ArrayList<NameValuePair>( 2); nameValuePairs.add(new
					 * BasicNameValuePair( "loginForm:usuario", "8092791"));
					 * nameValuePairs.add(new BasicNameValuePair(
					 * "loginForm:senha", "cessna")); nameValuePairs.add(new
					 * BasicNameValuePair( "loginForm:login", "Entrar"));
					 * post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					 */

					String response = fromStreamToString(c
							.execute(post, context).getEntity().getContent());

					Message m = new Message();
					m.obj = new String(response);
					h.sendMessage(m);

				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	String fromStreamToString(InputStream is) {
		InputStreamReader isr = new InputStreamReader(is);
		StringWriter sw = new StringWriter();

		char[] buffer = new char[1024];

		try {
			int r;
			while ((r = isr.read(buffer)) != -1) {
				sw.write(buffer, 0, r);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sw.toString();
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
