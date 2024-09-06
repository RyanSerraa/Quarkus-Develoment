package org.acme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DatabaseService {
    @Inject
    DataSource dataSource;

    public String consulta(String sql) {
        StringBuilder resultString = new StringBuilder();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet result = stm.executeQuery(sql)) {

            if (!result.isBeforeFirst()) { // Verifica se o ResultSet está vazio
                return "No data found.";
            }

            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (result.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = result.getString(i);
                    resultString.append(columnName).append(": ").append(columnValue);
                    if (i < columnCount) {
                        resultString.append(", ");
                    }
                }
                resultString.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao executar a consulta: " + e.getMessage();
        }

        if (resultString.length() > 0) {
            return resultString.toString();
        }

        return "Pesquisa inválida";
    }

    public String executarComando(String sql) {
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement()) {
            //int affectedRows = stm.executeUpdate(sql);
             PreparedStatement ps = conn.prepareStatement(sql);
            return "Comando executado com sucesso. Linhas afetadas: " + ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao executar o comando: " + e.getMessage();
        }
    }
}
