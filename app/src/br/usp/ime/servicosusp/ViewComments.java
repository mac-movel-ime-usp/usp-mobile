package br.usp.ime.servicosusp;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import br.usp.ime.servicosusp.model.BandejaoComments;
import br.usp.ime.servicosusp.model.Comment;

public class ViewComments extends Activity {
	TextView tvJsonData;
	ListView lvComments;
	ProgressDialog pg;
	TextView edtMessage;
	Spinner elvFila;
	Button btSendComment;
	int sid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_rest_comments);
		tvJsonData = (TextView) findViewById(R.id.tvJsonData);
		lvComments = (ListView) findViewById(R.id.lvComments);
		edtMessage = (TextView) findViewById(R.id.edtMessage);
		elvFila = (Spinner) findViewById(R.id.elvFila);
		btSendComment = (Button) findViewById(R.id.btSendComment);
		sid = getIntent().getExtras().getInt("ServiceID");
		getCommentsFromRestaurant();

		setupSendButton();
	}

	String getNickRestaurant(int sid) {
		String ret = new String();
		switch (sid) {
		case 9996:
			ret = "central";
			break;
		case 9995:
			ret = "quimica";
			break;
		case 9994:
			ret = "fisica";
			break;
		case 9993:
			ret = "saude_publica";
			break;
		case 9992:
			ret = "professores";
			break;
		case 9991:
			ret = "cocesp";
			break;
		default:
			ret = "";
			break;
		}
		return ret;
	}

	void setupSendButton() {
		btSendComment.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				new Thread(new Runnable() {

					public void run() {
						BandejaoComments.sendComment(getNickRestaurant(sid), new Comment(
								edtMessage.getText().toString(), new Date(),
								(String) elvFila.getSelectedItem()));
					}
				}).start();

				AlertDialog.Builder alb = new AlertDialog.Builder(
						ViewComments.this);
				alb.setTitle("Mensage Enviada!");
				alb.setMessage("Sua mensagem foi enviada com sucesso. Obrigado!");
				AlertDialog ad = alb.create();
				ad.setCanceledOnTouchOutside(true);
				ad.show();
				ad.setOnDismissListener(new OnDismissListener() {
					public void onDismiss(DialogInterface dialog) {
						getCommentsFromRestaurant();
					}
				});

			}
		});
	}

	void getCommentsFromRestaurant() {
		pg = new ProgressDialog(ViewComments.this);
		pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pg.setMessage("Carregando mensagens...");
		pg.show();

		Thread thread = new Thread(new Runnable() {
			public void run() {
				List<Comment> t = BandejaoComments.getObject(getNickRestaurant(sid));
				Message msg = new Message();
				msg.obj = t;
				handler.sendMessage(msg);

			}
		});

		thread.start();

	}

	final Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			@SuppressWarnings("unchecked")
			final CommentsAdapter ca = new CommentsAdapter(ViewComments.this,
					(List<Comment>) msg.obj);
			lvComments.setAdapter(ca);
			ca.notifyDataSetChanged();
			pg.hide();
			pg.dismiss();
		}
	};

}
