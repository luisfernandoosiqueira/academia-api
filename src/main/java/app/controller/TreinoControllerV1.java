package app.controller;

import app.entity.Treino;
import app.service.TreinoServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/academia/treinos")
public class TreinoControllerV1 {

    @Autowired
    private TreinoServiceV1 treinoService;

    @PostMapping
    public ResponseEntity<Treino> salvar(@RequestBody Treino treino) {
        Treino salvo = treinoService.save(treino);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treino> atualizar(@PathVariable Long id, @RequestBody Treino dados) {
        Treino atualizado = treinoService.update(id, dados);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<Treino>> listarTodos() {
        List<Treino> lista = treinoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treino> buscarPorId(@PathVariable Long id) {
        Treino treino = treinoService.findById(id);
        return ResponseEntity.ok(treino);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        treinoService.delete(id);
        return ResponseEntity.noContent().build(); 
    }
}
