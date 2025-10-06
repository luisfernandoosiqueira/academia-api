package app.dto;

import jakarta.validation.constraints.*;

public record PlanoRequestDTO(
        
		@Size(max = 120) 
		String descr,
        
		@NotNull 
        @PositiveOrZero 
        Double valorMensal
) { }
