package model;

import java.sql.Date;

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
	
}
