package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.repositorio.ItemRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.ItemDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<ItemDTO> listarItensDisponiveis(){
        List<Item> itens = itemRepositorio.findAllByDisponibilidade(true);
        return itemMapper.toDto(itens);
    }

    public ItemDTO obterPorId(Long id){
        Item item = itemRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Item não encontrado"));

        return itemMapper.toDto(item);
    }

    public ItemDTO salvar(ItemDTO itemDTO){
        Item item = itemMapper.toEntity(itemDTO);
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
