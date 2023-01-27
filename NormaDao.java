package br.gov.ce.pefoce.norma.dao;

import java.util.List;

import br.gov.ce.pefoce.dao.DAO;
import br.gov.ce.pefoce.norma.entity.Norma;

public interface NormaDao extends DAO<Norma>{
	public List<Norma>buscarPorDescricao(String descricaoNorma);

	public List<Norma> buscarTodosAtivos();

}
