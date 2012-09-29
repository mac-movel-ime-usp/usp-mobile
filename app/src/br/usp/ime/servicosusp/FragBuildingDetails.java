package br.usp.ime.servicosusp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.usp.ime.servicosusp.model.Local;
import br.usp.ime.servicosusp.model.Service;
import br.usp.ime.uspmap.ShowMap;

public class FragBuildingDetails extends Fragment {

	Local thisBuilding;
	TextView tvBuildingAbrev;
	TextView tvBuildingName;
	ListView lvBuildingServices;
	LinearLayout frPhotos;
	TextView tvPhotosLabel;
	TextView tvLocalAddress;
	TextView tvLocalAddressLabel;
	TextView tvComplement;
	TextView tvServiceNameLabel;
	protected boolean hasIv1Loaded;
	Button btMap;
	Button btRoute;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View retorno = inflater.inflate(R.layout.view_buildings_details,
				container, false);

		// thisBuilding=
		// Local.getLocalById(getIntent().getIntExtra("BuildingID", 0));

		// Log.i("Teste", thisBuilding.getNameloc());

		tvBuildingAbrev = (TextView) retorno.findViewById(R.id.tvBuildingAbv);
		tvBuildingName = (TextView) retorno.findViewById(R.id.tvBuildingName);
		lvBuildingServices = (ListView) retorno
				.findViewById(R.id.lvBuildingServices);
		frPhotos = (LinearLayout) retorno.findViewById(R.id.frPhotos);
		tvPhotosLabel = (TextView) retorno.findViewById(R.id.tvPhotosLabel);
		tvLocalAddress = (TextView) retorno.findViewById(R.id.tvLocalAddress);
		tvLocalAddressLabel = (TextView) retorno
				.findViewById(R.id.tvLocalAddressLabel);
		tvComplement = (TextView) retorno.findViewById(R.id.tvComplement);
		tvServiceNameLabel = (TextView) retorno
				.findViewById(R.id.tvServiceNameLabel);
		btMap = (Button) retorno.findViewById(R.id.btnMap);
		btRoute = (Button) retorno.findViewById(R.id.btnRoute);

		return retorno;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		btMap.setVisibility(View.INVISIBLE);
		btRoute.setVisibility(View.INVISIBLE);
		tvPhotosLabel.setVisibility(View.INVISIBLE);

		tvBuildingAbrev.setText(getResources().getString(R.string.selectAnItem));
		tvBuildingName.setText("");
		tvServiceNameLabel.setText("");
		tvComplement.setText("");

