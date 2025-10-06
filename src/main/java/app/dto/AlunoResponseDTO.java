package app.dto;

import java.time.LocalDate;
import java.util.List;

public record AlunoResponseDTO(
        Long id,
        String cpf,
        String nome,
        LocalDate dataNascimento,
        boolean ativo,
        PlanoResponseDTO plano,
        List<TreinoResponseDTO> treinos
) {}
