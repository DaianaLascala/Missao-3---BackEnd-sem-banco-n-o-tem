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

public class PessoaJuridicaDAO {

    
    public PessoaJuridica getPessoa(int id) {
        PessoaJuridica pessoa = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConectorBD.getConnection(); 
            String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pj.cnpj " +
                         "FROM Pessoa p JOIN PessoaJuridica pj ON p.id = pj.id WHERE p.id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                pessoa = new PessoaJuridica(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cnpj")
                );
            }
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmt, rs);
        }

        return pessoa;
    }

    
    public List<PessoaJuridica> getPessoas() {
        List<PessoaJuridica> pessoas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConectorBD.getConnection();
            String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pj.cnpj " +
                         "FROM Pessoa p JOIN PessoaJuridica pj ON p.id = pj.id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                PessoaJuridica pessoa = new PessoaJuridica(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cnpj")
                );
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmt, rs);
        }

        return pessoas;
    }

    
    public boolean incluir(PessoaJuridica pessoa) {
        boolean sucesso = false;
        Connection conn = null;
        PreparedStatement stmtPessoa = null;
        PreparedStatement stmtPessoaJuridica = null;

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

            
            String sqlPessoaJuridica = "INSERT INTO PessoaJuridica (id, cnpj) VALUES (?, ?)";
            stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
            stmtPessoaJuridica.setInt(1, id);
            stmtPessoaJuridica.setString(2, pessoa.getCnpj());
            stmtPessoaJuridica.executeUpdate();

            sucesso = true;
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmtPessoa, stmtPessoaJuridica);
        }

        return sucesso;
    }

    
    public boolean alterar(PessoaJuridica pessoa) {
        boolean sucesso = false;
        Connection conn = null;
        PreparedStatement stmtPessoa = null;
        PreparedStatement stmtPessoaJuridica = null;

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

            
            String sqlPessoaJuridica = "UPDATE PessoaJuridica SET cnpj = ? WHERE id = ?";
            stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
            stmtPessoaJuridica.setString(1, pessoa.getCnpj());
            stmtPessoaJuridica.setInt(2, pessoa.getId());
            stmtPessoaJuridica.executeUpdate();

            sucesso = true;
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmtPessoa, stmtPessoaJuridica);
        }

        return sucesso;
    }

    
    public boolean excluir(int id) {
        boolean sucesso = false;
        Connection conn = null;
        PreparedStatement stmtPessoaJuridica = null;
        PreparedStatement stmtPessoa = null;

        try {
            conn = ConectorBD.getConnection();

            
            String sqlPessoaJuridica = "DELETE FROM PessoaJuridica WHERE id = ?";
            stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
            stmtPessoaJuridica.setInt(1, id);
            stmtPessoaJuridica.executeUpdate();

            
            String sqlPessoa = "DELETE FROM Pessoa WHERE id = ?";
            stmtPessoa = conn.prepareStatement(sqlPessoa);
            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            sucesso = true;
        } catch (SQLException e) {
        } finally {
            fecharRecursos(conn, stmtPessoaJuridica, stmtPessoa);
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