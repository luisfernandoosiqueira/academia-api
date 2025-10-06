package app.dto;

import app.enums.NivelTreino;

public record TreinoResponseDTO(
        
		Long id,
        String descr,
        NivelTreino nivel
        
) { }
