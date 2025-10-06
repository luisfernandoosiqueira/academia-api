package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Aluno;
import app.entity.Plano;
import app.entity.Treino;
import app.exceptions.NegocioException;
import app.exceptions.RecursoNaoEncontradoException;
import app.repository.AlunoRepository;
import app.repository.PlanoRepository;
import app.repository.TreinoRepository;

@Service
public class AlunoServiceV1 {

	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private PlanoRepository planoRepository;
	@Autowired
	private TreinoRepository treinoRepository;
	@Autowired(required = false)
	private List<NotificacaoService> notificacoes;

	public List<Aluno> findAll() {
		return alunoRepository.findAll();
	}

	public Aluno findById(Long id) {
		if (id == null)
			throw new NegocioException("Id do aluno é obrigatório.");
		return alunoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + id));
	}

	public Aluno save(Aluno a) {
		validar(a, false);
		if (a.getPlano() != null && a.getPlano().getId() != null) {
			Plano p = planoRepository.findById(a.getPlano().getId()).orElseThrow(
					() -> new RecursoNaoEncontradoException("Plano não encontrado: " + a.getPlano().getId()));
			a.setPlano(p);
		}
		Aluno salvo = alunoRepository.save(a);
		notificar("Aluno " + salvo.getNome() + " cadastrado.");
		return salvo;
	}

	public Aluno update(Long id, Aluno dados) {
		Aluno atual = findById(id);
		if (dados == null)
			throw new NegocioException("Dados do aluno são obrigatórios.");
		if (dados.getNome() != null)
			atual.setNome(dados.getNome());
		if (dados.getCpf() != null)
			atual.setCpf(dados.getCpf());
		if (dados.getDataNascimento() != null)
			atual.setDataNascimento(dados.getDataNascimento());
		if (dados.getPlano() != null && dados.getPlano().getId() != null) {
			Plano p = planoRepository.findById(dados.getPlano().getId()).orElseThrow(
					() -> new RecursoNaoEncontradoException("Plano não encontrado: " + dados.getPlano().getId()));
			atual.setPlano(p);
		}
		validar(atual, true);
		Aluno atualizado = alunoRepository.save(atual);
		notificar("Aluno " + atualizado.getNome() + " atualizado.");
		return atualizado;
	}

	public void delete(Long id) {
		Aluno a = findById(id);
		alunoRepository.delete(a);
		notificar("Aluno " + a.getNome() + " removido.");
	}

	public Aluno vincularTreino(Long alunoId, Long treinoId) {
		if (alunoId == null || treinoId == null)
			throw new NegocioException("Ids de aluno e treino são obrigatórios.");
		Aluno aluno = findById(alunoId);
		Treino treino = treinoRepository.findById(treinoId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Treino não encontrado: " + treinoId));
		boolean jaVinculado = false;
		for (Treino t : aluno.getTreinos()) {
			if (t.getId().equals(treino.getId())) {
				jaVinculado = true;
				break;
			}
		}
		if (!jaVinculado) {
			aluno.getTreinos().add(treino);
			aluno = alunoRepository.save(aluno);
			notificar("Treino \"" + treino.getDescr() + "\" vinculado ao aluno " + aluno.getNome() + ".");
		} else {
			notificar("Treino já vinculado ao aluno " + aluno.getNome() + ".");
		}
		return aluno;
	}

	public Aluno desvincularTreino(Long alunoId, Long treinoId) {
		if (alunoId == null || treinoId == null)
			throw new NegocioException("Ids de aluno e treino são obrigatórios.");
		Aluno aluno = findById(alunoId);

		boolean removido = aluno.getTreinos().removeIf(t -> t.getId().equals(treinoId));

		aluno = alunoRepository.save(aluno);
		if (removido) {
			notificar("Treino (id=" + treinoId + ") desvinculado do aluno " + aluno.getNome() + ".");
		} else {
			notificar("Treino (id=" + treinoId + ") não estava vinculado ao aluno " + aluno.getNome() + ".");
		}
		return aluno;
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
			alunoRepository.findByCpf(a.getCpf()).ifPresent(outro -> {
				if (!outro.getId().equals(a.getId()))
					throw new NegocioException("CPF já cadastrado.");
			});
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
