package app.service;

import app.entity.Plano;
import app.exceptions.NegocioException;
import app.exceptions.RecursoNaoEncontradoException;
import app.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoServiceV1 {

    @Autowired private PlanoRepository planoRepository;
    @Autowired(required = false) private List<NotificacaoService> notificacoes;

    public List<Plano> findAll() {
        return planoRepository.findAll();
    }

    public Plano findById(Long id) {
        if (id == null) throw new NegocioException("Id do plano é obrigatório.");
        return planoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado: " + id));
    }

    public Plano save(Plano p) {
        validar(p);
        Plano salvo = planoRepository.save(p);
        notificar("Plano \"" + salvo.getDescr() + "\" salvo.");
        return salvo;
    }

    public Plano update(Long id, Plano dados) {
        Plano atual = findById(id);
        if (dados == null) throw new NegocioException("Dados do plano são obrigatórios.");
        if (dados.getDescr() != null) atual.setDescr(dados.getDescr());
        if (dados.getValorMensal() != null) atual.setValorMensal(dados.getValorMensal());
        Plano atualizado = save(atual);
        notificar("Plano \"" + atualizado.getDescr() + "\" atualizado.");
        return atualizado;
    }

    public void delete(Long id) {
        Plano p = findById(id);
        planoRepository.delete(p);
        notificar("Plano \"" + p.getDescr() + "\" removido.");
    }

    private void validar(Plano p) {
        if (p == null) throw new NegocioException("Plano é obrigatório.");
        if (p.getValorMensal() == null || p.getValorMensal() < 0.0)
            throw new NegocioException("Valor mensal deve ser zero ou positivo.");
        if (p.getDescr() != null && p.getDescr().length() > 120)
            throw new NegocioException("Descrição do plano excede 120 caracteres.");
    }

    private void notificar(String msg) {
        if (notificacoes == null) return;
        for (NotificacaoService n : notificacoes) {
            try { n.mensagem(msg); } catch (Exception e) { System.out.println("Falha ao enviar mensagem: " + e); }
        }
    }
}
