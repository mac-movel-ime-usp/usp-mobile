package br.usp.ime.servicosusp.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import br.usp.ime.servicosusp.MainActivity;
import br.usp.ime.servicosusp.ViewBuildingDetails;
import br.usp.ime.servicosusp.ViewBuildings;
import br.usp.ime.servicosusp.ViewCategories;
import br.usp.ime.servicosusp.ViewServices;
import br.usp.ime.servicosusp.servicosUsp;
import br.usp.ime.servicosusp.model.Local;
import br.usp.ime.servicosusp.model.Service;
import br.usp.ime.uspmap.ShowMap;

import com.jayway.android.robotium.solo.Solo;

public class TestMain extends ActivityInstrumentationTestCase2<MainActivity> {

	Button dashboard_services;
	Button dashboard_buildings;
	Button dashboard_categories;
	Button dashboard_map;
	Solo solo;
	

	public TestMain() {
		super("br.usp.ime.servicosusp", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());

	}

	public void testDashboard() {
		solo.clickOnButton("Serviços");
		solo.assertCurrentActivity("Mapa", ViewServices.class);
		solo.goBack();

		solo.clickOnButton("Prédios");
		solo.assertCurrentActivity("Mapa", ViewBuildings.class);

		solo.goBack();

		solo.clickOnButton("Categorias");
		solo.assertCurrentActivity("Mapa", ViewCategories.class);

		solo.goBack();

		solo.clickOnButton("Mapa da USP");
		Log.i("TESTE", solo.getCurrentActivity().getClass().getName());
		solo.assertCurrentActivity("Mapa", ShowMap.class);

		solo.goBack();

	}

	public void testServiceDetailsNavigation() {
		solo.clickOnButton("Serviços");
		assertTrue(solo.waitForActivity("ViewServices", 2000));
		solo.waitForView(ListView.class);

		int numItens = solo.getCurrentListViews().get(0).getCount();
		for (int i = 1; i < numItens; i++) {
			Service text = (Service) solo.getCurrentListViews().get(0)
					.getItemAtPosition(i);
			solo.clickOnText(text.getNomesrv());

			solo.sleep(100);
			solo.goBack();
			solo.sleep(100);

		}

	}
	
	
	public void testBuildingDetailsNavigation() {
		solo.clickOnButton("Prédios");
		assertTrue(solo.waitForActivity("ViewBuildings", 2000));
		solo.waitForView(ListView.class);

		int numItens = solo.getCurrentListViews().get(0).getCount();
		for (int i = 1; i < numItens; i++) {
			Local text = (Local) solo.getCurrentListViews().get(0)
					.getItemAtPosition(i);
			solo.clickOnText(text.getNameloc());

			solo.sleep(500);
			solo.goBack();
			solo.sleep(500);

		}

	}

	public void testUpdateDatabase() {

		servicosUsp.dbHelper.getWritableDatabase().execSQL("insert into categoria(codctg,nomctg) values(8,'TEST TEST TEST')");
		solo.clickOnButton("Categorias");
		assertTrue(solo.waitForText("TEST TEST TEST",1,500));
		solo.goBack();
		solo.pressMenuItem(3);
		solo.clickOnButton("Atualizar");
		solo.waitForText("Ok");
		solo.clickOnText("Ok");
		solo.clickOnButton("Categorias");
		assertFalse(solo.waitForText("TEST TEST TEST",1,500));
	}

    public void testDashboardPredios() {
        solo.clickOnButton("Prédios");
        solo.sleep(100);
        solo.clickInList(5);
        solo.assertCurrentActivity("MAC",ViewBuildingDetails.class);
        solo.goBack();

    }
    
   public void testDashboardMapa() {
        solo.clickOnButton("Mapa da USP");
        solo.sleep(100);
        solo.assertCurrentActivity("MAC",ShowMap.class);
        solo.goBack();

    }
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
