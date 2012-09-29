package br.usp.ime.servicosusp;

import android.app.Activity;
import android.content.Intent;

public class ErrorBox extends Activity{
	void showMsg(String s){
		Intent t=new Intent("showError:asd");
		startActivity(t);
	}
}
