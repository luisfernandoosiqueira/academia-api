package app.controller;

import app.dto.PlanoRequestDTO;
import app.dto.PlanoResponseDTO;
import app.service.PlanoServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/academia/planos")
public class PlanoControllerV2 {

    @Autowired
    private PlanoServiceV2 planoService;

    @PostMapping
    public ResponseEntity<PlanoResponseDTO> salvar(@RequestBody PlanoRequestDTO dto) {
        PlanoResponseDTO salvo = planoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> atualizar(@PathVariable Long id, @RequestBody PlanoRequestDTO dto) {
        PlanoResponseDTO atualizado = planoService.update(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<PlanoResponseDTO>> listarTodos() {
        List<PlanoResponseDTO> lista = planoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> buscarPorId(@PathVariable Long id) {
        PlanoResponseDTO plano = planoService.findById(id);
        return ResponseEntity.ok(plano);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        planoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
