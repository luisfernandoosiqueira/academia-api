package app.dto;

import java.time.LocalDate;
import java.util.Set;

public record AlunoResponseDTO(
        Long id,
        String cpf,
        String nome,
        LocalDate dataNascimento,
        Boolean ativo,
        Long planoId,
        Set<Long> treinosIds
) { }
