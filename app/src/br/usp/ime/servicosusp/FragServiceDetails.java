package br.usp.ime.servicosusp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.usp.ime.servicosusp.model.Local;
import br.usp.ime.servicosusp.model.Service;
import br.usp.ime.uspmap.ShowMap;

public class FragServiceDetails extends Fragment {
	TextView tvBuildingAbv;
	TextView serviceName;
	TextView buildingName;
	TextView complement;
	TextView address;
	TextView serviceDesc;
	TextView tvTelephone;
	TextView serviceLink;
	Service thisService;
	Button btMap;
	Button btRoute;
	int numImages;

	int servId;
	Local thisBuilding;
	LinearLayout frPhotos;
	LinearLayout frPhotosMain;

	TextView tvServiceNameLabel;
	TextView tvServiceDescLabel;
	TextView tvLocalAddressLabel;

	boolean hasIv1Loaded;
	
	public FragServiceDetails() {
		servId = -1;
	}

	public FragServiceDetails(int s) {
		servId = s;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View retorno = inflater.inflate(R.layout.view_service_details,
				container, false);

		serviceName = (TextView) retorno.findViewById(R.id.tvServiceName);
		serviceDesc = (TextView) retorno
				.findViewById(R.id.tvServiceDescription);
		serviceLink = (TextView) retorno.findViewById(R.id.tvServiceLink);
		buildingName = (TextView) retorno.findViewById(R.id.tvBuildingName);
		complement = (TextView) retorno.findViewById(R.id.tvComplement);
		address = (TextView) retorno.findViewById(R.id.tvLocalAddress);
		serviceDesc = (TextView) retorno
				.findViewById(R.id.tvServiceDescription);
		frPhotosMain = (LinearLayout) retorno.findViewById(R.id.frPhotosMain);
		tvBuildingAbv = (TextView) retorno.findViewById(R.id.tvBuildingAbv);
		tvTelephone = (TextView) retorno.findViewById(R.id.tvTelephone);
		btMap = (Button) retorno.findViewById(R.id.btnMap);
		btRoute = (Button) retorno.findViewById(R.id.btnRoute);
		servId = -1;

		tvServiceNameLabel = (TextView) retorno
				.findViewById(R.id.tvServiceNameLabel);
		tvServiceDescLabel = (TextView) retorno
				.findViewById(R.id.tvServiceDescLabel);
		tvLocalAddressLabel = (TextView) retorno
				.findViewById(R.id.tvLocalAddressLabel);

		frPhotos = (LinearLayout) retorno.findViewById(R.id.frPhotos);
		hasIv1Loaded = false;

		return retorno;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		serviceName = (TextView) activity.findViewById(R.id.tvServiceName);
		serviceDesc = (TextView) activity.findViewById(
				R.id.tvServiceDescription);
		serviceLink = (TextView) activity.findViewById(R.id.tvServiceLink);
		buildingName = (TextView) activity.findViewById(
				R.id.tvBuildingName);
		complement = (TextView) activity.findViewById(R.id.tvComplement);
		address = (TextView) activity.findViewById(R.id.tvLocalAddress);
		serviceDesc = (TextView) activity.findViewById(
				R.id.tvServiceDescription);
		frPhotosMain = (LinearLayout) activity.findViewById(
				R.id.frPhotosMain);
		tvBuildingAbv = (TextView) activity.findViewById(
				R.id.tvBuildingAbv);
		tvTelephone = (TextView) activity.findViewById(R.id.tvTelephone);
		btMap = (Button) activity.findViewById(R.id.btnMap);
		btRoute = (Button) activity.findViewById(R.id.btnRoute);
		servId = -1;

		tvServiceNameLabel = (TextView) activity.findViewById(
				R.id.tvServiceNameLabel);
		tvServiceDescLabel = (TextView) activity.findViewById(
				R.id.tvServiceDescLabel);
		tvLocalAddressLabel = (TextView) activity.findViewById(
				R.id.tvLocalAddressLabel);

		frPhotos = (LinearLayout) activity.findViewById(R.id.frPhotos);
		hasIv1Loaded = false;
		

	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		btMap.setVisibility(View.INVISIBLE);
		btRoute.setVisibility(View.INVISIBLE);
		tvBuildingAbv.setText(getResources().getString(R.string.selectAnItem));
		tvServiceNameLabel.setText("");
		tvServiceDescLabel.setText("");
		tvLocalAddressLabel.setText("");

	}

	void changeService(int servId) {

		btMap.setVisibility(View.VISIBLE);
		btRoute.setVisibility(View.VISIBLE);
		tvServiceNameLabel.setText(getResources().getString(
				R.string.serviceName));
		tvServiceDescLabel.setText(getResources().getString(
				R.string.serviceDescription));
		tvLocalAddressLabel.setText(getResources().getString(
				R.string.serviceAddress));

		this.servId = servId;

		frPhotos.removeAllViews();

		fillComponents();
		setupServiceLink();
		setupMapButtons();
	}

	void setupMapButtons() {

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
		thisService = Service.getServiceById(servId);

		if (thisService != null) {
			thisBuilding = Local.getLocalById(thisService.getCodloc());
		}

		if (thisService != null && thisBuilding != null) {

			tvBuildingAbv.setText(thisBuilding.getAbbrevloc());
			serviceName.setText(thisService.getNomesrv());
			buildingName.setText(thisBuilding.getNameloc());
			address.setText(thisBuilding.getEndloc());
			complement.setText(thisService.getCplloc());
			serviceDesc.setText(thisService.getDescsrv());
			serviceLink.setText(thisService.getLink());
			if (!"".equals(thisService.getTel1())) {
				tvTelephone.setText("Tel: " + thisService.getTel1());
			}
			if (!"".equals(thisService.getTel2())) {
				tvTelephone.setText(tvTelephone.getText() + "\nTel2: "
						+ thisService.getTel1());
			}

			numImages = 0;
			if (!"".equals(thisBuilding.getUrlPhoto1())) {
				numImages++;
			}

			if (!"".equals(thisBuilding.getUrlPhoto2())) {
				numImages++;
			}

			if (!"".equals(thisBuilding.getUrlPhoto3())) {
				numImages++;
			}

			if (!"".equals(thisBuilding.getUrlPhoto1())) {
				insertImagemFromHttp(thisBuilding.getUrlPhoto1());
			}

			if (!"".equals(thisBuilding.getUrlPhoto2())) {
				insertImagemFromHttp(thisBuilding.getUrlPhoto2());
			}

			if (!"".equals(thisBuilding.getUrlPhoto3())) {
				insertImagemFromHttp(thisBuilding.getUrlPhoto3());
			}

		} else {
			Log.e("ServicosUSP", "ServicoID não encontrado");

		}
	}

	private void insertImagemFromHttp(final String http) {

		frPhotosMain.setVisibility(LinearLayout.VISIBLE);

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
				iv1.setMaxWidth(getActivity().getWindowManager()
						.getDefaultDisplay().getWidth()
						/ numImages / 2);
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
							iv1.setVisibility(1);
							frPhotos.removeView(prog);
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

	private OptionsMenu optionsMenu;

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		optionsMenu = new OptionsMenu(menu, getActivity());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		optionsMenu.OptionsHandler(item);
		return true;
	}

	private void setupServiceLink() {
		serviceLink.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri
						.parse((String) serviceLink.getText()));
				startActivity(i);
			}
		});
	}

}
