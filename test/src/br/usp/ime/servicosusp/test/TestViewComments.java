package br.usp.ime.servicosusp.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import br.usp.ime.servicosusp.R;
import br.usp.ime.servicosusp.ViewComments;

import com.jayway.android.robotium.solo.Solo;

public class TestViewComments extends
		ActivityInstrumentationTestCase2<ViewComments> {

	Solo solo;
	TextView tvComment;
	Button btSend;

	public TestViewComments() {
		super("br.usp.ime.servicosusp", ViewComments.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityIntent(new Intent().putExtra("ServiceID", 9996));
		solo = new Solo(getInstrumentation(), getActivity());
		tvComment = (TextView) getActivity().findViewById(R.id.edtMessage);
		btSend = (Button) getActivity().findViewById(R.id.btSendComment);

	}

	public void testPostAndGet() {
		

		solo.waitForDialogToClose(5000);
		solo.enterText(0, "TestViewComments");
		solo.sleep(1000);
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
		solo.clickOnButton(0);
		solo.waitForDialogToClose(2000);
		solo.waitForText("TestViewComments", 1, 2000);
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
