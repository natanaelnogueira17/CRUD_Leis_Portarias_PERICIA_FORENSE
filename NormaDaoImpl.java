package br.gov.ce.pefoce.norma.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.pefoce.dao.impl.GenericDAO;
import br.gov.ce.pefoce.norma.dao.NormaDao;
import br.gov.ce.pefoce.norma.entity.Norma;


@Repository("normaDao")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class NormaDaoImpl extends GenericDAO<Norma> implements NormaDao{

	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Norma> buscarPorDescricao(String descricaoNorma) {
		Criteria listaNormas = this.getSession().createCriteria(Norma.class);
		listaNormas.add(Restrictions.ilike("descricao", '%' + descricaoNorma + '%'));
		listaNormas.addOrder(Order.asc("id"));		
		return listaNormas.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Norma> buscarTodosAtivos() {
		Criteria listaNorma = this.getSession().createCriteria(Norma.class);
		listaNorma.add(Property.forName("dataFinalizacao").isNull());
		listaNorma.addOrder(Order.asc("descricao"));
		return listaNorma.list();
	}
	
	


}
