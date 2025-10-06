package app.mapper;

import app.dto.TreinoRequestDTO;
import app.dto.TreinoResponseDTO;
import app.entity.Treino;
import org.springframework.stereotype.Component;

@Component
public class TreinoMapper {

    public Treino toEntity(TreinoRequestDTO dto) {
        Treino t = new Treino();
        t.setDescr(dto.descr());
        t.setNivel(dto.nivel());
        return t;
    }

    public TreinoResponseDTO toResponseDTO(Treino t) {
        return new TreinoResponseDTO(
                t.getId(),
                t.getDescr(),
                t.getNivel()
        );
    }
}
