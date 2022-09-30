package io.github.martnsbreno.Service.PedidoServiceImpl;

import io.github.martnsbreno.Service.PedidoService;
import io.github.martnsbreno.domain.entity.Cliente;
import io.github.martnsbreno.domain.entity.ItemPedido;
import io.github.martnsbreno.domain.entity.Pedido;
import io.github.martnsbreno.domain.entity.Produto;
import io.github.martnsbreno.domain.entity.repository.Clientes;
import io.github.martnsbreno.domain.entity.repository.ItemsPedido;
import io.github.martnsbreno.domain.entity.repository.Pedidos;
import io.github.martnsbreno.domain.entity.repository.Produtos;
import io.github.martnsbreno.domain.enums.StatusPedido;
import io.github.martnsbreno.exception.PedidoNaoEncontradoException.PedidoNaoEncontradoException;
import io.github.martnsbreno.exception.RegraNegocioException;
import io.github.martnsbreno.domain.dto.ItemPedidoDto;
import io.github.martnsbreno.domain.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientes;
    private final Produtos produtos;
    private final ItemsPedido itemsPedidos;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientes.findById(idCliente).orElseThrow(() ->
                new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidos.saveAll(itemPedido);
        pedido.setItens(itemPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id).map(pedido -> {
            pedido.setStatusPedido(statusPedido);
            return repository.save(pedido);
        }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDto> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Nao é possível realizar um pedido sem items.");
        }

        return items.stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtos.findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
}
