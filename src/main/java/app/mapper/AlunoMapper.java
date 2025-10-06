package app.mapper;

import app.dto.AlunoRequestDTO;
import app.dto.AlunoResponseDTO;
import app.dto.PlanoResponseDTO;
import app.dto.TreinoResponseDTO;
import app.entity.Aluno;
import org.springframework.stereotype.Component;

import java.util.List;

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
        PlanoResponseDTO planoDto = (a.getPlano() == null)
                ? null
                : new PlanoResponseDTO(
                        a.getPlano().getId(),
                        a.getPlano().getDescr(),
                        a.getPlano().getValorMensal()
                  );

        List<TreinoResponseDTO> treinosDto = (a.getTreinos() == null)
                ? List.of()
                : a.getTreinos().stream()
                    .map(t -> new TreinoResponseDTO(t.getId(), t.getDescr(), t.getNivel()))
                    .toList();

        return new AlunoResponseDTO(
                a.getId(),
                a.getCpf(),
                a.getNome(),
                a.getDataNascimento(),
                a.isAtivo(),
                planoDto,
                treinosDto
        );
    }
}
