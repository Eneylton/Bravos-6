package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.java.dao.ListasDAO;
import com.java.modelo.Financeiro;

public class ListaService implements Serializable {

	private static final long serialVersionUID = 1L;

	private ListasDAO listaDAO = new ListasDAO();

	public List<Financeiro> retornoDespesa() throws SQLException {
		return listaDAO.retornoDespesa();
	}

	public List<Financeiro> contasAreceber() throws SQLException {
		return listaDAO.contasAreceber();
	}

	public List<Financeiro> contasApagar() throws SQLException {
		return listaDAO.contasApagar();
	}

	public double getTotalDespesa() throws SQLException {
		return listaDAO.getTotalDespesa();

	}

	public double getTotalAreceber() throws SQLException {

		return listaDAO.getTotalAreceber();
	}

	public double getTotalApagar() throws SQLException {

		return listaDAO.getTotalApagar();
	}

}
