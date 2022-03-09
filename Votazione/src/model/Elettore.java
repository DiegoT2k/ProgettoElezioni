package model;

public class Elettore {

	public String nome;
	public String cognome;
	public String codfiscale;
	public String datan;
	public String comunen;
	public String nazionen;
	public String sesso;
	public int voto;
	
	public Elettore(String nome, String cognome, String cod, String data, String comunen, String nazionen, String sesso) {
		this.nome = nome;
		this.cognome = cognome;
		this.codfiscale = cod;
		this.datan = data;
		this.comunen = comunen;
		this.nazionen = nazionen;
		this.sesso = sesso;
		this.voto = 0;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodfiscale() {
		return codfiscale;
	}

	public void setCodfiscale(String codfiscale) {
		this.codfiscale = codfiscale;
	}

	public String getDatan() {
		return datan;
	}

	public void setDatan(String datan) {
		this.datan = datan;
	}

	public String getComunen() {
		return comunen;
	}

	public void setComunen(String comunen) {
		this.comunen = comunen;
	}

	public String getNazionen() {
		return nazionen;
	}

	public void setNazionen(String nazionen) {
		this.nazionen = nazionen;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

}
