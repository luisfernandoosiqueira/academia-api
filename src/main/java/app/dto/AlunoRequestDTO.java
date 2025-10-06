package app.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public record AlunoRequestDTO(
        
		@NotBlank @Size(min = 11, max = 14) 
        String cpf,
        
        @NotBlank @Size(min = 2,  max = 120) 
        String nome,
        
        @Past 
        LocalDate dataNascimento,
        
        Long planoId,
        
        Set<Long> treinosIds,
        
        Boolean ativo 
        
) { }
