package org.example.dao;

import org.example.factory.ConnectionFactory;
import org.example.exception.DAOException;
import org.example.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public Long insert(Cliente cliente) throws DAOException {
        String sql = "INSERT INTO TB_CLIENTE (NOME, EMAIL, CPF, TELEFONE) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_CLIENTE"})) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getTelefone());

            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new DAOException("Erro ao obter ID gerado");
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir cliente", e);
        }
    }

    public int update(Cliente cliente) throws DAOException {
        String sql = "UPDATE TB_CLIENTE SET NOME = ?, CPF = ?, EMAIL = ?, TELEFONE = ? WHERE ID_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setInt(5, cliente.getId());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar cliente", e);
        }
    }

    public int delete(Long id) throws DAOException {
        String sql = "DELETE FROM TB_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar cliente", e);
        }
    }

    public List<Cliente> findAll() throws DAOException {
        String sql = "SELECT * FROM TB_CLIENTE ORDER BY NOME";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("ID_CLIENTE"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setCpf(rs.getString("CPF"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefone(rs.getString("TELEFONE"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar clientes", e);
        }
        return clientes;
    }

    public Cliente findById(Long id) throws DAOException {
        String sql = "SELECT * FROM TB_CLIENTE WHERE ID_CLIENTE = ?";
        Cliente cliente = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("ID_CLIENTE"));
                    cliente.setNome(rs.getString("NOME"));
                    cliente.setCpf(rs.getString("CPF"));
                    cliente.setEmail(rs.getString("EMAIL"));
                    cliente.setTelefone(rs.getString("TELEFONE"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar cliente por ID", e);
        }

        return cliente;
    }
    
    // Métodos mantidos para compatibilidade com versões anteriores
    public void inserir(Cliente cliente) throws DAOException {
        insert(cliente);
    }

    public void atualizar(Cliente cliente) throws DAOException {
        update(cliente);
    }

    public void deletar(int id) throws DAOException {
        delete(Long.valueOf(id));
    }

    public List<Cliente> listar() throws DAOException {
        return findAll();
    }

    public Cliente buscarPorId(int id) throws DAOException {
        return findById(Long.valueOf(id));
    }
}
