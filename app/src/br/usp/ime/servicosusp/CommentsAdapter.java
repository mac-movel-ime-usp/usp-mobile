package br.usp.ime.servicosusp;

import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.usp.ime.servicosusp.model.Comment;

public class CommentsAdapter extends BaseAdapter {

	private Context c;
	List<Comment> lista;

	public CommentsAdapter(Context context, List<Comment> lista) {
		this.c = context;
		this.lista = lista;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			LayoutInflater vi = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.itemview_comment, null);
		}
		ImageView imIcon = (ImageView) convertView.findViewById(R.id.item_icon);
		TextView tvText = (TextView) convertView.findViewById(R.id.tvItemText);
		TextView tvHour = (TextView) convertView.findViewById(R.id.tvItemHour);
		Comment o = (Comment) getItem(position);
		if (o != null) {

			tvText.setText(o.texto);
			tvHour.setText(DateFormat.format("dd/MM/yy h:mmaa", o.hora));

			if (o.fila.equals("SEM_FILA")) {
				imIcon.setImageResource(R.drawable.queue_1);
			} else if (o.fila.equals("PEQUENA")) {
				imIcon.setImageResource(R.drawable.queue_2);
			} else if (o.fila.equals("MEDIA")) {
				imIcon.setImageResource(R.drawable.queue_3);
			} else if (o.fila.equals("GRANDE")) {
				imIcon.setImageResource(R.drawable.queue_4);
			} else if (o.fila.equals("MUITO_GRANDE")) {
				imIcon.setImageResource(R.drawable.queue_5);
			}

		}
		return convertView;

	}

	public int getCount() {
		return lista.size();
	}

	public Object getItem(int arg0) {
		return lista.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

}
