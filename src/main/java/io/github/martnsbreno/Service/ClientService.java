package io.github.martnsbreno.Service;

import io.github.martnsbreno.Repository.ClientsRepository;
import io.github.martnsbreno.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientsRepository repository;

    @Autowired
    public ClientService (ClientsRepository repository) {
        this.repository = repository;
    }
    public void salvarCliente (Client cliente) {
        validarCliente(cliente);
        this.repository.persistir(cliente);
    }

    public void validarCliente(Client client) {
        //aplicando validaçoes
    }
}