		tvLocalAddressLabel.setText("");

	}

	void changeBuilding(int id) {

		thisBuilding = Local.getLocalById(id);
		frPhotos.removeAllViews();

		fillComponents();
		setupSelectionEvent();
		setupButtons();
	}

	void setupButtons() {
		if (thisBuilding != null) {
			btMap.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					Intent myIntent = new Intent(view.getContext(),
							ShowMap.class);
					myIntent.putExtra("centerOnUser", false);
					myIntent.putExtra("Latitude", thisBuilding.getPosLat());
					myIntent.putExtra("Longitute", thisBuilding.getPosLong());
					myIntent.putExtra("displayCircular1Route", false);
					myIntent.putExtra("displayCircular2Route", false);
					startActivity(myIntent);
				}
			});
		}

		if (thisBuilding != null) {
			btRoute.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// Log.i("lat",
					// String.valueOf(thisBuilding.getPosLat()/1e6));
					// Log.i("long",
					// String.valueOf(thisBuilding.getPosLong()/1e6));
					Intent intent = new Intent(
							android.content.Intent.ACTION_VIEW, Uri
									.parse("google.navigation:q="
											+ String.valueOf(thisBuilding
													.getPosLat() / 1e6)
											+ ","
											+ String.valueOf(thisBuilding
													.getPosLong() / 1e6)));
					startActivity(intent);
				}
			});
		}
	}

	void fillComponents() {

		tvComplement.setText(getResources().getString(R.string.complement));
		tvServiceNameLabel.setText(getResources().getString(
				R.string.avaluableServices));
		tvPhotosLabel.setText(getResources().getString(R.string.photos));
		tvLocalAddressLabel.setText(getResources().getString(
				R.string.serviceAddress));
		btMap.setVisibility(View.VISIBLE);
		btRoute.setVisibility(View.VISIBLE);
		tvPhotosLabel.setVisibility(View.VISIBLE);

		tvBuildingAbrev.setText(thisBuilding.getAbbrevloc());
		tvBuildingName.setText(thisBuilding.getNameloc());

		ArrayAdapter<Service> aa = new ArrayAdapter<Service>(getActivity(),
				android.R.layout.simple_list_item_1,
				Service.getServiceByLocal(thisBuilding.getCodloc()));
		lvBuildingServices.setAdapter(aa);
		aa.notifyDataSetChanged();

		loadPhotos();

		tvLocalAddress.setText(thisBuilding.getEndloc());
		tvComplement.setText(thisBuilding.getCep());
	}

	void loadPhotos() {

		if (!"".equals(thisBuilding.getUrlPhoto1())) {
			insertImagemFromHttp(thisBuilding.getUrlPhoto1());
		}

		if (!"".equals(thisBuilding.getUrlPhoto2())) {
			insertImagemFromHttp(thisBuilding.getUrlPhoto2());
		}

		if (!"".equals(thisBuilding.getUrlPhoto3())) {
			insertImagemFromHttp(thisBuilding.getUrlPhoto3());
		}

	}

	private void insertImagemFromHttp(final String http) {

		frPhotos.setVisibility(LinearLayout.VISIBLE);

		new Thread(new Runnable() {

			public void run() {
				// String u =
				// "https://www.google.com.br/intl/pt-BR_ALL/images/logos/images_logo_lg.gif";
				String u = http;
				final ProgressBar prog = new ProgressBar(getActivity()
						.getApplicationContext(), null,
						android.R.attr.progressBarStyleHorizontal);
				prog.setMinimumWidth(20);

				frPhotos.addView(prog);
				final ImageView iv1 = new ImageView(getActivity()
						.getApplicationContext());
				iv1.setMaxHeight(200);
				iv1.setMaxWidth(500);
				iv1.setAdjustViewBounds(true);

				iv1.setLayoutParams(new LayoutParams(
						android.view.ViewGroup.LayoutParams.FILL_PARENT,
						android.view.ViewGroup.LayoutParams.FILL_PARENT));

				frPhotos.addView(iv1);

				servicosUsp appCtx = (servicosUsp) getActivity()
						.getApplicationContext();

				if (appCtx.loadedImagesBuffer.containsKey(u)) {
					final Drawable bit_temp = appCtx.loadedImagesBuffer.get(u);
					iv1.setImageDrawable(bit_temp);
					hasIv1Loaded = true;
					iv1.setVisibility(1);
					frPhotos.removeView(prog);
					Log.i("teste", "Contains");

				} else {
					final Drawable bit_temp = loadImageFromNetwork(u, prog);
					appCtx.loadedImagesBuffer.put(u, bit_temp);
					iv1.post(new Runnable() {

						public void run() {
							iv1.setImageDrawable(bit_temp);
							hasIv1Loaded = true;
							frPhotos.removeView(prog);
							iv1.setVisibility(1);

						}
					});

				}

			}
		}).start();
	}

	private Drawable loadImageFromNetwork(String url, final ProgressBar pb) {
		try {
			URL req = new URL(url);
			HttpURLConnection conection = (HttpURLConnection) req
					.openConnection();
			int size = conection.getContentLength();
			int downloaded = 0;
			conection.connect();

			InputStream is = conection.getInputStream();

			ByteArrayOutputStream os = new ByteArrayOutputStream(size);

			pb.setMax(100);
			pb.setIndeterminate(false);

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
				final Integer percentage = new Integer(
						(int) (((double) downloaded / (double) size) * 100));

				pb.post(new Runnable() {
					public void run() {
						pb.setProgress(percentage);
					}
				});
			}

			Drawable d = Drawable.createFromStream(
					new ByteArrayInputStream(os.toByteArray()), null);
			return d;
		} catch (Exception e) {
			Log.e(getActivity().getApplicationContext().getPackageName(),
					e.getLocalizedMessage());
			return null;
		}
	}

	void setupSelectionEvent() {
		lvBuildingServices.setOnItemClickListener(new OnItemClickListener() {

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
				} else if (s.getCodsrv() >= 9991 && s.getCodsrv() <= 9996) {
					// Case Restaurants
					Intent i = new Intent(getActivity().getApplicationContext(),
							ViewRestDetails.class);
					i.putExtra("ServiceID", s.getCodsrv());
					startActivity(i);
				} else {
					Intent i = new Intent(
							getActivity().getApplicationContext(),
							ViewServiceDetails.class);
					i.putExtra("ServiceID", s.getCodsrv());
					startActivity(i);

					/*
					 * FragServiceDetails frag = new
					 * FragServiceDetails(s.getCodsrv()); FragmentTransaction tr
					 * = getFragmentManager().beginTransaction();
					 * tr.replace(R.id.viewBuildingDetails_fragment, frag);
					 * tr.commitAllowingStateLoss();
					 */
				}
				Log.i("ServicosUSP", String.valueOf(s.getCodsrv()));
			}
		});
	}
}
