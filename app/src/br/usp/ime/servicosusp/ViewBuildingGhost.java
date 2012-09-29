package br.usp.ime.servicosusp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.usp.ime.servicosusp.model.Local;
import br.usp.ime.servicosusp.model.Service;

public class ViewBuildingGhost extends Activity {
	Local thisBuilding;
	ListView lvBuildingServices;
	protected boolean hasIv1Loaded;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_ghost_building_details);

		thisBuilding = Local.getLocalById(getIntent().getIntExtra("BuildingID",
				0));

		lvBuildingServices = (ListView) findViewById(R.id.lvBuildingServices);

		fillComponents();
		setupSelectionEvent();
	}

	void fillComponents() {
		ArrayAdapter<Service> aa = new ArrayAdapter<Service>(this,
				android.R.layout.simple_list_item_1,
				Service.getServiceByLocal(thisBuilding.getCodloc()));
		lvBuildingServices.setAdapter(aa);
		aa.notifyDataSetChanged();
	}

	void setupSelectionEvent() {
		lvBuildingServices.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Service s = (Service) arg0.getAdapter().getItem(arg2);

				// Check for special services
				if (s.getCodsrv() == 9999) {
					// Case Request Grades:
					if (((servicosUsp) getApplicationContext())
							.hasInternetConnection(ViewBuildingGhost.this)) {
						Intent i = new Intent(getApplicationContext(),
								ViewJanus.class);
						startActivity(i);
					}else{
						Utils.createNoConnectionDialog(ViewBuildingGhost.this).show();
					}
				} else if (s.getCodsrv() == 9997) {
					// Case Circular
					Intent i = new Intent(getApplicationContext(),
							ViewCircular.class);
					startActivity(i);
				} else if (s.getCodsrv() >= 9991 && s.getCodsrv() <= 9996) {
					// Case Restaurants
					Intent i = new Intent(getApplicationContext(),
							ViewRestDetails.class);
					i.putExtra("ServiceID", s.getCodsrv());
					startActivity(i);
				} else {
					Intent i = new Intent(getApplicationContext(),
							ViewServiceFromGhostBuilding.class);
					i.putExtra("ServiceID", s.getCodsrv());
					startActivity(i);
				}
				Log.i("ServicosUSP", String.valueOf(s.getCodsrv()));
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
