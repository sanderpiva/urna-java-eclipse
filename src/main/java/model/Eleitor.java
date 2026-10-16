package model;

import java.sql.Date;

public class Eleitor {
	
	private int idEleitor;
	private String nome;
	private Date data;
	private String zona_eleitoral;
	private String municipio;
	private String cpf; 
	private String secao;
	private String filiacao;
	
	
	public int getIdEleitor() {
		return idEleitor;
	}
	public void setIdEleitor(int idEleitor) {
		this.idEleitor = idEleitor;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getZona_eleitoral() {
		return zona_eleitoral;
	}
	public void setZona_eleitoral(String zona_eleitoral) {
		this.zona_eleitoral = zona_eleitoral;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getSecao() {
		return secao;
	}
	public void setSecao(String secao) {
		this.secao = secao;
	}
	public String getFiliacao() {
		return filiacao;
	}
	public void setFiliacao(String filiacao) {
		this.filiacao = filiacao;
	}
	
}
