package app.dto;

import app.enums.NivelTreino;
import jakarta.validation.constraints.*;

public record TreinoRequestDTO(
        
		@Size(max = 120) 
		String descr,
        
		@NotNull 
        NivelTreino nivel
        
) { }
