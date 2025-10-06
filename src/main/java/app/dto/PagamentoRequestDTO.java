package app.dto;

import app.enums.FormaPagamento;
import app.enums.StatusPagamento;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record PagamentoRequestDTO(
        
		@NotNull 
		Long alunoId,
       
		@NotNull @PositiveOrZero 
        Double valorPago,
        
        StatusPagamento status,
        
        @NotNull FormaPagamento formaPagamento,
        
        LocalDateTime dataPagamento
        
) { }
