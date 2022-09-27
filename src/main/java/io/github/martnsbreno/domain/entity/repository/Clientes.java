package io.github.martnsbreno.domain.entity.repository;

import io.github.martnsbreno.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Clientes extends JpaRepository<Cliente, Integer> {
}
