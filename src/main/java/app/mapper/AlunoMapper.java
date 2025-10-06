package app.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import app.dto.AlunoRequestDTO;
import app.dto.AlunoResponseDTO;
import app.entity.Aluno;

@Component
public class AlunoMapper {

    public Aluno toEntity(AlunoRequestDTO dto) {
        Aluno a = new Aluno();
        a.setCpf(dto.cpf());
        a.setNome(dto.nome());
        a.setDataNascimento(dto.dataNascimento());
        if (dto.ativo() != null) a.setAtivo(dto.ativo());
        return a;
    }

    public AlunoResponseDTO toResponseDTO(Aluno a) {
        Long planoId = (a.getPlano() != null ? a.getPlano().getId() : null);
        Set<Long> treinosIds = a.getTreinos() == null
                ? Set.of()
                : a.getTreinos().stream().map(t -> t.getId()).collect(Collectors.toSet());

        return new AlunoResponseDTO(
                a.getId(),
                a.getCpf(),
                a.getNome(),
                a.getDataNascimento(),
                a.isAtivo(),
                planoId,
                treinosIds
        );
    }
}
