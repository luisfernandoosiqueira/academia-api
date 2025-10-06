package app.controller;

import app.dto.PagamentoRequestDTO;
import app.dto.PagamentoResponseDTO;
import app.service.PagamentoServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/academia")
public class PagamentoControllerV2 {

    @Autowired
    private PagamentoServiceV2 pagamentoService;

    @PostMapping("/alunos/{alunoId}/pagamentos")
    public ResponseEntity<PagamentoResponseDTO> salvar(@PathVariable Long alunoId, @RequestBody PagamentoRequestDTO dto) {
        PagamentoResponseDTO salvo = pagamentoService.save(alunoId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/pagamentos")
    public ResponseEntity<List<PagamentoResponseDTO>> listarTodos() {
        List<PagamentoResponseDTO> lista = pagamentoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/pagamentos/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        PagamentoResponseDTO pagamento = pagamentoService.findById(id);
        return ResponseEntity.ok(pagamento);
    }

    @DeleteMapping("/pagamentos/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
