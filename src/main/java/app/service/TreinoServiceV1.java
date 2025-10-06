package app.service;

import app.entity.Treino;
import app.exceptions.NegocioException;
import app.exceptions.RecursoNaoEncontradoException;
import app.repository.AlunoRepository;
import app.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreinoServiceV1 {

    @Autowired private TreinoRepository treinoRepository;
    @Autowired private AlunoRepository alunoRepository;
    @Autowired(required = false) private List<NotificacaoService> notificacoes;

    public List<Treino> findAll() {
        return treinoRepository.findAll();
    }

    public Treino findById(Long id) {
        if (id == null) throw new NegocioException("Id do treino é obrigatório.");
        return treinoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Treino não encontrado: " + id));
    }

    public Treino save(Treino t) {
        validar(t);
        Treino salvo = treinoRepository.save(t);
        notificar("Treino \"" + salvo.getDescr() + "\" salvo.");
        return salvo;
    }

    public Treino update(Long id, Treino dados) {
        Treino atual = findById(id);
        if (dados == null) throw new NegocioException("Dados do treino são obrigatórios.");
        if (dados.getDescr() != null) atual.setDescr(dados.getDescr());
        if (dados.getNivel() != null) atual.setNivel(dados.getNivel());
        Treino atualizado = save(atual);
        notificar("Treino \"" + atualizado.getDescr() + "\" atualizado.");
        return atualizado;
    }

    public void delete(Long id) {
        var vinculados = alunoRepository.findByTreinosId(id);
        if (vinculados != null && !vinculados.isEmpty())
            throw new NegocioException("Não pode remover treino vinculado a alunos.");
        Treino t = findById(id);
        treinoRepository.delete(t);
        notificar("Treino \"" + t.getDescr() + "\" removido.");
    }

    private void validar(Treino t) {
        if (t == null) throw new NegocioException("Treino é obrigatório.");
        if (t.getNivel() == null) throw new NegocioException("Nível do treino é obrigatório.");
        if (t.getDescr() != null && t.getDescr().length() > 120)
            throw new NegocioException("Descrição do treino excede 120 caracteres.");
    }

    private void notificar(String msg) {
        if (notificacoes == null) return;
        for (NotificacaoService n : notificacoes) {
            try { n.mensagem(msg); } catch (Exception e) { System.out.println("Falha ao enviar mensagem: " + e); }
        }
    }
}
