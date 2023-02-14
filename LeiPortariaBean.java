package br.gov.ce.pefoce.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.gov.ce.pefoce.leiPortaria.dao.LeiPortariaDao;
import br.gov.ce.pefoce.leiPortaria.entity.LeiPortaria;
import br.gov.ce.pefoce.norma.dao.NormaDao;
import br.gov.ce.pefoce.norma.entity.Norma;
import br.gov.ce.pefoce.util.FacesUtils;
import br.gov.ce.pefoce.util.StrUtil;

@ViewScoped
@ManagedBean
public class LeiPortariaBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean editarItem = false;
	private LeiPortaria leiPortaria;
	private boolean ativo;
	private Norma norma;
	private LazyDataModel<LeiPortaria> leiPortariaModel;
	private int paginaAtual;

	private UploadedFile anexoUpload;
	private String nomeArquivo;
	private List<LeiPortaria> listaLeisPortarias;
	private List<Integer> listaAno;
	private List<Integer> listaNumero;
	private List<String> listStatus;
	private String statusSelecionado;
	private Norma normaSelecionada;
	private Integer anoSelecionado;
	private Integer numeroSelecionado;
	private Date dataInicioSelecionada;
	private Date dataTerminoSelecionada;
	private List<String> listaPalavraChaveSelecionada;
	private String descricaoSelecionada;

	@ManagedProperty("#{normaDao}")
	private NormaDao normaDao;

	@ManagedProperty("#{leiPortariaDao}")
	private LeiPortariaDao leiPortariaDao;

	@PostConstruct
	public void initBean() {
		setBrowsing(true);
		if (leiPortaria == null) {
			listaAno = new ArrayList<Integer>();
			leiPortaria = new LeiPortaria();
			leiPortaria.setListaPalavraChave(new ArrayList<String>());
			descricaoSelecionada = "dataDiarioOficial";
			redireciona();
			
			
		}

	}
	
	 public String redireciona() {
	        return "www.google.com.br?faces-redirect=true";
	    }

	public void incluir() {
		leiPortaria = new LeiPortaria();
		leiPortaria.setListaPalavraChave(new ArrayList<String>());
		setInserting(true);

	}

	public void editar(LeiPortaria leiPortaria) {
		this.leiPortaria = leiPortaria;
		setUpdating(true);
		leiPortaria.setListaPalavraChave(new ArrayList<String>());
		if (leiPortaria.getPalavraChave() != null) {

			leiPortaria.setListaPalavraChave(
					new ArrayList<String>(Arrays.asList(leiPortaria.getPalavraChave().split(" "))));
		}
	}

	public void consultar() {
		leiPortariaModel = new LazyLeiPortariaDataModel(
				leiPortariaDao.buscarPorLeiPortaria(leiPortaria, descricaoSelecionada));

	}

	public void cancelar() {
		setBrowsing(true);
		limparCampos();
		consultar();

	}

	public void limparCampos() {
		setBrowsing(true);
		leiPortaria = new LeiPortaria();

	}

	public void salvar() {
		try {

			if (leiPortaria.getAno() < 2000 || leiPortaria.getAno() > 2099) {
				FacesUtils.addErrorMessage("O valor do Ano não é válido! Por favor valores entre 2000 e 2099");
				return;
			}

			if (leiPortaria.getListaPalavraChave() != null) {
				leiPortaria.setPalavraChave("");
				if (!leiPortaria.getListaPalavraChave().isEmpty()) {
					for (String p : leiPortaria.getListaPalavraChave()) {
						String palavra = p.trim();
						palavra = palavra.replace("[", "");
						palavra = palavra.replace("]", "");
						palavra = palavra.replace(",", "");
						if (leiPortaria.getPalavraChave().isEmpty()) {
							leiPortaria.setPalavraChave(palavra);
						} else {
							leiPortaria.setPalavraChave(leiPortaria.getPalavraChave() + " " + palavra);
						}
					}
				}
			}

			if (isUpdating()) {
				if (leiPortaria.getAno() < 2000 || leiPortaria.getAno() > 2099) {
					FacesUtils.addErrorMessage("O valor do Ano não é válido! Por favor valores entre 2000 e 2099");				
					return;
				}

				leiPortaria.setUsuario(getUsuarioAutenticado());
				leiPortariaDao.update(leiPortaria);
			} else if (isInserting()) {
				leiPortaria.setDataInclusao(new Date());
				leiPortaria.setUsuario(getUsuarioAutenticado());

				leiPortaria = leiPortariaDao.save(leiPortaria);

			}
			consultar();
			setBrowsing(true);
			limparCampos();
			FacesUtils.addInfoMessage("Os dados foram salvos com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public StreamedContent downloadArquivo(LeiPortaria leiPortaria) {
		InputStream input = new ByteArrayInputStream(leiPortaria.getArquivo());
		return new DefaultStreamedContent(input, StrUtil.CONTENT_TYPE_OPENDOCUMENT_FILE,
				leiPortaria.getNorma().getDescricao() + "_" + leiPortaria.getNumero() + "_" + leiPortaria.getAno()
						+ ".pdf");
	}

	public void adicionarArquivo(FileUploadEvent file) {
		if (file.getFile().getSize() > 0 && file.getFile().getContents().length > 0) {

			leiPortaria.setArquivo(file.getFile().getContents());
			nomeArquivo = file.getFile().getFileName();

		}

	}

	public void uploadAnexo(FileUploadEvent event) {
		this.anexoUpload = event.getFile();
		this.anexoUpload.getContents();
	}

	private class LazyLeiPortariaDataModel extends LazyDataModel<LeiPortaria> {
		private static final long serialVersionUID = 1L;

		private List<LeiPortaria> datasource;

		public LazyLeiPortariaDataModel(List<LeiPortaria> datasource) {
			this.datasource = datasource;
		}

		@Override
		public LeiPortaria getRowData(String rowKey) {
			Integer i = null;
			if (rowKey != null && !rowKey.isEmpty()) {
				i = Integer.parseInt(rowKey);
			}

			for (LeiPortaria setor : datasource) {
				if (setor.getId().equals(i)) {
					return setor;
				}
			}
			return null;
		}

		@Override
		public Object getRowKey(LeiPortaria setor) {
			return setor.getId();
		}

		@Override
		public List<LeiPortaria> load(int first, int pageSize, String sortField, SortOrder sortOrder,
				Map<String, Object> filters) {
			List<LeiPortaria> data = new ArrayList<LeiPortaria>();

			// filter
			for (LeiPortaria setor : datasource) {
				boolean match = true;

				if (filters != null) {
					for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
						try {
							String filterProperty = it.next();
							Object filterValue = filters.get(filterProperty);
							String fieldValue = String.valueOf(setor.getClass().getField(filterProperty).get(setor));

							if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
								match = true;
							} else {
								match = false;
								break;
							}
						} catch (Exception e) {
							match = false;
						}
					}
				}

				if (match) {
					data.add(setor);
				}
			}

			// sort
			if (sortField != null) {
				Collections.sort(data, new LazySorter(sortField, sortOrder));
			}

			// rowCount
			int dataSize = data.size();
			this.setRowCount(dataSize);

			// paginate
			if (dataSize > pageSize) {
				try {
					return data.subList(first, first + pageSize);
				} catch (IndexOutOfBoundsException e) {
					return data.subList(first, first + (dataSize % pageSize));
				}
			} else {
				return data;
			}
		}

	}

	private class LazySorter implements Comparator<LeiPortaria> {

		private String sortField;

		private SortOrder sortOrder;

		public LazySorter(String sortField, SortOrder sortOrder) {
			this.sortField = sortField;
			this.sortOrder = sortOrder;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public int compare(LeiPortaria setor1, LeiPortaria setor2) {
			try {
				Object value1 = LeiPortaria.class.getField(this.sortField).get(setor1);
				Object value2 = LeiPortaria.class.getField(this.sortField).get(setor2);

				int value = ((Comparable) value1).compareTo(value2);

				return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
	}

	public List<Norma> getTodasNormas() {
		return normaDao.buscarTodosAtivos();
	}

	public List<LeiPortaria> getLeiPortarias() {
		return leiPortariaDao.buscarTodosAtivos();
	}

	public void atualizarCampoAno() {
		if (leiPortaria.getNorma() != null ) {
			setListaAno(leiPortariaDao.buscarTodosAnosPorNorma(leiPortaria.getNorma()));
		}else {
			setListaAno(null);
			setListaNumero(null);
		}
	}

	public void atualizarCampoNumero() {
		if (leiPortaria.getNorma() != null && leiPortaria.getAno() != null ) {
			setListaNumero(leiPortariaDao.buscarTodosNumeroPorNorma(leiPortaria));
		}else {
			setListaNumero(null);
		}
	}

	public void buscarPorStatus() {
		if (leiPortaria.getStatus() != null) {
			setListStatus(leiPortariaDao.buscarPorStatus(leiPortaria));
		}
	}

	// get and set
	public boolean isEditarItem() {
		return editarItem;
	}

	public void setEditarItem(boolean editarItem) {
		this.editarItem = editarItem;
	}

	public LeiPortaria getLeiPortaria() {
		return leiPortaria;
	}

	public void setLeiPortaria(LeiPortaria leiPortaria) {
		this.leiPortaria = leiPortaria;
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

	public UploadedFile getAnexoUpload() {
		return anexoUpload;
	}

	public void setAnexoUpload(UploadedFile anexoUpload) {
		this.anexoUpload = anexoUpload;
	}

	public List<LeiPortaria> getListaLeisPortarias() {
		return listaLeisPortarias;
	}

	public void setListaLeisPortarias(List<LeiPortaria> listaLeisPortarias) {
		this.listaLeisPortarias = listaLeisPortarias;
	}

	public NormaDao getNormaDao() {
		return normaDao;
	}

	public void setNormaDao(NormaDao normaDao) {
		this.normaDao = normaDao;
	}

	public LeiPortariaDao getLeiPortariaDao() {
		return leiPortariaDao;
	}

	public void setLeiPortariaDao(LeiPortariaDao leiPortariaDao) {
		this.leiPortariaDao = leiPortariaDao;
	}

	public LazyDataModel<LeiPortaria> getLeiPortariaModel() {
		return leiPortariaModel;

	}

	public void setLeiPortariaModel(LazyDataModel<LeiPortaria> leiPortariaModel) {
		this.leiPortariaModel = leiPortariaModel;

	}

	public int getPaginaAtual() {
		return paginaAtual;

	}

	public void setPaginaAtual(int paginaAtual) {
		this.paginaAtual = paginaAtual;

	}

	public List<Integer> getListaAno() {
		return listaAno;

	}

	public void setListaAno(List<Integer> listaAno) {
		this.listaAno = listaAno;

	}

	public List<Integer> getListaNumero() {
		return listaNumero;

	}

	public void setListaNumero(List<Integer> listaNumero) {
		this.listaNumero = listaNumero;

	}

	public String getNomeArquivo() {
		return nomeArquivo;

	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;

	}

	public Norma getNormaSelecionada() {
		return normaSelecionada;
	}

	public void setNormaSelecionada(Norma normaSelecionada) {
		this.normaSelecionada = normaSelecionada;
	}

	public Integer getAnoSelecionado() {
		return anoSelecionado;
	}

	public void setAnoSelecionado(Integer anoSelecionado) {
		this.anoSelecionado = anoSelecionado;
	}

	public Integer getNumeroSelecionado() {
		return numeroSelecionado;
	}

	public void setNumeroSelecionado(Integer numeroSelecionado) {
		this.numeroSelecionado = numeroSelecionado;
	}

	public Date getDataInicioSelecionada() {
		return dataInicioSelecionada;
	}

	public void setDataInicioSelecionada(Date dataInicioSelecionada) {
		this.dataInicioSelecionada = dataInicioSelecionada;
	}

	public Date getDataTerminoSelecionada() {
		return dataTerminoSelecionada;
	}

	public void setDataTerminoSelecionada(Date dataTerminoSelecionada) {
		this.dataTerminoSelecionada = dataTerminoSelecionada;
	}

	public List<String> getListaPalavraChaveSelecionada() {
		return listaPalavraChaveSelecionada;
	}

	public void setListaPalavraChaveSelecionada(List<String> listaPalavraChaveSelecionada) {
		this.listaPalavraChaveSelecionada = listaPalavraChaveSelecionada;
	}

	public String getDescricaoSelecionada() {
		return descricaoSelecionada;
	}

	public void setDescricaoSelecionada(String descricaoSelecionada) {
		this.descricaoSelecionada = descricaoSelecionada;
	}

	public List<String> getListStatus() {
		return listStatus;

	}

	public void setListStatus(List<String> listStatus) {
		this.listStatus = listStatus;

	}

}
