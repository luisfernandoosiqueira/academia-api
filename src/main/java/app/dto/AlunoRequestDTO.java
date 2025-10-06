package app.dto;

import java.time.LocalDate;
import java.util.List;

public record AlunoRequestDTO(
        String cpf,
        String nome,
        LocalDate dataNascimento,
        Long planoId,
        List<Long> treinosIds,
        Boolean ativo
) {}
