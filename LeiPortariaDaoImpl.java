
package br.gov.ce.pefoce.leiPortaria.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.pefoce.dao.impl.GenericDAO;
import br.gov.ce.pefoce.leiPortaria.dao.LeiPortariaDao;
import br.gov.ce.pefoce.leiPortaria.entity.LeiPortaria;
import br.gov.ce.pefoce.norma.entity.Norma;


@Repository("leiPortariaDao")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class LeiPortariaDaoImpl extends GenericDAO<LeiPortaria> implements LeiPortariaDao {

	private static final long serialVersionUID = 1L;
	


	@SuppressWarnings("unchecked")
	@Override
	public List<LeiPortaria> buscarPorLeiPortaria( LeiPortaria leiPortaria, String descricaoSelecionada) {		
		Criteria listaleiPortaria = this.getSession().createCriteria(LeiPortaria.class);		
		if(leiPortaria.getNorma()!= null) {
			listaleiPortaria.add(Restrictions.eq("norma", leiPortaria.getNorma()));
		}
		
		if(leiPortaria.getNumero() != null) {
			listaleiPortaria.add(Restrictions.eq("numero", leiPortaria.getNumero()));
		}
		
		if(leiPortaria.getAno() != null) {
			listaleiPortaria.add(Restrictions.eq("ano", leiPortaria.getAno()));			
		}
		
		if(leiPortaria.getDescricao() != null) {
			listaleiPortaria.add(Restrictions.ilike("descricao", '%' +leiPortaria.getDescricao()+'%' ));	
			
		}
		
	
		if(leiPortaria.getDataInicio() != null) {
			if(leiPortaria.getDataFim()!= null) {
				
				listaleiPortaria.add(Restrictions.between(descricaoSelecionada, leiPortaria.getDataInicio(), leiPortaria.getDataFim()));
			}else {
				//TODO data start inicio;
				listaleiPortaria.add(Restrictions.ge(descricaoSelecionada, leiPortaria.getDataInicio()));
			}
			
		}else if(leiPortaria.getDataFim()!= null) {
			// TODO data termino fim;
			listaleiPortaria.add(Restrictions.le(descricaoSelecionada, leiPortaria.getDataFim()));
		}
		
		if(leiPortaria.getListaPalavraChave() != null) {
			String palavra = null;
		 List<String> listPalavra = new ArrayList<String>(); 
		 listPalavra = leiPortaria.getListaPalavraChave();
		 for (String lp : listPalavra) {
			  palavra = lp;
			
		}
		 listaleiPortaria.add(Restrictions.ilike("palavraChave", '%'+palavra+'%' ));
		 
			
		}
		
		
		return listaleiPortaria.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<LeiPortaria> buscarTodosAtivos() {
		Criteria listaLeiPortaria = this.getSession().createCriteria(LeiPortaria.class);
		listaLeiPortaria.add(Property.forName("dataFinalizacao").isNull());
		listaLeiPortaria.addOrder(Order.asc("descricao"));
		
		return listaLeiPortaria.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> buscarTodosAnosPorNorma(Norma norma) {
		Criteria consulta = this.getSession().createCriteria(LeiPortaria.class);
		consulta.add(Property.forName("dataFinalizacao").isNull());
		consulta.createAlias("norma", "norma");
		consulta.add(Restrictions.eq("norma", norma));
		consulta.setProjection(Projections.distinct(Projections.projectionList().add(Property.forName("ano"))));
//		List<LeiPortaria> listaLp = consulta.list();
//		List<Integer> listaAno = new ArrayList<Integer>();
//		for (LeiPortaria lp : listaLp) {
//			if(!listaAno.contains(lp.getAno())) {
//				listaAno.add(lp.getAno());
//				
//			}
//			
//		}
		
		return consulta.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> buscarTodosNumeroPorNorma(LeiPortaria leiPortaria) {
		Criteria consultaNumero = this.getSession().createCriteria(LeiPortaria.class);
		consultaNumero.add(Property.forName("dataFinalizacao").isNull());
		consultaNumero.createAlias("norma", "norma");
		if(leiPortaria.getNorma() != null) {
			consultaNumero.add(Restrictions.eq("norma", leiPortaria.getNorma()));
			
		}
		
		if(leiPortaria.getAno()!= null) {
			consultaNumero.add(Restrictions.eq("ano", leiPortaria.getAno()));
			
		}
		
		consultaNumero.setProjection(Projections.distinct(Projections.projectionList().add(Property.forName("numero"))));
		
		return consultaNumero.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}


//	@Override
//	public List<Integer> buscarTodosNumeroPorNorma(Norma norma) {
//		Criteria consultaNumero = this.getSession().createCriteria(LeiPortaria.class);
//		consultaNumero.add(Property.forName("dataFinalizacao").isNull());
//		consultaNumero.add(Restrictions.eqOrIsNull("norma.id", consultaNumero));
//		return null;
//	}



	




}