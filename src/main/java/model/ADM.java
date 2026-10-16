package model;

public class ADM {

	private static final String NOME_ADM_PADRAO = "ADM";
	private static final String SENHA_ADM_PADRAO = "1234";

	private final String numero_voto;
	private final String nome_candidato;
	private final int total_votos_validos;
	private final int total_votos_brancos;
	private final int total_votos_nulos;

	public ADM(String numeroVoto, String nomeCandidato, int totalVotosValidos, int totalVotosBrancos, int totalVotosNulos) {
		this.numero_voto = numeroVoto;
		this.nome_candidato = nomeCandidato;
		this.total_votos_validos = totalVotosValidos;
		this.total_votos_brancos = totalVotosBrancos;
		this.total_votos_nulos = totalVotosNulos;
	}

	public static String getNome_adm() {
		return System.getenv().getOrDefault("URNA_ADMIN_NOME", NOME_ADM_PADRAO);
	}
	public static String getSenha_adm() {
		return System.getenv().getOrDefault("URNA_ADMIN_SENHA", SENHA_ADM_PADRAO);
	}
	public String getNumero_voto() {
		return numero_voto;
	}
	public String getNome_candidato() {
		return nome_candidato;
	}
	public int getTotal_votos_validos() {
		return total_votos_validos;
	}

	public int getTotal_votos_brancos() {
		return total_votos_brancos;
	}

	public int getTotal_votos_nulos() {
		return total_votos_nulos;
	}
	
}

