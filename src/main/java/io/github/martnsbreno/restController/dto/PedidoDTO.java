package io.github.martnsbreno.restController.dto;

import io.github.martnsbreno.validation.NotEmptyListPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    @NotNull(message = "o codigo do cliente é obrigatório")
    private Integer cliente;
    @NotNull(message = "o total do pedido é obrigatório")
    private BigDecimal total;
    @NotEmptyListPedido(message = "Pedido nao pode ser realizado sem itens")
    private List<ItemPedidoDto> items;
}
