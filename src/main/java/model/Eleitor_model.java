package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import config.Conecta;

public class Eleitor_model {

	public boolean eleitorPodeVotar(int idEleitor) throws SQLException {
		String sql = "SELECT idEleitor FROM eleitor WHERE idEleitor = ? AND votou = 0";
		try (Connection conn = Conecta.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idEleitor);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	public int autenticarEleitor(String nome, String data, String zona, String municipio, String cpf,
	        String secao, String filiacao) {
	    
	    String sql = "SELECT idEleitor FROM eleitor WHERE nome = ? AND data = ? AND zona = ? "
	            + "AND municipio = ? AND cpf = ? AND secao = ? AND filiacao = ? AND votou = 0 LIMIT 1";

	    try (Connection conn = Conecta.getConexao(); 
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, nome != null ? nome.trim() : "");
	        stmt.setString(2, data != null ? data.trim() : "");
	        stmt.setString(3, zona != null ? zona.trim() : "");
	        stmt.setString(4, municipio != null ? municipio.trim() : "");
	        stmt.setString(5, cpf != null ? cpf.trim() : "");
	        stmt.setString(6, secao != null ? secao.trim() : "");
	        stmt.setString(7, filiacao != null ? filiacao.trim() : "");

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                System.out.println("[DEBUG LOG] Eleitor encontrado! ID: " + rs.getInt("idEleitor"));
	                return rs.getInt("idEleitor");
	            } else {
	                System.out.println("[DEBUG LOG] Nenhum eleitor encontrado com esses dados exatos.");
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("[DEBUG LOG] ERRO SQL ao autenticar eleitor:");
	        e.printStackTrace();
	    }
	    return -1;
	}
	
	public Map<String, String> registrarVoto(String numeroVoto, int idEleitor) throws SQLException {
		
		String sqlEleitor = "SELECT idEleitor FROM eleitor WHERE idEleitor = ? AND votou = 0 FOR UPDATE";
		String sqlLegenda = "SELECT id, nome_candidato FROM legenda WHERE numero_voto = ? FOR UPDATE";
		
		String sqlInserirVoto = "INSERT INTO votacao "
				+ "(eleitor_idEleitor, legenda_id, votos_validos, votos_brancos, votos_nulos) VALUES (?, ?, ?, ?, ?)";
		String sqlMarcarEleitor = "UPDATE eleitor SET votou = 1 WHERE idEleitor = ? AND votou = 0";

		try (Connection conn = Conecta.getConexao()) {
			conn.setAutoCommit(false);
			try {
				try (PreparedStatement stmt = conn.prepareStatement(sqlEleitor)) {
					stmt.setInt(1, idEleitor);
					try (ResultSet rs = stmt.executeQuery()) {
						if (!rs.next()) {
							conn.rollback();
							return null;
						}
					}
				}

				Integer idLegenda = null;
				String nomeCandidato = null;
				if (!"branco".equals(numeroVoto)) {
					try (PreparedStatement stmt = conn.prepareStatement(sqlLegenda)) {
						stmt.setInt(1, Integer.parseInt(numeroVoto));
						try (ResultSet rs = stmt.executeQuery()) {
							if (rs.next()) {
								idLegenda = rs.getInt("id");
								nomeCandidato = rs.getString("nome_candidato");
							}
						}
					}
				}

				String tipoVoto = "branco".equals(numeroVoto) ? "branco"
						: idLegenda == null ? "nulo" : "valido";
				try (PreparedStatement stmt = conn.prepareStatement(sqlInserirVoto)) {
					stmt.setInt(1, idEleitor);
					if (idLegenda == null) {
						stmt.setNull(2, Types.INTEGER);
					} else {
						stmt.setInt(2, idLegenda);
					}
					stmt.setInt(3, "valido".equals(tipoVoto) ? 1 : 0);
					stmt.setInt(4, "branco".equals(tipoVoto) ? 1 : 0);
					stmt.setInt(5, "nulo".equals(tipoVoto) ? 1 : 0);
					
					stmt.executeUpdate();
				}

				try (PreparedStatement stmt = conn.prepareStatement(sqlMarcarEleitor)) {
					stmt.setInt(1, idEleitor);
					if (stmt.executeUpdate() != 1) {
						conn.rollback();
						return null;
					}
				}

				conn.commit();
				Map<String, String> resultado = new HashMap<>();
				resultado.put("tipo", tipoVoto);
				if (nomeCandidato != null) {
					resultado.put("candidato", nomeCandidato);
				}
				return resultado;
			} catch (SQLException | RuntimeException e) {
				conn.rollback();
				throw e;
			}
		}
	}
}
