package app.mapper;

import app.dto.PagamentoRequestDTO;
import app.dto.PagamentoResponseDTO;
import app.entity.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public Pagamento toEntity(PagamentoRequestDTO dto) {
        Pagamento p = new Pagamento();
        p.setValorPago(dto.valorPago());
        p.setFormaPagamento(dto.formaPagamento());
        p.setStatus(dto.status());          
        return p;
    }

    public PagamentoResponseDTO toResponseDTO(Pagamento p) {
        Long alunoId = (p.getAluno() != null ? p.getAluno().getId() : null);
        return new PagamentoResponseDTO(
                p.getId(),
                p.getDataPagamento(),
                p.getValorPago(),
                p.getStatus(),
                p.getFormaPagamento(),
                alunoId
        );
    }
}
