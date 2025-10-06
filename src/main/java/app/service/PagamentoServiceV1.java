package app.service;

import app.entity.Aluno;
import app.entity.Pagamento;
import app.enums.StatusPagamento;
import app.exceptions.NegocioException;
import app.exceptions.RecursoNaoEncontradoException;
import app.repository.AlunoRepository;
import app.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoServiceV1 {

    @Autowired private PagamentoRepository pagamentoRepository;
    @Autowired private AlunoRepository alunoRepository;
    @Autowired(required = false) private List<NotificacaoService> notificacoes;

    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();
    }

    public Pagamento findById(Long id) {
        if (id == null) throw new NegocioException("Id do pagamento é obrigatório.");
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento não encontrado: " + id));
    }

    public Pagamento save(Long alunoId, Pagamento p) {
        if (alunoId == null) throw new NegocioException("Id do aluno é obrigatório.");
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + alunoId));
        p.setAluno(aluno);
        validar(p);
        Pagamento salvo = pagamentoRepository.save(p);
        notificar("Pagamento registrado para " + aluno.getNome() + " no valor de " + salvo.getValorPago() + ".");
        return salvo;
    }

    public void delete(Long id) {
        Pagamento pag = findById(id);
        pagamentoRepository.delete(pag);
        notificar("Pagamento (id=" + id + ") removido.");
    }

    private void validar(Pagamento p) {
        if (p == null) throw new NegocioException("Pagamento é obrigatório.");
        if (p.getValorPago() == null || p.getValorPago() < 0.0)
            throw new NegocioException("Valor do pagamento deve ser zero ou positivo.");
        if (p.getDataPagamento() == null) p.setDataPagamento(LocalDateTime.now());
        if (p.getStatus() == null)
            p.setStatus(p.getValorPago() > 0.0 ? StatusPagamento.PAGO : StatusPagamento.PENDENTE);
    }

    private void notificar(String msg) {
        if (notificacoes == null) return;
        for (NotificacaoService n : notificacoes) {
            try { n.mensagem(msg); } catch (Exception e) { System.out.println("Falha ao enviar mensagem: " + e); }
        }
    }
}
