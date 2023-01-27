package br.gov.ce.pefoce.norma.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.gov.ce.pefoce.sistema.entity.Usuario;

@Entity(name="Norma")
@Table(name = "TB_NORMA_NOR", schema = "documento")
public class Norma implements Serializable {
	private static final long serialVersionUID = -3713602570174981886L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_NORMA")
	@SequenceGenerator(name = "SEQ_NORMA", sequenceName = "DOCUMENTO.SEQ_NORMA")
	@Column(name = "ID_NOR", columnDefinition = "numeric", nullable = false)
	private Integer id;
	
	@Column(name = "DESCRICAO_NOR")
	private String descricao;
	
	@Column(name = "DATA_INCLUSAO_NOR")
	private Date dataInclusao;
	
	@Column(name = "DATA_FINALIZACAO_NOR")
	private Date dataFinalizacao;
	
	@OneToOne
	@JoinColumn(name = "ID_USU_NOR", referencedColumnName = "ID_USU")
	private Usuario usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataFinalizacao, dataInclusao, descricao, id, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Norma other = (Norma) obj;
		return Objects.equals(dataFinalizacao, other.dataFinalizacao)
				&& Objects.equals(dataInclusao, other.dataInclusao) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(id, other.id) && Objects.equals(usuario, other.usuario);
	}

	
}
