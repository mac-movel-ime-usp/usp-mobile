package br.usp.ime.servicosusp.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import br.usp.ime.servicosusp.Utils;

import com.google.gson.Gson;

public class BandejaoComments {

	private List<Map<String, String>> list;
	public List<Comment> comments;

	BandejaoComments() {
		list = new ArrayList<Map<String, String>>();
		comments = new ArrayList<Comment>();
	}

	public static boolean sendComment(String bandejaoName, Comment c) {
		DefaultHttpClient http = new DefaultHttpClient();

		HttpPost post = new HttpPost(
				"http://valinhos.ime.usp.br:56080/usp-mobile/bandejao/"
						.concat(bandejaoName));

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

		String f = new String();

		if (c.fila.equals("Sem fila")) {
			f = "SEM_FILA";
		} else if (c.fila.equals("Pequena")) {
			f = "PEQUENA";
		} else if (c.fila.equals("Média")) {
			f = "MEDIA";
		} else if (c.fila.equals("Grande")) {
			f = "GRANDE";
		} else if (c.fila.equals("Muito Grande")) {
			f = "MUITO_GRANDE";
		}

		nameValuePairs.add(new BasicNameValuePair("comentario.fila", f));
		nameValuePairs.add(new BasicNameValuePair("comentario.texto", c.texto));
		post.addHeader("Content-type", "application/x-www-form-urlencoded");

		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			http.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static List<Comment> getObject(String bandejaoName) {

		Gson g = new Gson();

		DefaultHttpClient http = new DefaultHttpClient();
		String fullText;

		HttpGet get = new HttpGet(
				"http://valinhos.ime.usp.br:56080/usp-mobile/bandejao/"
						.concat(bandejaoName));

		BandejaoComments r = null;

		try {

			fullText = Utils.convertStreamToString(http.execute(get)
					.getEntity().getContent());

			r = g.fromJson(fullText, BandejaoComments.class);
			for (Map<String, String> i : r.list) {

				SimpleDateFormat formatter = new SimpleDateFormat(
						"ss-mm-HH-dd-MM-yy");

				String texto = new String(i.get("texto"));
				Date hora = (Date) formatter.parse(i.get("hora"));
				String fila = new String("SEM_FILA");
				if (i.get("fila") != null) {
					fila = new String(i.get("fila"));
				}
				Comment c = new Comment(texto, hora, fila);
				r.comments.add(c);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (r != null) {
			// Ordena do mais recente para o menos recente
			Collections.sort(r.comments, new Comparator<Comment>() {

				public int compare(Comment lhs, Comment rhs) {
					return rhs.hora.compareTo(lhs.hora);
				}
			});
			return r.comments;
		} else {
			return new ArrayList<Comment>(0);
		}

	}

}
