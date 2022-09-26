package io.github.martnsbreno.domain.entity.repository;

import io.github.martnsbreno.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
