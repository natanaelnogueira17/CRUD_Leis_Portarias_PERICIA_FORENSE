package br.gov.ce.pefoce.leiPortaria.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;

import br.gov.ce.pefoce.norma.entity.Norma;
import br.gov.ce.pefoce.sistema.entity.Usuario;



@Entity
@Table(name = "TB_LEI_PORTARIA_LEI", schema = "documento")

public class LeiPortaria implements Serializable {

	private static final long serialVersionUID = -3713602570174981886L;

	@Id
	@Column(name = "ID_LEI", columnDefinition = "numeric", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LEI_PORTARIA")
	@SequenceGenerator(name = "SEQ_LEI_PORTARIA", sequenceName = "DOCUMENTO.SEQ_LEI_PORTARIA")
	private Integer id;


	@Column(name = "DATA_INCLUSAO_LEI")
	private Date dataInclusao;
	
	@Column(name = "DATA_FINALIZACAO_LEI")
	private Date dataFinalizacao;
	
	@OneToOne
	@JoinColumn(name = "ID_USU_LEI", referencedColumnName = "ID_USU")
	private Usuario usuario;
	
	@Column(name = "NUMERO_LEI")
	private Integer numero;

	@OneToOne
	@JoinColumn(name = "ID_NOR_LEI", referencedColumnName = "ID_NOR")
	private Norma norma;
	
	@Column(name = "ANO_LEI")
	private Integer ano;
	

	@Column(name = "DATA_NORMATIVA_LEI")
	private Date dataNormativa;
	

	@Column(name = "DATA_DIARIO_OFICIAL_LEI")
	private Date dataDiarioOficial;


	@Column(name = "PALAVRA_CHAVE_LEI")
	private String palavraChave;

	@Column(name = "DESCRICAO_LEI")
	private String descricao;
	
	@Column(name = "ARQUIVO_LEI")
	private byte[] arquivo;
	
	@Transient
	private List<String>listaPalavraChave;
	
	@Transient
	private Date dataInicio;
	
	@Transient
	private Date dataFim;
	

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Norma getNorma() {
		return norma;
	}

	public void setNorma(Norma norma) {
		this.norma = norma;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Date getDataNormativa() {
		return dataNormativa;
	}

	public void setDataNormativa(Date dataNormativa) {
		this.dataNormativa = dataNormativa;
	}

	public Date getDataDiarioOficial() {
		return dataDiarioOficial;
	}

	public void setDataDiarioOficial(Date dataDiarioOficial) {
		this.dataDiarioOficial = dataDiarioOficial;
	}

	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<String> getListaPalavraChave() {
		return listaPalavraChave;
	}

	public void setListaPalavraChave(List<String> listaPalavraChave) {
		this.listaPalavraChave = listaPalavraChave;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(arquivo);
		result = prime * result + Objects.hash(ano, dataDiarioOficial, dataFinalizacao, dataInclusao, dataNormativa,
				descricao, id, norma, numero, palavraChave, usuario);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeiPortaria other = (LeiPortaria) obj;
		return Objects.equals(ano, other.ano) && Arrays.equals(arquivo, other.arquivo)
				&& Objects.equals(dataDiarioOficial, other.dataDiarioOficial)
				&& Objects.equals(dataFinalizacao, other.dataFinalizacao)
				&& Objects.equals(dataInclusao, other.dataInclusao)
				&& Objects.equals(dataNormativa, other.dataNormativa) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(id, other.id) && Objects.equals(norma, other.norma)
				&& Objects.equals(numero, other.numero) && Objects.equals(palavraChave, other.palavraChave)
				&& Objects.equals(usuario, other.usuario);
	}
	
		
	

	
	
	

}
