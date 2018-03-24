package com.java.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java.conexao.ConnectionFactory;

public class PainelFinanceiroDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection con;
	
	public double getTotalAtualizado() throws SQLException {
		con = new ConnectionFactory().getConnection();

		double total = 0;

		String sql = "SELECT sum(valor) as total FROM financeiro as fin where flag='1' AND fin.tipo='1' ";

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
	
	

	public double getTotalReceita() throws SQLException {
		con = new ConnectionFactory().getConnection();

		double total = 0;

		String sql = "SELECT sum(valor) as total FROM financeiro as fin where flag='1' AND fin.tipo='1' ";

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
	
	
	public double getTotalDespesa() throws SQLException {
		con = new ConnectionFactory().getConnection();

		double total = 0;

		String sql = "SELECT sum(valor) as total FROM financeiro as fin where flag='1' AND fin.tipo='2' AND fin.caixa_id='1'";

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
	
	
	public double getTotalBancoReceita() throws SQLException {
		con = new ConnectionFactory().getConnection();

		double total = 0;

		String sql = "SELECT sum(valor) as total FROM financeiro as fin where flag='1' AND fin.tipo='1' AND fin.caixa_id='2'";

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


	public double getTotalBancoDespesa() throws SQLException {
		con = new ConnectionFactory().getConnection();

		double total = 0;

		String sql = "SELECT sum(valor) as total FROM financeiro as fin where flag='1' AND fin.tipo='2' AND fin.caixa_id='2'";

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





public double getTotalDespesaMovimentada() throws SQLException {
	con = new ConnectionFactory().getConnection();

	double total = 0;

	String sql = "SELECT sum(valor) as total FROM financeiro as fin where flag='1' AND fin.tipo='2'";

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




public double getPagar() throws SQLException {
	con = new ConnectionFactory().getConnection();

	double total = 0;

	String sql = "SELECT sum(valor) as total FROM financeiro WHERE data > curdate() AND tipo ='2' AND flag='2'";

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



public double getReceber() throws SQLException {
	con = new ConnectionFactory().getConnection();

	double total = 0;

	String sql = "SELECT sum(valor) as total FROM financeiro WHERE data > curdate() AND tipo ='1' AND flag='2'";

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




public double getRecebido() throws SQLException {
	con = new ConnectionFactory().getConnection();

	double total = 0;

	String sql = "SELECT sum(valor) as total FROM financeiro WHERE flag ='1' AND tipo='1'";

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



public double getTotalAtrasado() throws SQLException {
	con = new ConnectionFactory().getConnection();

	double total = 0;

	
	String sql = "SELECT sum(valor) as total FROM financeiro WHERE data < curdate() AND flag ='2'";

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


