package app.controller;

import app.entity.Plano;
import app.service.PlanoServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/academia/planos")
public class PlanoControllerV1 {

    @Autowired
    private PlanoServiceV1 planoService;

    @PostMapping
    public ResponseEntity<Plano> salvar(@RequestBody Plano plano) {
        Plano salvo = planoService.save(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plano> atualizar(@PathVariable Long id, @RequestBody Plano dados) {
        Plano atualizado = planoService.update(id, dados);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<Plano>> listarTodos() {
        List<Plano> lista = planoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plano> buscarPorId(@PathVariable Long id) {
        Plano plano = planoService.findById(id);
        return ResponseEntity.ok(plano);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        planoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
