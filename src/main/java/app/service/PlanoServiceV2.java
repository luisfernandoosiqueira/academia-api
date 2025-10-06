package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.PlanoRequestDTO;
import app.dto.PlanoResponseDTO;
import app.entity.Plano;
import app.exceptions.NegocioException;
import app.exceptions.RecursoNaoEncontradoException;
import app.mapper.PlanoMapper;
import app.repository.PlanoRepository;

@Service
public class PlanoServiceV2 {

	@Autowired
	private PlanoRepository planoRepository;
	@Autowired
	private PlanoMapper planoMapper;
	@Autowired(required = false)
	private List<NotificacaoService> notificacoes;

	public List<PlanoResponseDTO> findAll() {
		return planoRepository.findAll().stream().map(planoMapper::toResponseDTO).toList();
	}

	public PlanoResponseDTO findById(Long id) {
		if (id == null)
			throw new NegocioException("Id do plano é obrigatório.");
		Plano p = planoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado: " + id));
		return planoMapper.toResponseDTO(p);
	}

	public PlanoResponseDTO save(PlanoRequestDTO dto) {
		validar(dto);
		Plano p = planoMapper.toEntity(dto);
		Plano salvo = planoRepository.save(p);
		notificar("Plano \"" + salvo.getDescr() + "\" salvo.");
		return planoMapper.toResponseDTO(salvo);
	}

	public PlanoResponseDTO update(Long id, PlanoRequestDTO dto) {
		Plano atual = planoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado: " + id));
		if (dto == null)
			throw new NegocioException("Dados do plano são obrigatórios.");
		if (dto.descr() != null)
			atual.setDescr(dto.descr());
		if (dto.valorMensal() != null)
			atual.setValorMensal(dto.valorMensal());
		validar(planoMapper.toResponseDTO(atual)); 
		Plano atualizado = planoRepository.save(atual);
		notificar("Plano \"" + atualizado.getDescr() + "\" atualizado.");
		return planoMapper.toResponseDTO(atualizado);
	}

	public void delete(Long id) {
		Plano p = planoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado: " + id));
		planoRepository.delete(p);
		notificar("Plano \"" + p.getDescr() + "\" removido.");
	}

	private void validar(PlanoRequestDTO dto) {
		if (dto == null)
			throw new NegocioException("Dados do plano são obrigatórios.");
		if (dto.valorMensal() == null || dto.valorMensal() < 0.0)
			throw new NegocioException("Valor mensal deve ser zero ou positivo.");
		if (dto.descr() != null && dto.descr().length() > 120)
			throw new NegocioException("Descrição do plano excede 120 caracteres.");
	}

	private void validar(PlanoResponseDTO dto) {
		if (dto.valorMensal() == null || dto.valorMensal() < 0.0)
			throw new NegocioException("Valor mensal deve ser zero ou positivo.");
		if (dto.descr() != null && dto.descr().length() > 120)
			throw new NegocioException("Descrição do plano excede 120 caracteres.");
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
