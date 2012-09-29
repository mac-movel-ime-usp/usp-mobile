package br.usp.ime.servicosusp;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import br.usp.ime.servicosusp.model.Service;

public class FragListServices extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.view_services, container);
	}

	ListView listServices;
	EditText editFilter;
	ArrayList<Service> services;
	ArrayAdapter<Service> aa;
	Spinner spin;
	FragServiceDetails det;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// setContentView(R.layout.view_services);

		listServices = (ListView) getActivity().findViewById(R.id.listServices);
		editFilter = (EditText) getActivity().findViewById(R.id.editFilter);
		spin = (Spinner) getActivity().findViewById(R.id.spFilterType);

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

		if (getActivity().getIntent().getExtras() != null
				&& getActivity().getIntent().getExtras()
						.containsKey("CategoryID")) {
			services = Service.getServicesByCategory(getActivity().getIntent()
					.getExtras().getInt("CategoryID"));
		} else {
			services = Service.getServices();
		}

		aa = new ArrayAdapter<Service>(getActivity().getApplicationContext(),
				R.layout.lists, services);
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
						if (getActivity().getIntent().getExtras() != null
								&& getActivity().getIntent().getExtras()
										.containsKey("CategoryID")) {
							services.addAll(Service.getServicesByCatAndKWord(
									getActivity().getIntent().getExtras()
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

		det = (FragServiceDetails) getFragmentManager().findFragmentById(
				R.id.viewServiceDetails_fragment);

		listServices.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Service s = (Service) arg0.getAdapter().getItem(arg2);

				// Check for special services
				if (s.getCodsrv() == 9999) {
					// Case Request Grades:
					if (((servicosUsp) getActivity().getApplicationContext())
							.hasInternetConnection(getActivity())) {
						Intent i = new Intent(getActivity()
								.getApplicationContext(), ViewJanus.class);
						startActivity(i);
					}else{
						Utils.createNoConnectionDialog(getActivity()).show();
					}
				} else if (s.getCodsrv() == 9997) {
					// Case Circular
					Intent i = new Intent(
							getActivity().getApplicationContext(),
							ViewCircular.class);
					startActivity(i);
				} else if (s.getCodloc() == 0) {
					Intent i = new Intent(
							getActivity().getApplicationContext(),
							ViewServiceFromGhostBuilding.class);
					i.putExtra("ServiceID", s.getCodsrv());
					startActivity(i);
				} else if (s.getCodsrv() >= 9991 && s.getCodsrv() <= 9996) {
					Intent i = new Intent(
							getActivity().getApplicationContext(),
							ViewRestDetails.class);
					i.putExtra("ServiceID", s.getCodsrv());
					startActivity(i);
				} else {
					det.changeService(s.getCodsrv());

				}
				Log.i("ServicosUSP", String.valueOf(s.getCodsrv()));
			}

		});

	}

}
