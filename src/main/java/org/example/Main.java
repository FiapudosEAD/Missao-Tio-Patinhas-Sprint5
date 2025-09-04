package org.example;

import org.example.dao.ClienteDAO;
import org.example.model.Cliente;

public class Main {
    public static void main(String[] args) {
        try {
            Long id = testarInsert();   // CREATE
            testarSelect(id);           // READ (by id)
            testarUpdate(id);           // UPDATE
            testarSelect(id);           // READ (after update)
            testarLista();              // READ all
            testarDelete(id);           // DELETE
            testarLista();              // READ all after delete
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Long testarInsert() throws Exception {
        ClienteDAO dao = new ClienteDAO();
        Long idGerado = dao.insert(new Cliente("Felipe Teste", "felipe@exemplo.com"));
        System.out.println("INSERT ok | id = " + idGerado);
        return idGerado;
    }

    static void testarSelect(Long id) throws Exception {
        ClienteDAO dao = new ClienteDAO();
        Cliente c = dao.findById(id);
        System.out.println("SELECT by id: " + (c == null ? "não encontrado"
                : c.getId() + " | " + c.getNome() + " | " + c.getEmail()));
    }

    static void testarUpdate(Long id) throws Exception {
        ClienteDAO dao = new ClienteDAO();
        Cliente c = dao.findById(id);
        if (c != null) {
            c.setNome("Felipe Atualizado");
            c.setEmail("felipe.atualizado@exemplo.com");
            int linhas = dao.update(c);
            System.out.println("UPDATE linhas afetadas: " + linhas);
        } else {
            System.out.println("UPDATE: registro não encontrado");
        }
    }

    static void testarDelete(Long id) throws Exception {
        ClienteDAO dao = new ClienteDAO();
        int linhas = dao.delete(id);
        System.out.println("DELETE linhas afetadas: " + linhas);
    }

    static void testarLista() throws Exception {
        ClienteDAO dao = new ClienteDAO();
        System.out.println("SELECT *:");
        for (Cliente c : dao.findAll()) {
            System.out.println(c.getId() + " | " + c.getNome() + " | " + c.getEmail());
        }
    }
}
