package io.github.martnsbreno.Service;

import io.github.martnsbreno.domain.entity.Pedido;
import io.github.martnsbreno.domain.enums.StatusPedido;
import io.github.martnsbreno.domain.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
