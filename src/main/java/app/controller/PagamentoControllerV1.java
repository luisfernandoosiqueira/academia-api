package app.controller;

import app.entity.Pagamento;
import app.service.PagamentoServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/academia")
public class PagamentoControllerV1 {

    @Autowired
    private PagamentoServiceV1 pagamentoService;

    @PostMapping("/alunos/{alunoId}/pagamentos")
    public ResponseEntity<Pagamento> salvar(@PathVariable Long alunoId, @RequestBody Pagamento pagamento) {
        Pagamento salvo = pagamentoService.save(alunoId, pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/pagamentos")
    public ResponseEntity<List<Pagamento>> listarTodos() {
        List<Pagamento> lista = pagamentoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/pagamentos/{id}")
    public ResponseEntity<Pagamento> buscarPorId(@PathVariable Long id) {
        Pagamento pagamento = pagamentoService.findById(id);
        return ResponseEntity.ok(pagamento);
    }

    @DeleteMapping("/pagamentos/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
