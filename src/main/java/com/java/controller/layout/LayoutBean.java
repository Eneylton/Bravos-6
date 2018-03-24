package com.java.controller.layout;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.java.util.Session;

@Named
@ViewScoped
public class LayoutBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String usuarioLogado;

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	@PostConstruct
	public void init(){
		this.usuarioLogado = Session.retornaNomeCompletoUsuarioLogado();
	}
	
	

}
