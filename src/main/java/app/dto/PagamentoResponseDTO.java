package app.dto;

import app.enums.FormaPagamento;
import app.enums.StatusPagamento;

import java.time.LocalDateTime;

public record PagamentoResponseDTO(
        Long id,
        LocalDateTime dataPagamento,
        Double valorPago,
        StatusPagamento status,
        FormaPagamento formaPagamento,
        Long alunoId
) {}
