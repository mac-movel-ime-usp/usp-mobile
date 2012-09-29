package br.usp.ime.servicosusp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.database.Cursor;
import br.usp.ime.servicosusp.servicosUsp;

public class Service implements Comparable<Service> {

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescsrv() {
		return descsrv;
	}

	public void setDescsrv(String descsrv) {
		this.descsrv = descsrv;
	}

	@Override
	public String toString() {
		return nomesrv;
	}

	private int codsrv;
	private int codctg;
	private int codloc;
	private String nomesrv;
	private String cplloc;
	private String tel1;
	private String tel2;
	private String email;
	private String link;
	private String descsrv;
	private String keywords;

	public Service(int codsrv, int codctg, int codloc, String nomesrv,
			String desc, String keywords, String cplloc, String tel1,
			String tel2, String email, String link) {
		super();
		this.codsrv = codsrv;
		this.codctg = codctg;
		this.codloc = codloc;
		this.nomesrv = nomesrv;
		this.cplloc = cplloc;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.email = email;
		this.link = link;
		this.descsrv = desc;
		this.keywords = keywords;

		if (this.cplloc == null) {
			this.cplloc = new String("");
		}
		if (this.tel1 == null) {
			this.tel1 = new String("");
		}
		if (this.tel2 == null) {
			this.tel2 = new String("");
		}
		if (this.email == null) {
			this.email = new String("");
		}
		if (this.link == null) {
			this.link = new String("");
		}
		if (this.descsrv == null) {
			this.descsrv = new String("");
		}
		if (this.keywords == null) {
			this.keywords = new String("");
		}
	}

	public Service() {
		super();
	}

	public int getCodsrv() {
		return codsrv;
	}

	public void setCodsrv(int codsrv) {
		this.codsrv = codsrv;
	}

	public int getCodctg() {
		return codctg;
	}

	public void setCodctg(int codctg) {
		this.codctg = codctg;
	}

	public int getCodloc() {
		return codloc;
	}

	public void setCodloc(int codloc) {
		this.codloc = codloc;
	}

	public String getNomesrv() {
		return nomesrv;
	}

	public void setNomesrv(String nomesrv) {
		this.nomesrv = nomesrv;
	}

	public String getCplloc() {
		return cplloc;
	}

	public void setCplloc(String cplloc) {
		this.cplloc = cplloc;
	}

	public String getTel1() {
		if (tel1 != null) {
			return tel1;
		} else {
			return new String("");
		}
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		if (tel2 != null) {
			return tel2;
		} else {
			return new String("");
		}
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public static ArrayList<Service> getServices() {

		ArrayList<Service> services = new ArrayList<Service>();

		Cursor banco = servicosUsp.dbHelper.getReadableDatabase().rawQuery(
				"Select * from Servico order by nomsrv", null);
		if (banco.moveToFirst()) {
			do {
				Service s = new Service(banco.getInt(0), banco.getInt(1),
						banco.getInt(2), banco.getString(3),
						banco.getString(4), banco.getString(5),
						banco.getString(6), banco.getString(7),
						banco.getString(8), banco.getString(9),
						banco.getString(10));
				services.add(s);
			} while (banco.moveToNext());
		}
		banco.close();
		// Add Special Service Grades...
		// TODO add more special services here + more details to the ones below
		Service grades = new Service();
		grades.setNomesrv("Acesso Janus");
		grades.setCodsrv(9999);
		grades.setCodctg(1);
		grades.setCodloc(0);
		grades.setKeywords("janus notas pos graduacao");
		services.add(grades);

		Service circular = new Service();
		circular.setNomesrv("Circular");
		circular.setCodsrv(9997);
		circular.setCodctg(3);
		circular.setCodloc(0);
		circular.setKeywords("Circular Onibus Metro Butanta");
		services.add(circular);

		// Restaurantes
		// Central, Quimica, Fisica
		Service rest = new Service();
		rest.setNomesrv("Restaurante Central");
		rest.setCodsrv(9996);
		rest.setCodctg(2);
		rest.setCodloc(7);
		rest.setDescsrv("Restaurante Central para Comunidade USP.");
		rest.setTel1("(11) 3091-3318");
		rest.setLink("http://www.usp.br/coseas/cardapio.html");
		rest.setKeywords("Restaurante bandejão COSEAS central");
		services.add(rest);

		rest = new Service();
		rest.setNomesrv("Restaurante Quimica");
		rest.setCodsrv(9995);
		rest.setCodctg(2);
		rest.setCodloc(8);
		rest.setDescsrv("Restaurante da Quimica para Comunidade USP.");
		rest.setTel1("(11) 3034-1993");
		rest.setLink("http://www.usp.br/coseas/cardapioquimica.html");
		rest.setKeywords("Restaurante bandejão quimica COSEAS");
		services.add(rest);

		rest = new Service();
		rest.setNomesrv("Restaurante Física");
		rest.setCodsrv(9994);
		rest.setCodctg(2);
		rest.setCodloc(9);
		rest.setDescsrv("Restaurante da Física para Comunidade USP.");
		rest.setTel1("(11) 3091-6710");
		rest.setLink("http://www.usp.br/coseas/cardapiofisica.html");
		rest.setKeywords("Restaurante bandejão física COSEAS");
		services.add(rest);

		rest = new Service();
		rest.setNomesrv("Restaurante FSP");
		rest.setCodsrv(9993);
		rest.setCodctg(2);
		rest.setCodloc(10);
		rest.setDescsrv("Restaurante da Faculdade de Saúde Pública para Comunidade USP.");
		rest.setTel1("(11) 3061-7785");
		rest.setLink("http://www.usp.br/coseas/");
		rest.setKeywords("Restaurante bandejão fsp faculdade de saúde pública COSEAS");
		services.add(rest);

		rest = new Service();
		rest.setNomesrv("Restaurante Clube Professores");
		rest.setCodsrv(9992);
		rest.setCodctg(2);
		rest.setCodloc(14);
		rest.setDescsrv("Restaurante Clube dos Professores para Comunidade USP.");
		rest.setTel1("(11) 3091-5005");
		rest.setTel2("(11) 3091-5010");
		rest.setLink("http://www.usp.br/coseas/carddoc.html");
		rest.setKeywords("Restaurante bandejão Clube dos Professores COSEAS");
		services.add(rest);

		rest = new Service();
		rest.setNomesrv("Restaurante COCESP");
		rest.setCodsrv(9991);
		rest.setCodctg(2);
		rest.setCodloc(11);
		rest.setDescsrv("Restaurante da COCESP para Comunidade USP.");
		rest.setLink("http://www.usp.br/coseas/cardcocesp.html");
		rest.setKeywords("Restaurante bandejão COCESP COSEAS prefeitura");
		services.add(rest);

		Collections.sort(services);

		return services;
	}

	public static ArrayList<Service> getServiceByLocal(int codlocal) {
		ArrayList<Service> services = new ArrayList<Service>();
		ArrayList<Service> servicesByLocal = new ArrayList<Service>();

		services = getServices();

		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getCodloc() == codlocal) {
				servicesByLocal.add(services.get(i));
			}
		}

