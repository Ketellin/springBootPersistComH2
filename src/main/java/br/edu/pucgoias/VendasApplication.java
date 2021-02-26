package br.edu.pucgoias;

import br.edu.pucgoias.domain.entity.Cliente;
import br.edu.pucgoias.domain.repositorio.ClientesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClientesDao clientesDao){
        return args -> {

            System.out.println("***********SALVANDO CLIENTES");
            clientesDao.Salvar(new Cliente(null,"LUDMILLA REIS PINHEIRO"));

            clientesDao.Salvar(new Cliente(null,"KETELLIN FREITAS NASCIMENTO"));

            List<Cliente> todosClientes = clientesDao.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("***********ATUALIZANDO CLIENTES");
            todosClientes.forEach(cliente -> {
                cliente.setNome(cliente.getNome() + " atualizado.");
                clientesDao.atualizar(cliente);
            });

            todosClientes = clientesDao.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("***********CONSULTANDO CLIENTES POR NOME");
            clientesDao.buscarPorNome("REIS").forEach(System.out::println);

            /*System.out.println("***********DELETAR TODOS");
            clientesDao.obterTodos().forEach(c -> {
                clientesDao.apagar(c);
            });*/
            todosClientes = clientesDao.obterTodos();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado!!!");
            }
            else{
                todosClientes.forEach(System.out::println);
            }

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
