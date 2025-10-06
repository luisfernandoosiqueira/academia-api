package app.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.AlunoRequestDTO;
import app.dto.AlunoResponseDTO;
import app.entity.Aluno;
import app.entity.Plano;
import app.entity.Treino;
import app.exceptions.NegocioException;
import app.exceptions.RecursoNaoEncontradoException;
import app.mapper.AlunoMapper;
import app.repository.AlunoRepository;
import app.repository.PlanoRepository;
import app.repository.TreinoRepository;

@Service
public class AlunoServiceV2 {

	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private PlanoRepository planoRepository;
	@Autowired
	private TreinoRepository treinoRepository;
	@Autowired
	private AlunoMapper alunoMapper;
	@Autowired(required = false)
	private List<NotificacaoService> notificacoes;

	public List<AlunoResponseDTO> findAll() {
		List<Aluno> lista = alunoRepository.findAll();
		return lista.stream().map(alunoMapper::toResponseDTO).toList();
	}

	public AlunoResponseDTO findById(Long id) {
		if (id == null)
			throw new NegocioException("Id do aluno é obrigatório.");
		Aluno aluno = alunoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + id));
		return alunoMapper.toResponseDTO(aluno);
	}

	public AlunoResponseDTO save(AlunoRequestDTO dto) {
		validar(dto, false);

		Aluno a = alunoMapper.toEntity(dto);
		if (dto.planoId() != null) {
			Plano p = planoRepository.findById(dto.planoId())
					.orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado: " + dto.planoId()));
			a.setPlano(p);
		}
		if (dto.treinosIds() != null && !dto.treinosIds().isEmpty()) {
			List<Treino> treinos = treinoRepository.findAllById(dto.treinosIds());
			a.getTreinos().addAll(treinos);
		}
		if (dto.ativo() == null)
			a.setAtivo(true);

		Aluno salvo = alunoRepository.save(a);
		notificar("Aluno " + salvo.getNome() + " cadastrado.");
		return alunoMapper.toResponseDTO(salvo);
	}

	public AlunoResponseDTO update(Long id, AlunoRequestDTO dto) {
		Aluno atual = alunoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + id));

		if (dto == null)
			throw new NegocioException("Dados do aluno são obrigatórios.");

		if (dto.nome() != null)
			atual.setNome(dto.nome());
		if (dto.cpf() != null)
			atual.setCpf(dto.cpf());
		if (dto.dataNascimento() != null)
			atual.setDataNascimento(dto.dataNascimento());

		if (dto.planoId() != null) {
			Plano p = planoRepository.findById(dto.planoId())
					.orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado: " + dto.planoId()));
			atual.setPlano(p);
		}

		if (dto.treinosIds() != null) {
			List<Treino> treinos = dto.treinosIds().isEmpty() ? List.of()
					: treinoRepository.findAllById(dto.treinosIds());
			atual.getTreinos().clear();
			atual.getTreinos().addAll(treinos);
		}

		if (dto.ativo() != null)
			atual.setAtivo(dto.ativo());

		validar(atual, true);

		Aluno atualizado = alunoRepository.save(atual);
		notificar("Aluno " + atualizado.getNome() + " atualizado.");
		return alunoMapper.toResponseDTO(atualizado);
	}

	public void delete(Long id) {
		Aluno a = alunoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + id));
		alunoRepository.delete(a);
		notificar("Aluno " + a.getNome() + " removido.");
	}

	public AlunoResponseDTO vincularTreino(Long alunoId, Long treinoId) {
		if (alunoId == null || treinoId == null)
			throw new NegocioException("Ids de aluno e treino são obrigatórios.");

		Aluno aluno = alunoRepository.findById(alunoId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + alunoId));
		Treino treino = treinoRepository.findById(treinoId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Treino não encontrado: " + treinoId));

		boolean jaVinculado = aluno.getTreinos().stream().anyMatch(t -> t.getId().equals(treino.getId()));
		if (!jaVinculado) {
			aluno.getTreinos().add(treino);
			aluno = alunoRepository.save(aluno);
			notificar("Treino \"" + treino.getDescr() + "\" vinculado ao aluno " + aluno.getNome() + ".");
		} else {
			notificar("Treino já vinculado ao aluno " + aluno.getNome() + ".");
		}
		return alunoMapper.toResponseDTO(aluno);
	}

	public AlunoResponseDTO desvincularTreino(Long alunoId, Long treinoId) {
		if (alunoId == null || treinoId == null)
			throw new NegocioException("Ids de aluno e treino são obrigatórios.");

		Aluno aluno = alunoRepository.findById(alunoId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + alunoId));

		boolean removido = aluno.getTreinos().removeIf(t -> t.getId().equals(treinoId));
		aluno = alunoRepository.save(aluno);

		if (removido)
			notificar("Treino (id=" + treinoId + ") desvinculado do aluno " + aluno.getNome() + ".");
		else
			notificar("Treino (id=" + treinoId + ") não estava vinculado ao aluno " + aluno.getNome() + ".");

		return alunoMapper.toResponseDTO(aluno);
	}

	private void validar(AlunoRequestDTO dto, boolean update) {
		if (dto == null)
			throw new NegocioException("Dados do aluno são obrigatórios.");
		if (!update) {
			if (dto.cpf() == null || dto.cpf().isBlank())
				throw new NegocioException("CPF é obrigatório.");
			if (dto.nome() == null || dto.nome().isBlank())
				throw new NegocioException("Nome é obrigatório.");
			if (alunoRepository.existsByCpf(dto.cpf()))
				throw new NegocioException("CPF já cadastrado.");
		} else {
			if (dto.cpf() != null) {
				alunoRepository.findByCpf(dto.cpf()).ifPresent(outro -> {
					throw new NegocioException("CPF já cadastrado.");
				});
			}
		}
	}

	private void validar(Aluno a, boolean update) {
		if (a == null)
			throw new NegocioException("Aluno é obrigatório.");
		if (a.getNome() == null || a.getNome().isBlank())
			throw new NegocioException("Nome é obrigatório.");
		if (a.getCpf() == null || a.getCpf().isBlank())
			throw new NegocioException("CPF é obrigatório.");
		if (!update) {
			if (alunoRepository.existsByCpf(a.getCpf()))
				throw new NegocioException("CPF já cadastrado.");
			a.setAtivo(true);
		} else {
			Set<Long> idsMesmoCpf = alunoRepository.findByCpf(a.getCpf()).stream().map(Aluno::getId)
					.collect(Collectors.toSet());
			if (!idsMesmoCpf.isEmpty() && !idsMesmoCpf.contains(a.getId()))
				throw new NegocioException("CPF já cadastrado.");
		}
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
