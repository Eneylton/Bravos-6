package com.java.controller.usuario;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Usuario;
import com.java.modeloEspecializado.AlteraSenhaUsuario;
import com.java.service.UsuarioService;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;
	
	private Usuario usuario;
	
	private AlteraSenhaUsuario alteraSenhaUsuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public AlteraSenhaUsuario getAlteraSenhaUsuario() {
		return alteraSenhaUsuario;
	}

	public void setAlteraSenhaUsuario(AlteraSenhaUsuario alteraSenhaUsuario) {
		this.alteraSenhaUsuario = alteraSenhaUsuario;
	}

	@PostConstruct
	public void init() throws IOException {		
		this.limpar();
	}
	
	public void salvar() {
		try {
			this.usuarioService.salvar(usuario);
			FacesUtil.addSuccessMessage("Usuário salvo com sucesso!");
			this.limpar();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void alterarSenhaUsuario() {
		
		try {
			
			this.alteraSenhaUsuario = new AlteraSenhaUsuario();
			
			this.alteraSenhaUsuario.setId(this.usuario.getId());
			this.alteraSenhaUsuario.setLogin(this.usuario.getLogin());
			this.alteraSenhaUsuario.setNovaSenha(this.usuario.getSenha());
			
			this.usuarioService.alterarSenhaUsuario(this.alteraSenhaUsuario);
			FacesUtil.addSuccessMessage("Senha alterada com sucesso!");
			this.limpar();
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}
	
	public void limpar() {
		this.usuario = new Usuario();
		this.alteraSenhaUsuario = new AlteraSenhaUsuario();
	}
	
	
}
