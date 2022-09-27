package io.github.martnsbreno.Service;

import io.github.martnsbreno.domain.entity.Pedido;
import io.github.martnsbreno.restController.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}
