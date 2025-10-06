package app.mapper;

import app.dto.PlanoRequestDTO;
import app.dto.PlanoResponseDTO;
import app.entity.Plano;
import org.springframework.stereotype.Component;

@Component
public class PlanoMapper {

    public Plano toEntity(PlanoRequestDTO dto) {
        Plano p = new Plano();
        p.setDescr(dto.descr());
        p.setValorMensal(dto.valorMensal());
        return p;
    }

    public PlanoResponseDTO toResponseDTO(Plano p) {
        return new PlanoResponseDTO(
                p.getId(),
                p.getDescr(),
                p.getValorMensal()
        );
    }
}
