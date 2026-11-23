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
		if (idEleitor <= 0) {
			throw new IllegalArgumentException("ID do eleitor inválido.");
		}
		this.idEleitor = idEleitor;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("O nome do eleitor é obrigatório.");
		}
		this.nome = nome.trim();
	}
	

	public Date getData() {
		return data;
	}
	
	
	public void setData(Date data) {
		if (data == null) {
			throw new IllegalArgumentException("A data de nascimento é obrigatória.");
		}
		this.data = data;
	}
	
	
	public String getZona_eleitoral() {
		return zona_eleitoral;
	}
	
	public void setZona_eleitoral(String zona_eleitoral) {
		if (zona_eleitoral == null || zona_eleitoral.trim().isEmpty()) {
			throw new IllegalArgumentException("A zona eleitoral é obrigatória.");
		}
		this.zona_eleitoral = zona_eleitoral.trim();
	}
	
	public String getMunicipio() {
		return municipio;
	}
	
	
	public void setMunicipio(String municipio) {
		if (municipio == null || municipio.trim().isEmpty()) {
			throw new IllegalArgumentException("O município é obrigatório.");
		}
		this.municipio = municipio.trim();
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		if (cpf == null || cpf.trim().isEmpty()) {
			throw new IllegalArgumentException("O CPF é obrigatório.");
		}
		String cpfLimpo = cpf.trim();
		if (!cpfLimpo.matches("\\d{11}")) {
			throw new IllegalArgumentException("O CPF deve conter exatamente 11 dígitos numéricos.");
		}
		this.cpf = cpfLimpo;
	}
	
	public String getSecao() {
		return secao;
	}
	
	
	public void setSecao(String secao) {
		if (secao == null || secao.trim().isEmpty()) {
			throw new IllegalArgumentException("A seção eleitoral é obrigatória.");
		}
		this.secao = secao.trim();
	}
	
	
	public String getFiliacao() {
		return filiacao;
	}
	
	
	public void setFiliacao(String filiacao) {
		if (filiacao == null || filiacao.trim().isEmpty()) {
			throw new IllegalArgumentException("A filiação é obrigatória.");
		}
		this.filiacao = filiacao.trim();
	}
	
}