		/*
		 * ArrayList<Service> services = new ArrayList<Service>(); String[] args
		 * = { String.valueOf(codlocal) }; Cursor banco =
		 * servicosUsp.dbHelper.getReadableDatabase().rawQuery(
		 * "Select * from Servico where codloc=?", args); if
		 * (banco.moveToFirst()) { do { Service s = new Service(banco.getInt(0),
		 * banco.getInt(1), banco.getInt(2), banco.getString(3),
		 * banco.getString(4), banco.getString(5), banco.getString(6),
		 * banco.getString(7), banco.getString(8), banco.getString(9),
		 * banco.getString(10)); services.add(s); } while (banco.moveToNext());
		 * }
		 */
		Collections.sort(servicesByLocal);
		return servicesByLocal;
	}

	public static Service getServiceById(int codservice) {
		Service s = new Service();
		s.setCodsrv(codservice);
		List<Service> list = getServices();
		Collections.sort(list, new Comparator<Service>() {

			public int compare(Service lhs, Service rhs) {
				return rhs.getCodsrv() - lhs.getCodsrv();
			}
		});

		int pos = Collections.binarySearch(list, s, new Comparator<Service>() {

			public int compare(Service lhs, Service rhs) {
				return rhs.getCodsrv() - lhs.getCodsrv();
			}

		});

		if (pos >= 0) {
			return list.get(pos);
		} else {
			return null;
		}
	}

	public static ArrayList<Service> getServicesByCategory(int codctg) {
		ArrayList<Service> services = new ArrayList<Service>();
		ArrayList<Service> servicesFromCat = new ArrayList<Service>();

		services = getServices();

		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getCodctg() == codctg) {
				servicesFromCat.add(services.get(i));
			}
		}

		Collections.sort(servicesFromCat);

		return servicesFromCat;
	}

	public static ArrayList<Service> getServicesByKeyword(String search) {
		ArrayList<Service> services = new ArrayList<Service>();
		ArrayList<Service> servicesByKw = new ArrayList<Service>();
		services = getServices();

		for (Service s : services) {
			if (s.getKeywords().toUpperCase().contains(search.toUpperCase())) {
				servicesByKw.add(s);
			}
		}

		Collections.sort(servicesByKw);

		return servicesByKw;
	}

	public static ArrayList<Service> getServicesByCatAndKWord(int codctg,
			String search) {
		ArrayList<Service> services = new ArrayList<Service>();
		ArrayList<Service> servicesByCatAndKeyWord = new ArrayList<Service>();

		services = getServices();

		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getCodctg() == codctg
					&& services.get(i).getKeywords().toUpperCase()
							.contains(search.toUpperCase())) {
				servicesByCatAndKeyWord.add(services.get(i));
			}
		}

		Collections.sort(servicesByCatAndKeyWord);

		return servicesByCatAndKeyWord;
	}

	public int compareTo(Service another) {
		return getNomesrv().compareTo(another.getNomesrv());
	}
}
