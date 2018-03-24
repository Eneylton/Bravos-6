package com.java.controller.financeiro;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;

import com.java.dao.UsuarioDAO;
import com.java.modelo.Caixa;
import com.java.modelo.Categoria;
import com.java.modelo.ExcelOptions;
import com.java.modelo.Financeiro;
import com.java.modelo.PDFOptions;
import com.java.service.CaixaService;
import com.java.service.CategoriaService;
import com.java.service.FinanceiroService;
import com.java.service.PainelFinanceiroService;
import com.java.service.RelatorioService;
import com.java.util.FacesMessages;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class FinanceiroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private FinanceiroService financeiroService;

	@Inject
	private PainelFinanceiroService painelFinanceiroService;

	@Inject
	private FacesMessages messages;

	@Inject
	private CaixaService caixaService;

	@Inject
	private CategoriaService categoriaService;

	@Inject
	private RelatorioService relatorioService;

	private Financeiro financeiro;

	private Financeiro financeiroSelecionado;

	@Inject
	private Financeiro financeiroEdicao;

	private List<Financeiro> listaFinanceiros = new ArrayList<>();

	private List<Financeiro> pesquisarFinanceiros = new ArrayList<>();

	private Map<String, Caixa> listarCaixas = new HashMap<String, Caixa>();

	private Map<String, Categoria> listarCategorias = new HashMap<String, Categoria>();

	private double receita;

	private double despesa;

	private double movimentado;
	
	private ExcelOptions excelOpt;

	private PDFOptions pdfOpt;

	@PostConstruct
	public void init() {

		try {

			carregarLista();
			carregarValor();

			preencheListasSelect();

			this.limpar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}

	}

	public void salvar() {

		try {

			if (financeiroEdicao.getId() == null) {
				financeiroEdicao.setUsuario(usuarioDAO.retornarUsuarioPorID(Session.retornaIdUsuarioLogado()));
				financeiroService.incluirFinanceiro(financeiroEdicao);
			} else {
				financeiroEdicao.setUsuario(usuarioDAO.retornarUsuarioPorID(Session.retornaIdUsuarioLogado()));
				financeiroService.alterar(financeiroEdicao);
			}

			FacesUtil.addSuccessMessage("Lançamento Registrado com Sucesso !");

			this.limpar();

			carregarLista();
			carregarValor();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void excluir() throws SQLException {
		financeiroService.excluir(financeiroSelecionado);
		financeiroSelecionado = null;

		carregarLista();
		carregarValor();
		
		limpar();

		messages.error("Lançamento excluído com sucesso!");
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:servicoTable"));
	}

	private void preencheListasSelect() throws SQLException {

		listarCaixas = new HashMap<String, Caixa>();
		for (Caixa caixa : caixaService.listarTodos()) {
			listarCaixas.put(caixa.getDescricao(), caixa);
		}

		listarCategorias = new HashMap<String, Categoria>();
		for (Categoria categoria : categoriaService.listarTodos()) {
			listarCategorias.put(categoria.getDescricao(), categoria);
		}

	}

	private void carregarLista() throws SQLException {

		listaFinanceiros = financeiroService.listarTodos();
		pesquisarFinanceiros = financeiroService.listarTodos();
	}

	public void gerarRelatorio() {

		try {
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			listaFinanceiros = financeiroService.listarTodos();
			relatorioService.gerarRelatorio(listaFinanceiros, parametros, "financeiro");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void gerarRecibo() {

		try {

			Long idRecibo = financeiroSelecionado.getId();
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			listaFinanceiros = financeiroService.retornoIdRecibo(idRecibo);
			relatorioService.gerarRecibo(listaFinanceiros, parametros, "recibo");

		

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	 public void customizationOptions() {
	        excelOpt = new ExcelOptions();
	        excelOpt.setFacetBgColor("#8c2f4d");
	        excelOpt.setFacetFontSize("14");
	        excelOpt.setFacetFontColor("#ffcb39");
	        excelOpt.setFacetFontStyle("BOLD");
	        excelOpt.setCellFontColor("#0000ff");
	        excelOpt.setCellFontSize("14");
	         
	        pdfOpt = new PDFOptions();
	        pdfOpt.setFacetBgColor("#FF0000");
	        pdfOpt.setFacetFontColor("#e1e1e2");
	        pdfOpt.setFacetFontStyle("BOLD");
	        pdfOpt.setCellFontSize("18");
	    }
	 
	 
		public void custumizadoXLS(Object document) {
			HSSFWorkbook wb = (HSSFWorkbook) document;
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow header = sheet.getRow(0);

			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.CORAL.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setUserStyleName("Alunos");

			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				HSSFCell cell = header.getCell(i);

				cell.setCellStyle(cellStyle);
			}
		}
		


	private void carregarValor() throws SQLException {

		receita = painelFinanceiroService.getTotalAtualizado();

		despesa = painelFinanceiroService.getTotalDespesa();

		movimentado = receita - despesa;
	}

	public void prepararNovoCadastro() {
		financeiroEdicao = new Financeiro();
	}

	public void limpar() {
		this.financeiro = new Financeiro();
	}

	public Financeiro getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Financeiro financeiro) {
		this.financeiro = financeiro;
	}

	public Map<String, Caixa> getListarCaixas() {
		return listarCaixas;
	}

	public void setListarCaixas(Map<String, Caixa> listarCaixas) {
		this.listarCaixas = listarCaixas;
	}

	public Map<String, Categoria> getListarCategorias() {
		return listarCategorias;
	}

	public void setListarCategorias(Map<String, Categoria> listarCategorias) {
		this.listarCategorias = listarCategorias;
	}

	public List<Financeiro> getListaFinanceiros() {
		return listaFinanceiros;
	}

	public void setListaFinanceiros(List<Financeiro> listaFinanceiros) {
		this.listaFinanceiros = listaFinanceiros;
	}

	public double getMovimentado() {
		return movimentado;
	}

	public void setMovimentado(double movimentado) {
		this.movimentado = movimentado;
	}

	public List<Financeiro> getPesquisarFinanceiros() {
		return pesquisarFinanceiros;
	}

	public void setPesquisarFinanceiros(List<Financeiro> pesquisarFinanceiros) {
		this.pesquisarFinanceiros = pesquisarFinanceiros;
	}

	public Financeiro getFinanceiroSelecionado() {
		return financeiroSelecionado;
	}

	public void setFinanceiroSelecionado(Financeiro financeiroSelecionado) {
		this.financeiroSelecionado = financeiroSelecionado;
	}

	public Financeiro getFinanceiroEdicao() {
		return financeiroEdicao;
	}

	public void setFinanceiroEdicao(Financeiro financeiroEdicao) {
		this.financeiroEdicao = financeiroEdicao;
	}

	public FacesMessages getMessages() {
		return messages;
	}

	public void setMessages(FacesMessages messages) {
		this.messages = messages;
	}

	public double getReceita() {
		return receita;
	}

	public void setReceita(double receita) {
		this.receita = receita;
	}

	public double getDespesa() {
		return despesa;
	}

	public void setDespesa(double despesa) {
		this.despesa = despesa;
	}

	public ExcelOptions getExcelOpt() {
		return excelOpt;
	}

	public void setExcelOpt(ExcelOptions excelOpt) {
		this.excelOpt = excelOpt;
	}

	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}
	
	public boolean isEditando(){
		
		return this.financeiroEdicao.getId() != null;
		
		
	}
	
	
public boolean isOcultarInput(){
		
		return this.financeiroEdicao.getId() == null;
		
		
	}
	
	
	

}
