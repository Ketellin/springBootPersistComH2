package br.edu.pucgoias.domain.repositorio;

import br.edu.pucgoias.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//O spring entende que a classe faz operações com o banco de dados e também traduzirá as exception geradas pelo banco
//Com essa anotaçao o spring acionará a feature ExeptionTranslate
@Repository
public class ClientesDao {

    //Anotação para que o SpringBoot injet uma instância da classe JdbcTemplate
    @Autowired
    /*
    A classe JdbcTemplate trabalha com sql nativo e por isso precisaremos de scripts sql, já que
    ele é uma configuração jdbc comum.
     */
    private JdbcTemplate jdbcTemplate;

    /*
    Obj INSERT conterá o script sql de inserção de dados no banco.
    O campo a ser inserido é somente nome, já que o id é auto-incrementado
     */
    private static String INSERT = "insert into cliente (nome) values (?)";

    private static String SELECT_ALL = "select * from cliente";

    private static String SELECT_POR_NOME = "select * from cliente where nome like ?";

    private static String UPDATE = "update cliente set nome = ? where id = ?";

    private static String DELETE = "delete from cliente where id = ?";

    public Cliente Salvar(Cliente cliente){
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()} );
        return cliente;
    }

    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }

    public void apagar(Cliente cliente){
        apagar(cliente.getId());
    }

    public void apagar(Integer id){
        jdbcTemplate.update(DELETE, new Object[]{id});
    }

    public List<Cliente> obterTodos(){
        RowMapper<Cliente> rowMappers = getRowMappers();
        return jdbcTemplate.query(SELECT_ALL,rowMappers);
    }

    private RowMapper<Cliente> getRowMappers() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"));
            }
        };
    }

    public List<Cliente> buscarPorNome(String nome){
        return jdbcTemplate.query(SELECT_POR_NOME,new Object[]{"%" + nome + "%"}, getRowMappers());
    }
}
