package br.usp.ime.servicosusp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ViewSettings extends Activity {

	EditText editSettingsUserJanus;
	EditText editSettingsPassJanus;

	Button btSave;
	Button btCancel;

	HashMap<String, String> sett;
	static String filepath="/data/data/br.usp.ime.servicosusp/databases/settings.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_settings);

		editSettingsUserJanus = (EditText) findViewById(R.id.editSettingsUserJanus);
		editSettingsPassJanus = (EditText) findViewById(R.id.editSettingsPassJanus);

		btSave = (Button) findViewById(R.id.btSaveSettings);
		btCancel = (Button) findViewById(R.id.btCancelSettings);
		sett=new HashMap<String, String>();

		loadSettings();
		fillComponents();
		setupSaveClickEvent();
		setupCancelClickEvent();
		setupTextListener();
	}

	private void setupCancelClickEvent() {
		btCancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void setupSaveClickEvent() {
		btSave.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				saveSettings();
			}
		});
	}

	private void setupTextListener() {

		editSettingsUserJanus.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				sett.put("UserJanus", s.toString());
			}
		});
		editSettingsPassJanus.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void afterTextChanged(Editable s) {
				sett.put("PasswordJanus", s.toString());
			}
		});
		


	}

	private void fillComponents() {
		if (sett.containsKey("UserJanus")) {
			editSettingsUserJanus.setText(sett.get("UserJanus"));
		}
		if (sett.containsKey("PasswordJanus")) {
			editSettingsPassJanus.setText(sett.get("PasswordJanus"));
		}
		
	}

	@SuppressWarnings("unchecked")
	private void loadSettings() {

		
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(filepath));
			ObjectInputStream ois = new ObjectInputStream(fis);
			sett = (HashMap<String, String>) ois.readObject();

			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void saveSettings() {

		FileOutputStream fis;
		try {
			fis = new FileOutputStream(new File(filepath));
			ObjectOutputStream ois = new ObjectOutputStream(fis);

			ois.writeObject(sett);

			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("unchecked")
	public static String loadSetting(String key){

		HashMap<String, String> sett = new HashMap<String, String>();
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(filepath));
			ObjectInputStream ois = new ObjectInputStream(fis);
			sett = (HashMap<String, String>) ois.readObject();

			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		return sett.get(key);
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
