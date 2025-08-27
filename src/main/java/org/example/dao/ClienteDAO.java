package org.example.dao;

import org.example.factory.ConnectionFactory;
import org.example.exception.DAOException;
import org.example.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public void inserir(Cliente cliente) throws DAOException {
        String sql = "INSERT INTO TB_CLIENTE (ID_CLIENTE, NOME, CPF, EMAIL, TELEFONE) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getId());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir cliente", e);
        }
    }

    public void atualizar(Cliente cliente) throws DAOException {
        String sql = "UPDATE TB_CLIENTE SET NOME = ?, CPF = ?, EMAIL = ?, TELEFONE = ? WHERE ID_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setInt(5, cliente.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar cliente", e);
        }
    }

    public void deletar(int id) throws DAOException {
        String sql = "DELETE FROM TB_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar cliente", e);
        }
    }

    public List<Cliente> listar() throws DAOException {
        String sql = "SELECT * FROM TB_CLIENTE";
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

    public Cliente buscarPorId(int id) throws DAOException {
        String sql = "SELECT * FROM TB_CLIENTE WHERE ID_CLIENTE = ?";
        Cliente cliente = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                            rs.getInt("ID_CLIENTE"),
                            rs.getString("NOME"),
                            rs.getString("CPF"),
                            rs.getString("EMAIL"),
                            rs.getString("TELEFONE")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar cliente por ID", e);
        }

        return cliente;
    }
}
