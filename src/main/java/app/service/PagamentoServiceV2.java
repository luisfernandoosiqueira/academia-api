package app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.PagamentoRequestDTO;
import app.dto.PagamentoResponseDTO;
import app.entity.Aluno;
import app.entity.Pagamento;
import app.enums.StatusPagamento;
import app.exceptions.NegocioException;
import app.exceptions.RecursoNaoEncontradoException;
import app.mapper.PagamentoMapper;
import app.repository.AlunoRepository;
import app.repository.PagamentoRepository;

@Service
public class PagamentoServiceV2 {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private PagamentoMapper pagamentoMapper;
	@Autowired(required = false)
	private List<NotificacaoService> notificacoes;

	public List<PagamentoResponseDTO> findAll() {
		return pagamentoRepository.findAll().stream().map(pagamentoMapper::toResponseDTO).toList();
	}

	public PagamentoResponseDTO findById(Long id) {
		if (id == null)
			throw new NegocioException("Id do pagamento é obrigatório.");
		Pagamento p = pagamentoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento não encontrado: " + id));
		return pagamentoMapper.toResponseDTO(p);
	}

	public PagamentoResponseDTO save(Long alunoId, PagamentoRequestDTO dto) {
		if (alunoId == null)
			throw new NegocioException("Id do aluno é obrigatório.");
		if (dto == null)
			throw new NegocioException("Dados do pagamento são obrigatórios.");

		Aluno aluno = alunoRepository.findById(alunoId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + alunoId));

		validar(dto);

		Pagamento p = pagamentoMapper.toEntity(dto);
		p.setAluno(aluno);

		if (p.getDataPagamento() == null)
			p.setDataPagamento(LocalDateTime.now());
		if (p.getStatus() == null)
			p.setStatus(p.getValorPago() != null && p.getValorPago() > 0.0 ? StatusPagamento.PAGO
					: StatusPagamento.PENDENTE);

		Pagamento salvo = pagamentoRepository.save(p);
		notificar("Pagamento registrado para " + aluno.getNome() + " no valor de " + salvo.getValorPago() + ".");
		return pagamentoMapper.toResponseDTO(salvo);
	}

	public void delete(Long id) {
		Pagamento pag = pagamentoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento não encontrado: " + id));
		pagamentoRepository.delete(pag);
		notificar("Pagamento (id=" + id + ") removido.");
	}

	private void validar(PagamentoRequestDTO dto) {
		if (dto.valorPago() == null || dto.valorPago() < 0.0)
			throw new NegocioException("Valor do pagamento deve ser zero ou positivo.");
		if (dto.formaPagamento() == null)
			throw new NegocioException("Forma de pagamento é obrigatória.");
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
