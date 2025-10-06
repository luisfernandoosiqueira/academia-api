package app.controller;

import app.dto.AlunoRequestDTO;
import app.dto.AlunoResponseDTO;
import app.service.AlunoServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/academia/alunos")
public class AlunoControllerV2 {

	@Autowired
	private AlunoServiceV2 alunoService;

	@PostMapping
	public ResponseEntity<AlunoResponseDTO> salvar(@RequestBody AlunoRequestDTO dto) {
		AlunoResponseDTO salvo = alunoService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable Long id, @RequestBody AlunoRequestDTO dto) {
		AlunoResponseDTO atualizado = alunoService.update(id, dto);
		return ResponseEntity.ok(atualizado);
	}

	@GetMapping
	public ResponseEntity<List<AlunoResponseDTO>> listarTodos() {
		List<AlunoResponseDTO> lista = alunoService.findAll();
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable Long id) {
		AlunoResponseDTO aluno = alunoService.findById(id);
		return ResponseEntity.ok(aluno);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		alunoService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{alunoId}/treinos/{treinoId}")
	public ResponseEntity<AlunoResponseDTO> vincularTreino(@PathVariable Long alunoId, @PathVariable Long treinoId) {
		AlunoResponseDTO atualizado = alunoService.vincularTreino(alunoId, treinoId);
		return ResponseEntity.ok(atualizado);
	}

	@DeleteMapping("/{alunoId}/treinos/{treinoId}")
	public ResponseEntity<AlunoResponseDTO> desvincularTreino(@PathVariable Long alunoId, @PathVariable Long treinoId) {
		AlunoResponseDTO atualizado = alunoService.desvincularTreino(alunoId, treinoId);
		return ResponseEntity.ok(atualizado);
	}
}
