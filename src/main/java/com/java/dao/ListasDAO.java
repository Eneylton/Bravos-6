package com.java.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.conexao.ConnectionFactory;
import com.java.modelo.Aluno;
import com.java.modelo.Caixa;
import com.java.modelo.Categoria;
import com.java.modelo.Financeiro;

public class ListasDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection con;
	
	

	public List<Financeiro> retornoDespesa() throws SQLException {

		List<Financeiro> lista = new ArrayList<Financeiro>();

		String sql = "SELECT " + "fin.id, " + "al.nome as idNome," + "sum(fin.valor) as total, " + "fin.data, "
				+ "fin.valor, " + "fin.flag, " + "fin.qtd, " + "fin.tipo as idTipo, " + "fin.servico_id AS servico, "
				+ "fin.aluno_id AS idAluno, " + "fin.descricao, " + "fin.categoria_id AS idCat, "
				+ "fin.caixa_id AS caixa, " + "fin.pagamento_id AS pagamento, " + "CASE "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 5 THEN '05 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 4 THEN '04 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 3 THEN '03 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 2 THEN '02 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 1 THEN 'FALTA UM DIA PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 0 THEN 'DIA DO PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) <= 0 THEN 'ATRASADO' " + " ELSE 'AQUARDANDO PAGAMENTO' "
				+ " END AS dia, " + "c.descricao AS categoria, CASE " + " WHEN fin.tipo = '1' THEN 'Receita' "
				+ " WHEN fin.tipo = '2' THEN 'Despesa' " + " END " + " AS idDescricao, CASE "
				+ " WHEN fin.flag = '1' THEN 'Pago' " + " WHEN fin.flag = '2' THEN 'Em Aberto' " + " END "
				+ " AS FlagDesc " + "FROM " + " financeiro AS fin " + "INNER JOIN "
				+ " categoria AS c ON (fin.categoria_id = c.id) INNER JOIN "
				+ " aluno AS al ON (fin.aluno_id = al.id)  WHERE fin.flag ='1' AND fin.tipo='2' "
				+ " group by fin.id ORDER BY fin.data ASC";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Financeiro financeiro = null;

		while (rs.next()) {

			financeiro = new Financeiro();

			financeiro.setId(rs.getLong("id"));
			financeiro.setData(rs.getDate("data"));
			financeiro.setValor(rs.getDouble("valor"));
			financeiro.setFlag(rs.getInt("flag"));
			financeiro.setQtd(rs.getInt("qtd"));
			financeiro.setTipo(rs.getInt("idTipo"));
			financeiro.setDescricao(rs.getString("descricao"));
			financeiro.setDia(rs.getString("dia"));
			financeiro.setIdTipo(rs.getString("idDescricao"));
			financeiro.setIdFlag(rs.getString("FlagDesc"));
			financeiro.setValorTotal(rs.getDouble("total"));
			financeiro.setIdFormaPagamento(rs.getInt("pagamento"));

			Categoria cat = new Categoria();

			cat.setId(rs.getLong("idCat"));
			cat.setDescricao(rs.getString("categoria"));
			financeiro.setCategoria(cat);

			Aluno al = new Aluno();

			al.setId(rs.getLong("idAluno"));
			al.setNome(rs.getString("idNome"));
			financeiro.setAluno(al);
			
			Caixa cx = new Caixa();
			cx.setId(rs.getLong("caixa"));
			financeiro.setCaixa(cx);

			lista.add(financeiro);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	public double getTotalDespesa() throws SQLException {
		con = new ConnectionFactory().getConnection();

		double total = 0;

		String sql = "SELECT sum(valor) as total FROM financeiro as fin where flag='1' AND fin.tipo='2' ";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			if (rs.getDouble("total") != 0) {
				total = rs.getDouble("total");
			} else {
				total = 0;
			}
		}

		rs.close();

		stmt.close();
		con.close();

		return total;
	}
	
	
	public List<Financeiro> contasAreceber() throws SQLException {

		List<Financeiro> lista = new ArrayList<Financeiro>();

		String sql = "SELECT " + "fin.id, " + "al.nome as idNome," + "sum(fin.valor) as total, " + "fin.data, "
				+ "fin.valor, " + "fin.flag, " + "fin.qtd, " + "fin.tipo as idTipo, " + "fin.servico_id AS servico, "
				+ "fin.aluno_id AS idAluno, " + "fin.descricao, " + "fin.categoria_id AS idCat, "
				+ "fin.caixa_id AS caixa, " + "fin.pagamento_id AS pagamento, " + "CASE "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 5 THEN '05 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 4 THEN '04 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 3 THEN '03 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 2 THEN '02 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 1 THEN 'FALTA UM DIA PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 0 THEN 'DIA DO PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) <= 0 THEN 'ATRASADO' " + " ELSE 'AQUARDANDO PAGAMENTO' "
				+ " END AS dia, " + "c.descricao AS categoria, CASE " + " WHEN fin.tipo = '1' THEN 'Receita' "
				+ " WHEN fin.tipo = '2' THEN 'Despesa' " + " END " + " AS idDescricao, CASE "
				+ " WHEN fin.flag = '1' THEN 'Pago' " + " WHEN fin.flag = '2' THEN 'Em Aberto' " + " END "
				+ " AS FlagDesc " + "FROM " + " financeiro AS fin " + "INNER JOIN "
				+ " categoria AS c ON (fin.categoria_id = c.id) INNER JOIN "
				+ " aluno AS al ON (fin.aluno_id = al.id) WHERE fin.data > curdate() AND tipo='1' "
				+ " group by fin.id ORDER BY fin.data ASC";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Financeiro financeiro = null;

		while (rs.next()) {

			financeiro = new Financeiro();

			financeiro.setId(rs.getLong("id"));
			financeiro.setData(rs.getDate("data"));
			financeiro.setValor(rs.getDouble("valor"));
			financeiro.setFlag(rs.getInt("flag"));
			financeiro.setQtd(rs.getInt("qtd"));
			financeiro.setTipo(rs.getInt("idTipo"));
			financeiro.setDescricao(rs.getString("descricao"));
			financeiro.setDia(rs.getString("dia"));
			financeiro.setIdTipo(rs.getString("idDescricao"));
			financeiro.setIdFlag(rs.getString("FlagDesc"));
			financeiro.setValorTotal(rs.getDouble("total"));
			financeiro.setIdFormaPagamento(rs.getInt("pagamento"));

			Categoria cat = new Categoria();

			cat.setId(rs.getLong("idCat"));
			cat.setDescricao(rs.getString("categoria"));
			financeiro.setCategoria(cat);

			Aluno al = new Aluno();

			al.setId(rs.getLong("idAluno"));
			al.setNome(rs.getString("idNome"));
			financeiro.setAluno(al);
			
			Caixa cx = new Caixa();
			cx.setId(rs.getLong("caixa"));
			financeiro.setCaixa(cx);

			lista.add(financeiro);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	public double getTotalAreceber() throws SQLException {
		con = new ConnectionFactory().getConnection();

		double total = 0;

		String sql = "SELECT sum(valor) as total FROM financeiro WHERE data > curdate() AND tipo='1'";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			if (rs.getDouble("total") != 0) {
				total = rs.getDouble("total");
			} else {
				total = 0;
			}
		}

		rs.close();

		stmt.close();
		con.close();

		return total;
	}
	
	
	public List<Financeiro> contasApagar() throws SQLException {

		List<Financeiro> lista = new ArrayList<Financeiro>();

		String sql = "SELECT " + "fin.id, " + "al.nome as idNome," + "sum(fin.valor) as total, " + "fin.data, "
				+ "fin.valor, " + "fin.flag, " + "fin.qtd, " + "fin.tipo as idTipo, " + "fin.servico_id AS servico, "
				+ "fin.aluno_id AS idAluno, " + "fin.descricao, " + "fin.categoria_id AS idCat, "
				+ "fin.caixa_id AS caixa, " + "fin.pagamento_id AS pagamento, " + "CASE "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 5 THEN '05 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 4 THEN '04 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 3 THEN '03 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 2 THEN '02 DIAS PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 1 THEN 'FALTA UM DIA PARA O PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) = 0 THEN 'DIA DO PAGAMENTO' "
				+ " WHEN DATEDIFF(fin.data, CURDATE()) <= 0 THEN 'ATRASADO' " + " ELSE 'AQUARDANDO PAGAMENTO' "
				+ " END AS dia, " + "c.descricao AS categoria, CASE " + " WHEN fin.tipo = '1' THEN 'Receita' "
				+ " WHEN fin.tipo = '2' THEN 'Despesa' " + " END " + " AS idDescricao, CASE "
				+ " WHEN fin.flag = '1' THEN 'Pago' " + " WHEN fin.flag = '2' THEN 'Em Aberto' " + " END "
				+ " AS FlagDesc " + "FROM " + " financeiro AS fin " + "INNER JOIN "
				+ " categoria AS c ON (fin.categoria_id = c.id) INNER JOIN "
				+ " aluno AS al ON (fin.aluno_id = al.id) WHERE fin.data > curdate() AND tipo='2' "
				+ " group by fin.id ORDER BY fin.data ASC";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Financeiro financeiro = null;

		while (rs.next()) {

			financeiro = new Financeiro();

			financeiro.setId(rs.getLong("id"));
			financeiro.setData(rs.getDate("data"));
			financeiro.setValor(rs.getDouble("valor"));
			financeiro.setFlag(rs.getInt("flag"));
			financeiro.setQtd(rs.getInt("qtd"));
			financeiro.setTipo(rs.getInt("idTipo"));
			financeiro.setDescricao(rs.getString("descricao"));
			financeiro.setDia(rs.getString("dia"));
			financeiro.setIdTipo(rs.getString("idDescricao"));
			financeiro.setIdFlag(rs.getString("FlagDesc"));
			financeiro.setValorTotal(rs.getDouble("total"));
			financeiro.setIdFormaPagamento(rs.getInt("pagamento"));

			Categoria cat = new Categoria();

			cat.setId(rs.getLong("idCat"));
			cat.setDescricao(rs.getString("categoria"));
			financeiro.setCategoria(cat);

			Aluno al = new Aluno();

			al.setId(rs.getLong("idAluno"));
			al.setNome(rs.getString("idNome"));
			financeiro.setAluno(al);
			
			Caixa cx = new Caixa();
			cx.setId(rs.getLong("caixa"));
			financeiro.setCaixa(cx);

			lista.add(financeiro);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	public double getTotalApagar() throws SQLException {
		con = new ConnectionFactory().getConnection();

		double total = 0;

		String sql = "SELECT sum(valor) as total FROM financeiro WHERE data > curdate() AND tipo='2'";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			if (rs.getDouble("total") != 0) {
				total = rs.getDouble("total");
			} else {
				total = 0;
			}
		}

		rs.close();

		stmt.close();
		con.close();

		return total;
	}
	

}
