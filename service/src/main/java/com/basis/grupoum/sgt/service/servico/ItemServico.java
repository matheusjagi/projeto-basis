package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.repositorio.ItemRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.ItemDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServico {

    private final ItemRepositorio itemRepositorio;
    private final ItemMapper itemMapper;

    public List<ItemDTO> listar(){
        List<Item> itens = itemRepositorio.findAll();
        return itemMapper.toDto(itens);
    }

    public List<ItemDTO> listarItensDisponiveis(boolean disponibilidade){
        List<Item> itens = itemRepositorio.findAllByDisponibilidade(disponibilidade);
        return itemMapper.toDto(itens);
    }

    public List<ItemDTO> listarItensPorNome(String nome){
        List<Item> itens = itemRepositorio.findByNomeContaining(nome);
        return itemMapper.toDto(itens);
    }

    public List<ItemDTO> listarItensPorCategoria(Long categoriaDtoId){
        List<Item> itens = itemRepositorio.findAllByCategoriaId(categoriaDtoId);
        return itemMapper.toDto(itens);
    }

    public List<ItemDTO> listarItensPorUsuario(Long usuarioDtoId){
        List<Item> itens = itemRepositorio.findAllByUsuarioId(usuarioDtoId);
        return itemMapper.toDto(itens);
    }

    public ItemDTO obterPorId(Long id){
        Item item = itemRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Item não encontrado"));

        return itemMapper.toDto(item);
    }

    public ItemDTO salvar(ItemDTO itemDTO){
        Item item = itemMapper.toEntity(itemDTO);
        String teste = "qual";
        item.setFoto(Base64.getDecoder().decode(teste.getBytes(StandardCharsets.UTF_8)));
        itemRepositorio.save(item);
        return itemMapper.toDto(item);
    }

    public ItemDTO atualizar(ItemDTO itemDTO){
        Item item = itemMapper.toEntity(itemDTO);
        itemRepositorio.save(item);
        return itemMapper.toDto(item);
    }

    public void deletar(Long idItem){
        itemRepositorio.deleteById(idItem);
    }

}
