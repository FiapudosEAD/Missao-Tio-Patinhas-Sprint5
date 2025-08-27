package org.example;

import org.example.dao.ClienteDAO;
import org.example.exception.DAOException;
import org.example.model.Cliente;

import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        ClienteDAO clienteDAO = new ClienteDAO();

        try {
            // Criando cliente
            Cliente cliente = new Cliente(1, "Rafael Porto", "12345678900", "rafael@email.com", "119999999");
            clienteDAO.inserir(cliente);
            System.out.println("Cliente inserido!");

            // Atualizando cliente
            cliente.setNome("Rafael Porto Annunciato");
            clienteDAO.atualizar(cliente);
            System.out.println("Cliente atualizado!");

            // Buscando cliente
            Cliente c = clienteDAO.buscarPorId(1);
            System.out.println("Cliente encontrado: " + c);

            // Listando todos
            List<Cliente> lista = clienteDAO.listar();
            lista.forEach(System.out::println);

            // Deletando cliente
            clienteDAO.deletar(1);
            System.out.println("Cliente deletado!");

        } catch (DAOException e) {
            System.err.println("Erro no banco: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
