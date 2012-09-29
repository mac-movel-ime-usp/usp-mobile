package br.usp.ime.servicosusp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import br.usp.ime.servicosusp.model.Service;

public class ViewCardapio extends Activity {
	/** Called when the activity is first created. */

	Service thisRestaurante;
	WebView mWebView;
	Integer servId;
	ProgressDialog pd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_cardapio);

		mWebView = (WebView) findViewById(R.id.webview);

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				while (view.zoomOut()) {
				}
				;
				view.scrollTo(view.getWidth() / 2, 0);
				pd.hide();
				pd.dismiss();
			}
		});
		// Obter codigo do serviï¿½o selecionado

		servId = getIntent().getExtras().getInt("ServiceID");
		thisRestaurante = Service.getServiceById(servId);
	}

	@Override
	protected void onStart() {
		super.onStart();
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setTitle("Por favor aguarde");
		pd.setMessage("Carregando o cardápio...");
		pd.setCanceledOnTouchOutside(false);
		pd.setCancelable(false);
		pd.show();
		mWebView.loadUrl(thisRestaurante.getLink());

	}

}