package com.java.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.conexao.ConnectionFactory;
import com.java.modelo.Usuario;
import com.java.modeloEspecializado.AlteraSenhaUsuario;

public class UsuarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection con;
	
	public List<Usuario> listarTodos() throws SQLException {
		
		List<Usuario> lista = new ArrayList<Usuario>();
		
		String sql = "select id, login, nomeCompleto, situacao, senha, admin from usuario order by nomeCompleto desc ";
		
		con = new ConnectionFactory().getConnection();
		
		PreparedStatement stmt = con.prepareStatement(sql); 
				
		ResultSet rs = stmt.executeQuery();
		
		Usuario usuario = null;
		
		while (rs.next()) {
			
			usuario = new Usuario();
			
			usuario.setId(rs.getLong("id"));
			usuario.setLogin(rs.getString("login"));
			usuario.setNomeCompleto(rs.getString("nomeCompleto"));
			usuario.setSituacao(rs.getString("situacao"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setAdmin(rs.getString("admin"));
			
			lista.add(usuario);
			
		}
		
		stmt.close();
		con.close();
		
		return lista;
	}
	
	public Usuario retornarUsuarioPorID(Long id) throws SQLException {

		String sql = "select id, login, nomeCompleto, situacao, senha, admin from usuario where id = ? ";
		
		con = new ConnectionFactory().getConnection();
		
		PreparedStatement stmt = con.prepareStatement(sql); 
		stmt.setLong(1, id);
		
		ResultSet rs = stmt.executeQuery();
		
		Usuario usuario = null;
		
		while (rs.next()) {
			
			usuario = new Usuario();
			
			usuario.setId(rs.getLong("id"));
			usuario.setLogin(rs.getString("login"));
			usuario.setNomeCompleto(rs.getString("nomeCompleto"));
			usuario.setSituacao(rs.getString("situacao"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setAdmin(rs.getString("admin"));
		}
		
		stmt.close();
		con.close();
		
		return usuario;
		
	}
	
	public Usuario retornaUsuarioPorLoginSenha(String login, String senha) throws SQLException {
		
		String sql = "select id, login, nomeCompleto, situacao, senha, admin from usuario where login = ? and senha = ? ";
		
		con = new ConnectionFactory().getConnection();
		
		PreparedStatement stmt = con.prepareStatement(sql); 
		stmt.setString(1, login);
		stmt.setString(2, senha);
		
		ResultSet rs = stmt.executeQuery();
		
		Usuario usuario = null;
		
		while (rs.next()) {
			
			usuario = new Usuario();
			
			usuario.setId(rs.getLong("id"));
			usuario.setLogin(rs.getString("login"));
			usuario.setNomeCompleto(rs.getString("nomeCompleto"));
			usuario.setSituacao(rs.getString("situacao"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setAdmin(rs.getString("admin"));
		}
		
		stmt.close();
		con.close();
		
		return usuario;

	}
	
	public Usuario retornaUsuarioPorLogin(String login) throws SQLException {
		
		String sql = "select id, login, nomeCompleto, situacao, senha, admin from usuario where login = ? and senha ? ";
		
		con = new ConnectionFactory().getConnection();
		
		PreparedStatement stmt = con.prepareStatement(sql); 
		stmt.setString(1, login);
		
		ResultSet rs = stmt.executeQuery();
		
		Usuario usuario = null;
		
		while (rs.next()) {
			
			usuario = new Usuario();
			
			usuario.setId(rs.getLong("id"));
			usuario.setLogin(rs.getString("login"));
			usuario.setNomeCompleto(rs.getString("nomeCompleto"));
			usuario.setSituacao(rs.getString("situacao"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setAdmin(rs.getString("admin"));
		}
		
		stmt.close();
		con.close();
		
		return usuario;

	}

	public void incluir(Usuario usuario) throws SQLException {
		
		con = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO usuario (login, nomeCompleto, situacao, senha, admin) " + "VALUES (?,?,?,?,?)";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, usuario.getLogin());
		stmt.setString(2, usuario.getNomeCompleto());
		stmt.setString(3, usuario.getSituacao());
		stmt.setString(4, usuario.getSenha());
		stmt.setString(5, usuario.getAdmin());

		stmt.execute();
		stmt.close();
		con.close();
		
	}
	
	public void excluir(Usuario usuario) throws SQLException {
		
		con = new ConnectionFactory().getConnection();

		String sql = "delete from usuario where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, usuario.getId());

		stmt.execute();
		stmt.close();
		con.close();
		
	}
	
	public void alterar(Usuario usuario) throws SQLException {
		
		con = new ConnectionFactory().getConnection();

		String sql = "update usuario set login = ?, nomeCompleto = ?, situacao = ?, admin = ? where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, usuario.getLogin());
		stmt.setString(2, usuario.getNomeCompleto());
		stmt.setString(3, usuario.getSituacao());	
		stmt.setString(4, usuario.getAdmin());
		stmt.setLong(5, usuario.getId());

		stmt.execute();
		stmt.close();
		con.close();
		
	}
	
	public void alterarSenha(AlteraSenhaUsuario usuario) throws SQLException {
		
		con = new ConnectionFactory().getConnection();

		String sql = "update usuario set senha = ? where login = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, usuario.getSenhaCriptografada());
		stmt.setString(2, usuario.getLogin());

		stmt.execute();
		stmt.close();
		con.close();
		
	}

}
