package br.usp.ime.servicosusp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import br.usp.ime.servicosusp.model.Service;

public class ViewServices extends Activity {

	ListView listServices;
	EditText editFilter;
	ArrayList<Service> services;
	ArrayAdapter<Service> aa;
	Spinner spin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_services);
		listServices = (ListView) findViewById(R.id.listServices);
		editFilter = (EditText) findViewById(R.id.editFilter);
		spin = (Spinner) findViewById(R.id.spFilterType);

		populateServicesList();

		setupSearchEvent();
		setupSpinFilterEvent();
		setupSelectionEvent();
		editFilter.clearFocus();
	}

	void setupSpinFilterEvent() {
		spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 == 0) {
					populateServicesList();
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	void populateServicesList() {

		if (getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey("CategoryID")) {
			services = Service.getServicesByCategory(getIntent().getExtras()
					.getInt("CategoryID"));
		} else {
			services = Service.getServices();
		}

		aa = new ArrayAdapter<Service>(this,
				android.R.layout.simple_list_item_1, services);
		listServices.setAdapter(aa);

		listServices.setTextFilterEnabled(true);

		aa.notifyDataSetChanged();
	}

	void setupSearchEvent() {
		editFilter.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {

				if (s.toString().equals("")) {
					listServices.clearTextFilter();
					populateServicesList();
				} else {
					if (spin.getSelectedItemPosition() == 0) {
						listServices.setFilterText(s.toString());
					} else {
						listServices.clearTextFilter();
						services.clear();
						if (getIntent().getExtras() != null
								&& getIntent().getExtras().containsKey(
										"CategoryID")) {
							services.addAll(Service.getServicesByCatAndKWord(
									getIntent().getExtras()
											.getInt("CategoryID"), s.toString()));
						} else {
							services.addAll(Service.getServicesByKeyword(s
									.toString()));
						}
						aa.notifyDataSetChanged();
						listServices.clearTextFilter();
					}
				}

			}
		});
	}

	void setupSelectionEvent() {
		listServices.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Service s = (Service) arg0.getAdapter().getItem(arg2);

				// Check for special services
				if (s.getCodsrv() == 9999) {
					// Case Request Grades:
					if (((servicosUsp) getApplicationContext())
							.hasInternetConnection(ViewServices.this)) {
						Intent i = new Intent(getApplicationContext(),
								ViewJanus.class);
						startActivity(i);
					}else{
						Utils.createNoConnectionDialog(ViewServices.this).show();
					}
				} else if (s.getCodsrv() == 9997) {
					// Case Circular
					Intent i = new Intent(getApplicationContext(),
							ViewCircular.class);
					startActivity(i);
				} else if (s.getCodloc() == 0) {
					// Case Ghost Building
					Intent i = new Intent(getApplicationContext(),
							ViewServiceFromGhostBuilding.class);
					i.putExtra("ServiceID", s.getCodsrv());
					startActivity(i);
				} else if (s.getCodsrv() >= 9991 && s.getCodsrv() <= 9996) {
					// Case Restaurants
					Intent i = new Intent(getApplicationContext(),
							ViewRestDetails.class);
					i.putExtra("ServiceID", s.getCodsrv());
					startActivity(i);
				} else {
					// Others
					Intent i = new Intent(getApplicationContext(),
							ViewServiceDetails.class);
					i.putExtra("ServiceID", s.getCodsrv());
					startActivity(i);
				}
				// Log.i("ServicosUSP", String.valueOf(s.getCodsrv()));
			}

		});
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
