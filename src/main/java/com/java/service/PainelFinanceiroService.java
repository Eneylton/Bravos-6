package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;

import com.java.dao.PainelFinanceiroDAO;

public class PainelFinanceiroService implements Serializable {

	private static final long serialVersionUID = 1L;

	private PainelFinanceiroDAO painelFinanceiroDAO = new PainelFinanceiroDAO();
	
	
	public double getTotalAtualizado() throws SQLException {
		return painelFinanceiroDAO.getTotalAtualizado();
	}
	
	
	public double getTotalReceita() throws SQLException {
		return painelFinanceiroDAO.getTotalReceita();
	}
	
	
	public double getTotalDespesa() throws SQLException {
		return painelFinanceiroDAO.getTotalDespesa();
	}
	
	
	public double getTotalBancoReceita() throws SQLException {
		return painelFinanceiroDAO.getTotalBancoReceita();
	
	}
	
	public double getTotalDespesaMovimentada() throws SQLException {
		return painelFinanceiroDAO.getTotalDespesaMovimentada();
	}

	public double getTotalBancoDespesa() throws SQLException {
		return painelFinanceiroDAO.getTotalBancoDespesa();
	}
	
	
	public double getPagar() throws SQLException {
		return painelFinanceiroDAO.getPagar();
	}
	
	public double getReceber() throws SQLException {
		return painelFinanceiroDAO.getReceber();
	}
	
	public double getRecebido() throws SQLException {
		return painelFinanceiroDAO.getRecebido();
	}
	
	
	public double getTotalAtrasado() throws SQLException {
		return painelFinanceiroDAO.getTotalAtrasado();
	}


}
