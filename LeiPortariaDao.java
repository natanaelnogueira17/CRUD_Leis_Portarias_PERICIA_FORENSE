package br.gov.ce.pefoce.leiPortaria.dao;

import java.util.List;

import br.gov.ce.pefoce.dao.DAO;
import br.gov.ce.pefoce.leiPortaria.entity.LeiPortaria;
import br.gov.ce.pefoce.norma.entity.Norma;

public interface LeiPortariaDao extends DAO<LeiPortaria> {
	

	List<LeiPortaria>buscarPorLeiPortaria(LeiPortaria leiPortaria, String descricaoSelecionada) ;
	
	public List<LeiPortaria> buscarTodosAtivos();

	List<Integer> buscarTodosAnosPorNorma(Norma norma);

	List<Integer> buscarTodosNumeroPorNorma(LeiPortaria leiPortaria);
	
	
	
	
	
	

}
