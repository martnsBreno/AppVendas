package io.github.martnsbreno.restController;

import io.github.martnsbreno.domain.entity.Cliente;
import io.github.martnsbreno.domain.entity.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/{id}")
    public ResponseEntity getClientById(@PathVariable Integer id) {
        Optional<Cliente> id1 = clientes.findById(id);

        if(id1.isPresent()) {
            return ResponseEntity.ok(id1.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveClient(@RequestBody Cliente cliente) {
        Cliente save = clientes.save(cliente);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable Integer id) {
        Optional<Cliente> byId = clientes.findById(id);

        if (byId.isPresent()) {
            clientes.delete(byId.get());
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("put/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Cliente cliente ) {
        Optional<Cliente> byId = clientes.findById(id);
        if (byId.isPresent()) {
            cliente.setId(byId.get().getId());
            clientes.save(cliente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Cliente> clienteList = clientes.findAll(example);
        return clienteList;
    }
}
