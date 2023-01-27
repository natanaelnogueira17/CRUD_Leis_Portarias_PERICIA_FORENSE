package br.gov.ce.pefoce.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.gov.ce.pefoce.norma.dao.NormaDao;
import br.gov.ce.pefoce.norma.entity.Norma;
import br.gov.ce.pefoce.util.FacesUtils;

@ManagedBean
@ViewScoped
public class NormaBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean ativo;
	private Norma norma;
	private String descricaoNorma;
	
	private List<Norma> listaNormas;


	@ManagedProperty("#{normaDao}")
	private NormaDao normaDao;
	
	
	
	@PostConstruct
	public void initBean() {		
		setBrowsing(true);
		
	}
	
	public void incluir () {
		norma =  new Norma();	
		setInserting(true);
		setAtivo(true);
	}
	

	
	public void salvar () {
		
		try {
			if(isUpdating()) {
				norma.setUsuario(getUsuarioAutenticado());
				normaDao.update(norma);
			}else if(isInserting()){				
				norma.setDataInclusao(new Date());
				norma.setUsuario(getUsuarioAutenticado());
				norma.setDescricao(norma.getDescricao());
				normaDao.save(norma);
				
			}
			consultar();
			setBrowsing(true);
			FacesUtils.addInfoMessage("Os dados foram salvos com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar() {
		setBrowsing(true);
		consultar();
	}
	
	public void consultar() {
		setListaNormas(normaDao.buscarPorDescricao(descricaoNorma));
	}
	
	public void edit(Norma norma) {
		if(norma.getDataFinalizacao() != null) {
			setAtivo(false);
		}else {
			setAtivo(true);
		}
		this.norma = norma;
		setUpdating(true);
	}

	public NormaDao getNormaDao() {
		return normaDao;
	}

	public void setNormaDao(NormaDao normaDao) {
		this.normaDao = normaDao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Norma getNorma() {
		return norma;
	}

	public void setNorma(Norma norma) {
		this.norma = norma;
	}

	public String getDescricaoNorma() {
		return descricaoNorma;
	}

	public void setDescricaoNorma(String descricaoNorma) {
		this.descricaoNorma = descricaoNorma;
	}

	public List<Norma> getListaNormas() {
		return listaNormas;
	}

	public void setListaNormas(List<Norma> listaNormas) {
		this.listaNormas = listaNormas;
	}
	
	
	


	
	
	
	
	
	

}
