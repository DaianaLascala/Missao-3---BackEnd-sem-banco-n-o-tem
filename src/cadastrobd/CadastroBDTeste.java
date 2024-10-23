/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridicaDAO;
/**
 *
 * @author Lascala
 */
public class CadastroBDTeste {
    public static void main(String[] args) {
       
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();

        
        PessoaFisica pessoaFisica = new PessoaFisica(0, "João Silva", "Rua A, 123", "São Paulo", "SP", "99999-9999", "joao@example.com", "123.456.789-00");
        boolean sucessoInclusao = pessoaFisicaDAO.incluir(pessoaFisica);
        System.out.println("Inclusão de Pessoa Física: " + (sucessoInclusao ? "Sucesso" : "Falha"));

       
        pessoaFisica.setTelefone("88888-8888");
        boolean sucessoAlteracao = pessoaFisicaDAO.alterar(pessoaFisica);
        System.out.println("Alteração de Pessoa Física: " + (sucessoAlteracao ? "Sucesso" : "Falha"));

       
        System.out.println("Lista de Pessoas Físicas:");
        for (PessoaFisica pf : pessoaFisicaDAO.getPessoas()) {
            System.out.println(pf);
        }

      
        boolean sucessoExclusao;
        sucessoExclusao = pessoaFisicaDAO.excluir(pessoaFisica.getId());
        System.out.println("Exclusão de Pessoa Física: " + (sucessoExclusao ? "Sucesso" : "Falha"));

       
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        
        PessoaJuridica pessoaJuridica = new PessoaJuridica(0, "Empresa XYZ", "Av. B, 456", "Rio de Janeiro", "RJ", "97777-7777", "empresa@example.com", "12.345.678/0001-90");
        boolean sucessoInclusaoPJ = pessoaJuridicaDAO.incluir(pessoaJuridica);
        System.out.println("Inclusão de Pessoa Jurídica: " + (sucessoInclusaoPJ ? "Sucesso" : "Falha"));

       
        pessoaJuridica.setTelefone("96666-6666");
        boolean sucessoAlteracaoPJ = pessoaJuridicaDAO.alterar(pessoaJuridica);
        System.out.println("Alteração de Pessoa Jurídica: " + (sucessoAlteracaoPJ ? "Sucesso" : "Falha"));

       
        System.out.println("Lista de Pessoas Jurídicas:");
        for (PessoaJuridica pj : pessoaJuridicaDAO.getPessoas()) {
            System.out.println(pj);
        }

       
        boolean sucessoExclusaoPJ = pessoaJuridicaDAO.excluir(pessoaJuridica.getId());
        System.out.println("Exclusão de Pessoa Jurídica: " + (sucessoExclusaoPJ ? "Sucesso" : "Falha"));
    }
}