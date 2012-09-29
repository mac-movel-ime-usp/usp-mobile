package br.usp.ime.servicosusp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class ViewGrades extends Activity {
	Button btn;
	WebView web;
	EditText editUser;
	EditText editPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_grades);
		btn = (Button) findViewById(R.id.btnLogin);
		btn.setOnClickListener(new btnLogin_Click());
		web = (WebView) findViewById(R.id.WebBrowser);
		editUser = (EditText) findViewById(R.id.editUser);
		editPassword = (EditText) findViewById(R.id.editPassword);
	}

	private class btnLogin_Click implements OnClickListener {

		public void onClick(View arg0) {

			new Thread(new Runnable() {
				
				public void run() {
					
					HttpClient http = new DefaultHttpClient();
					HttpPost post = new HttpPost(
							"https://sistemas.usp.br/janus/comum/entrada.jsf");

					try {
						// Add your data
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								2);
						nameValuePairs.add(new BasicNameValuePair("loginForm:usuario",
								editUser.getText().toString()));
						nameValuePairs.add(new BasicNameValuePair("loginForm:senha",
								editPassword.getText().toString()));
						post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

						// Execute HTTP Post Request
						final HttpResponse response = http.execute(post);
						Log.e("Http", response.toString());
						

					} catch (ClientProtocolException e) {
					} catch (IOException e) {
					}
				}
				
			});
		}
			
	}

}
