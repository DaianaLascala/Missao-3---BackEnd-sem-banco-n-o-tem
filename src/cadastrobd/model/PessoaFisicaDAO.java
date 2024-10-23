/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {

    
    public PessoaFisica getPessoa(int id) {
        PessoaFisica pessoa = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConectorBD.getConnection(); 
            String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pf.cpf " +
                         "FROM Pessoa p JOIN PessoaFisica pf ON p.id = pf.id WHERE p.id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                pessoa = new PessoaFisica(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cpf")
                );
            }
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmt, rs);
        }

        return pessoa;
    }

    
    public List<PessoaFisica> getPessoas() {
        List<PessoaFisica> pessoas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConectorBD.getConnection();
            String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pf.cpf " +
                         "FROM Pessoa p JOIN PessoaFisica pf ON p.id = pf.id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                PessoaFisica pessoa = new PessoaFisica(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cpf")
                );
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmt, rs);
        }

        return pessoas;
    }

    
    public boolean incluir(PessoaFisica pessoa) {
        boolean sucesso = false;
        Connection conn = null;
        PreparedStatement stmtPessoa = null;
        PreparedStatement stmtPessoaFisica = null;

        try {
            conn = ConectorBD.getConnection();
            int id = SequenceManager.getValue("pessoa_sequence"); // Obtém um novo ID usando SequenceManager

            
            String sqlPessoa = "INSERT INTO Pessoa (id, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmtPessoa = conn.prepareStatement(sqlPessoa);
            stmtPessoa.setInt(1, id);
            stmtPessoa.setString(2, pessoa.getNome());
            stmtPessoa.setString(3, pessoa.getLogradouro());
            stmtPessoa.setString(4, pessoa.getCidade());
            stmtPessoa.setString(5, pessoa.getEstado());
            stmtPessoa.setString(6, pessoa.getTelefone());
            stmtPessoa.setString(7, pessoa.getEmail());
            stmtPessoa.executeUpdate();

            
            String sqlPessoaFisica = "INSERT INTO PessoaFisica (id, cpf) VALUES (?, ?)";
            stmtPessoaFisica = conn.prepareStatement(sqlPessoaFisica);
            stmtPessoaFisica.setInt(1, id);
            stmtPessoaFisica.setString(2, pessoa.getCpf());
            stmtPessoaFisica.executeUpdate();

            sucesso = true;
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmtPessoa, stmtPessoaFisica);
        }

        return sucesso;
    }

    
    public boolean alterar(PessoaFisica pessoa) {
        boolean sucesso = false;
        Connection conn = null;
        PreparedStatement stmtPessoa = null;
        PreparedStatement stmtPessoaFisica = null;

        try {
            conn = ConectorBD.getConnection();

            
            String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id = ?";
            stmtPessoa = conn.prepareStatement(sqlPessoa);
            stmtPessoa.setString(1, pessoa.getNome());
            stmtPessoa.setString(2, pessoa.getLogradouro());
            stmtPessoa.setString(3, pessoa.getCidade());
            stmtPessoa.setString(4, pessoa.getEstado());
            stmtPessoa.setString(5, pessoa.getTelefone());
            stmtPessoa.setString(6, pessoa.getEmail());
            stmtPessoa.setInt(7, pessoa.getId());
            stmtPessoa.executeUpdate();

            
            String sqlPessoaFisica = "UPDATE PessoaFisica SET cpf = ? WHERE id = ?";
            stmtPessoaFisica = conn.prepareStatement(sqlPessoaFisica);
            stmtPessoaFisica.setString(1, pessoa.getCpf());
            stmtPessoaFisica.setInt(2, pessoa.getId());
            stmtPessoaFisica.executeUpdate();

            sucesso = true;
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmtPessoa, stmtPessoaFisica);
        }

        return sucesso;
    }

   
    public boolean excluir(int id) {
        boolean sucesso = false;
        Connection conn = null;
        PreparedStatement stmtPessoaFisica = null;
        PreparedStatement stmtPessoa = null;

        try {
            conn = ConectorBD.getConnection();

            
            String sqlPessoaFisica = "DELETE FROM PessoaFisica WHERE id = ?";
            stmtPessoaFisica = conn.prepareStatement(sqlPessoaFisica);
            stmtPessoaFisica.setInt(1, id);
            stmtPessoaFisica.executeUpdate();

            
            String sqlPessoa = "DELETE FROM Pessoa WHERE id = ?";
            stmtPessoa = conn.prepareStatement(sqlPessoa);
            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            sucesso = true;
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmtPessoaFisica, stmtPessoa);
        }

        return sucesso;
    }

    
    private void fecharRecursos(Connection conn, PreparedStatement... stmts) {
        for (PreparedStatement stmt : stmts) {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    private void fecharRecursos(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        fecharRecursos(conn, stmt);
    }
}
