package app.controller;

import app.dto.TreinoRequestDTO;
import app.dto.TreinoResponseDTO;
import app.service.TreinoServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/academia/treinos")
public class TreinoControllerV2 {

	@Autowired
	private TreinoServiceV2 treinoService;

	@PostMapping
	public ResponseEntity<TreinoResponseDTO> salvar(@RequestBody TreinoRequestDTO dto) {
		TreinoResponseDTO salvo = treinoService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TreinoResponseDTO> atualizar(@PathVariable Long id, @RequestBody TreinoRequestDTO dto) {
		TreinoResponseDTO atualizado = treinoService.update(id, dto);
		return ResponseEntity.ok(atualizado);
	}

	@GetMapping
	public ResponseEntity<List<TreinoResponseDTO>> listarTodos() {
		List<TreinoResponseDTO> lista = treinoService.findAll();
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TreinoResponseDTO> buscarPorId(@PathVariable Long id) {
		TreinoResponseDTO treino = treinoService.findById(id);
		return ResponseEntity.ok(treino);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		treinoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
