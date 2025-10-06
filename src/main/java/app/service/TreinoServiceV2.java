package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.TreinoRequestDTO;
import app.dto.TreinoResponseDTO;
import app.entity.Treino;
import app.exceptions.NegocioException;
import app.exceptions.RecursoNaoEncontradoException;
import app.mapper.TreinoMapper;
import app.repository.AlunoRepository;
import app.repository.TreinoRepository;

@Service
public class TreinoServiceV2 {

	@Autowired
	private TreinoRepository treinoRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private TreinoMapper treinoMapper;
	@Autowired(required = false)
	private List<NotificacaoService> notificacoes;

	public List<TreinoResponseDTO> findAll() {
		return treinoRepository.findAll().stream().map(treinoMapper::toResponseDTO).toList();
	}

	public TreinoResponseDTO findById(Long id) {
		if (id == null)
			throw new NegocioException("Id do treino é obrigatório.");
		Treino t = treinoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Treino não encontrado: " + id));
		return treinoMapper.toResponseDTO(t);
	}

	public TreinoResponseDTO save(TreinoRequestDTO dto) {
		validar(dto);
		Treino t = treinoMapper.toEntity(dto);
		Treino salvo = treinoRepository.save(t);
		notificar("Treino \"" + salvo.getDescr() + "\" salvo.");
		return treinoMapper.toResponseDTO(salvo);
	}

	public TreinoResponseDTO update(Long id, TreinoRequestDTO dto) {
		Treino atual = treinoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Treino não encontrado: " + id));
		if (dto == null)
			throw new NegocioException("Dados do treino são obrigatórios.");
		if (dto.descr() != null)
			atual.setDescr(dto.descr());
		if (dto.nivel() != null)
			atual.setNivel(dto.nivel());
		validar(treinoMapper.toResponseDTO(atual));
		Treino atualizado = treinoRepository.save(atual);
		notificar("Treino \"" + atualizado.getDescr() + "\" atualizado.");
		return treinoMapper.toResponseDTO(atualizado);
	}

	public void delete(Long id) {
		var vinculados = alunoRepository.findByTreinosId(id);
		if (vinculados != null && !vinculados.isEmpty())
			throw new NegocioException("Não pode remover treino vinculado a alunos.");
		Treino t = treinoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Treino não encontrado: " + id));
		treinoRepository.delete(t);
		notificar("Treino \"" + t.getDescr() + "\" removido.");
	}

	private void validar(TreinoRequestDTO dto) {
		if (dto == null)
			throw new NegocioException("Dados do treino são obrigatórios.");
		if (dto.nivel() == null)
			throw new NegocioException("Nível do treino é obrigatório.");
		if (dto.descr() != null && dto.descr().length() > 120)
			throw new NegocioException("Descrição do treino excede 120 caracteres.");
	}

	private void validar(TreinoResponseDTO dto) {
		if (dto.nivel() == null)
			throw new NegocioException("Nível do treino é obrigatório.");
		if (dto.descr() != null && dto.descr().length() > 120)
			throw new NegocioException("Descrição do treino excede 120 caracteres.");
	}

	private void notificar(String msg) {
		if (notificacoes == null)
			return;
		for (NotificacaoService n : notificacoes) {
			try {
				n.mensagem(msg);
			} catch (Exception e) {
				System.out.println("Falha ao enviar mensagem: " + e);
			}
		}
	}
}
