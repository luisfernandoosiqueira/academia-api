package app.dto;

import app.enums.FormaPagamento;
import app.enums.StatusPagamento;

public record PagamentoRequestDTO(
        Double valorPago,
        FormaPagamento formaPagamento,
        StatusPagamento status
) {}
