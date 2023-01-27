package br.gov.ce.pefoce.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.ce.pefoce.norma.dao.NormaDao;
import br.gov.ce.pefoce.norma.entity.Norma;

@FacesConverter("normaConverter")
public class NormaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		if (value == null)
			return null;

		NormaDao dao = ctx.getApplication().evaluateExpressionGet(ctx, "#{normaDao}", NormaDao.class);
		
		return dao.find(Integer.valueOf(value));
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component, Object value) {
		if (value instanceof Norma) {
			Norma norma = (Norma) value;
			return String.valueOf(norma.getId());
		}
		return "";
	}
}
