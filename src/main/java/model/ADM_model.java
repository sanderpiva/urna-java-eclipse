package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.Conecta;

public class ADM_model {

    public List<ADM> listarResultadosValidos() throws SQLException {
        String sql = "SELECT leg.numero_voto, leg.nome_candidato, "
                + "COALESCE(SUM(v.votos_validos), 0) AS total_votos_validos "
                + "FROM legenda AS leg "
                + "LEFT JOIN votacao AS v ON v.legenda_id = leg.id "
                + "GROUP BY leg.id, leg.numero_voto, leg.nome_candidato "
                + "ORDER BY total_votos_validos DESC, leg.numero_voto ASC";
        
        List<ADM> resultados = new ArrayList<>();
        try (Connection conn = Conecta.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Passamos 0 para os brancos e nulos para respeitar o construtor de ADM
                resultados.add(new ADM(
                    rs.getString("numero_voto"), 
                    rs.getString("nome_candidato"),
                    rs.getInt("total_votos_validos"), 
                    0, 
                    0
                ));
            }
        }
        return resultados;
    }
    
    public Map<String, Integer> listarBcosNulos() throws SQLException {
        String sql = "SELECT "
                + "COALESCE(SUM(votos_brancos), 0) AS brancos, "
                + "COALESCE(SUM(votos_nulos), 0) AS nulos "
                + "FROM votacao";

        Map<String, Integer> totais = new HashMap<>();
        try (Connection conn = Conecta.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                totais.put("totalVotosBrancos", rs.getInt("brancos"));
                totais.put("totalVotosNulos", rs.getInt("nulos"));
            }
        }
        return totais;
    }
    
    public int contarVotosRegistrados() throws SQLException {
        String sql = "SELECT COUNT(*) FROM votacao";
        try (Connection conn = Conecta.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
}