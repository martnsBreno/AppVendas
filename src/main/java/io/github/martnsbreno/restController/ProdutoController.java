package io.github.martnsbreno.restController;

import io.github.martnsbreno.domain.entity.Produto;
import io.github.martnsbreno.domain.entity.repository.Produtos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private Produtos repository;

    public ProdutoController(Produtos repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    private ResponseEntity<Produto> procura (@PathVariable Integer id) {
        Optional<Produto> produtoEncontrado = repository.findById(id);
        if (produtoEncontrado.isPresent()) {
            return ResponseEntity.ok(produtoEncontrado.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Produto save ( @Valid @RequestBody Produto produto) {
        return repository.save(produto);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update ( @Valid @PathVariable Integer id, @RequestBody Produto produto) {
        Optional<Produto> produto1 = repository.findById(id);
        if (produto1.isPresent()) {
            produto.setId(produto1.get().getId());
            repository.save(produto);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Integer id) {
        Optional<Produto> produtoParaDeletar = repository.findById(id);
        if (produtoParaDeletar.isPresent()) {
            repository.delete(produtoParaDeletar.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
