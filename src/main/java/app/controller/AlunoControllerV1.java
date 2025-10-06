package app.controller;

import app.entity.Aluno;
import app.service.AlunoServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/academia/alunos")
public class AlunoControllerV1 {

    @Autowired
    private AlunoServiceV1 alunoService;

    @PostMapping
    public ResponseEntity<Aluno> salvar(@RequestBody Aluno aluno) {
        Aluno salvo = alunoService.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno dados) {
        Aluno atualizado = alunoService.update(id, dados);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos() {
        List<Aluno> lista = alunoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        Aluno aluno = alunoService.findById(id);
        return ResponseEntity.ok(aluno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build(); 
    }

    @PostMapping("/{alunoId}/treinos/{treinoId}")
    public ResponseEntity<Aluno> vincularTreino(@PathVariable Long alunoId, @PathVariable Long treinoId) {
        Aluno atualizado = alunoService.vincularTreino(alunoId, treinoId);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{alunoId}/treinos/{treinoId}")
    public ResponseEntity<Aluno> desvincularTreino(@PathVariable Long alunoId, @PathVariable Long treinoId) {
        Aluno atualizado = alunoService.desvincularTreino(alunoId, treinoId);
        return ResponseEntity.ok(atualizado);
    }
}
