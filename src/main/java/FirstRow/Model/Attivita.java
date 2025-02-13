package FirstRow.Model;

import java.sql.Date;

public class Attivita {

	private String nome;
	private String categoria;
	private Date scadenza;
	private String priorita;
	
	public Attivita(String nome,String categoria, Date scadenza, String priorita ) {
		this.nome = nome;
		this.categoria = categoria;
		this.scadenza = scadenza;
		this.priorita = priorita;
	}
	
	public String getNome() {
		return this.nome;
	}
	public String getCategoria() {
		return this.categoria;
	}
	public Date getScadenza() {
		return this.scadenza;
	}
	public String getPriorita() {
		return this.priorita;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}

	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}
}
