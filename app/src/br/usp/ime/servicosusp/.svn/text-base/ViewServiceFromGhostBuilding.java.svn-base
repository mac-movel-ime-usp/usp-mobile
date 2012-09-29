package br.usp.ime.servicosusp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import br.usp.ime.servicosusp.model.Service;

public class ViewServiceFromGhostBuilding extends Activity {
	TextView serviceName;
	TextView serviceDesc;
	TextView serviceLink;
	Service thisService;
	int servId;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_service_without_building);

		serviceName = (TextView) findViewById(R.id.tvServiceName);
		serviceDesc = (TextView) findViewById(R.id.tvServiceDescription);
		serviceLink = (TextView) findViewById(R.id.tvServiceLink);
		servId = getIntent().getExtras().getInt("ServiceID");
	
		fillComponents();
	}

	void fillComponents() {
		thisService = Service.getServiceById(servId);

		if (thisService != null) {
			serviceName.setText(thisService.getNomesrv());
			serviceDesc.setText(thisService.getDescsrv());	
			serviceLink.setText(thisService.getLink());
		} else {
			Log.e("ServicosUSP", "ServicoID não encontrado");
		}
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
