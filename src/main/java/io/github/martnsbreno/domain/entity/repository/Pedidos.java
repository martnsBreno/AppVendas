package io.github.martnsbreno.domain.entity.repository;

import io.github.martnsbreno.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
}
