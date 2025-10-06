package app.dto;

import app.enums.NivelTreino;

public record TreinoRequestDTO(
        String descr,
        NivelTreino nivel
) {}
