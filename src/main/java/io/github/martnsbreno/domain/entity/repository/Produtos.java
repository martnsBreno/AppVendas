package io.github.martnsbreno.domain.entity.repository;

import io.github.martnsbreno.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
