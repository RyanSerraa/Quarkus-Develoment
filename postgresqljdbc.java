import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class postgresqljdbc {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5433/postgres";
        String user = "postgres";
        String password = "mysecretpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("org.postgresql.Driver");
            System.out.println("Conectado ao banco de dados com sucesso!");

            try (Statement stm = conn.createStatement()) {
                consulta(stm);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL não encontrado.");
            e.printStackTrace();
        }
    }

    public static void consulta(Statement stm) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite \n 1- Para consultar na tabela de pessoas \n 2- Para Pedidos \n 3- Para Produtos ");
        int tableChoice = sc.nextInt();
        sc.nextLine();  // Consumir nova linha
        System.out.println("Digite o comando SQL: ");
        String sql = sc.nextLine();

        try (ResultSet result = stm.executeQuery(sql)) {
            while (result.next()) {
                switch (tableChoice) {
                    case 1:
                        printPessoas(result, sql);
                        break;
                    case 2:
                        printPedidos(result, sql);
                        break;
                    case 3:
                        printProdutos(result, sql);
                        break;
                    default:
                        System.out.println("Escolha inválida.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sc.close();
    }

    private static void printPessoas(ResultSet result, String sql) throws SQLException {
        if (sql.contains("*") || (sql.contains("id") && sql.contains("nome"))) {
            System.out.println("id: " + result.getInt("id") + " , nome: " + result.getString("nome"));
        } else if (sql.contains("id")) {
            System.out.println("id: " + result.getInt("id"));
        } else if(sql.contains("nome")){
            System.out.println(result.getString("nome"));
        }
    }

    private static void printPedidos(ResultSet result, String sql) throws SQLException {
        if (sql.contains("*") || (sql.contains("id") && sql.contains("id_pessoa") && sql.contains("id_produto") && sql.contains("qtd") && sql.contains("pedidos_fechados"))) {
            System.out.println("id: " + result.getInt("id") + " , id_pessoa: " + result.getString("id_pessoa") + ", id_produto: " + result.getInt("id_produto") + ", qtd: " + result.getInt("qtd") + ", pedidos_fechados: " + result.getString("pedidos_fechados"));
        } else if (sql.contains("id") && sql.contains("id_pessoa") && sql.contains("id_produto") && sql.contains("qtd")) {
            System.out.println("id: " + result.getInt("id") + " , id_pessoa: " + result.getString("id_pessoa") + ", id_produto: " + result.getInt("id_produto") + ", qtd: " + result.getInt("qtd"));
        } else if (sql.contains("id") && sql.contains("id_pessoa") && sql.contains("id_produto")) {
            System.out.println("id: " + result.getInt("id") + " , id_pessoa: " + result.getString("id_pessoa") + ", id_produto: " + result.getInt("id_produto"));
        } else if (sql.contains("id") && sql.contains("id_pessoa")) {
            System.out.println("id: " + result.getInt("id") + " , id_pessoa: " + result.getString("id_pessoa"));
        } else if (sql.contains("id")) {
            System.out.println("id: " + result.getInt("id"));
        } else if (sql.contains("id_pessoa")) {
            System.out.println("id_pessoa: " + result.getInt("id_pessoa"));
        } else if (sql.contains("id_produto")) {
            System.out.println("id_produto: " + result.getInt("id_produto"));
        } else if (sql.contains("qtd")) {
            System.out.println("qtd: " + result.getInt("qtd"));
        } else if (sql.contains("pedidos_fechados")) {
            System.out.println("pedidos_fechados: " + result.getString("pedidos_fechados"));
        }
    }

    private static void printProdutos(ResultSet result, String sql) throws SQLException {
        if (sql.contains("*") || (sql.contains("id") && sql.contains("nome") && sql.contains("preco") && sql.contains("descricao") && sql.contains("qtd"))) {
            System.out.println("id: " + result.getInt("id") + " , nome: " + result.getString("nome") + ", preco: " + result.getFloat("preco") + ", descricao: " + result.getString("descricao") + ", qtd: " + result.getInt("qtd"));
        } else if (sql.contains("id") && sql.contains("nome") && sql.contains("preco") && sql.contains("descricao")) {
            System.out.println("id: " + result.getInt("id") + " , nome: " + result.getString("nome") + ", preco: " + result.getFloat("preco") + ", descricao: " + result.getString("descricao"));
        } else if (sql.contains("id") && sql.contains("nome") && sql.contains("preco")) {
            System.out.println("id: " + result.getInt("id") + " , nome: " + result.getString("nome") + ", preco: " + result.getFloat("preco"));
        } else if (sql.contains("id") && sql.contains("nome")) {
            System.out.println("id: " + result.getInt("id") + " , nome: " + result.getString("nome"));
        } else if (sql.contains("id")) {
            System.out.println("id: " + result.getInt("id"));
        } else if (sql.contains("nome")) {
            System.out.println("nome: " + result.getString("nome"));
        } else if (sql.contains("preco")) {
            System.out.println("preco: " + result.getFloat("preco"));
        } else if (sql.contains("descricao")) {
            System.out.println("descricao: " + result.getString("descricao"));
        } else if (sql.contains("qtd")) {
            System.out.println("qtd: " + result.getInt("qtd"));
        }
    }
}
