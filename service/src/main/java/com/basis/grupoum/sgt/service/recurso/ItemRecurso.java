package com.basis.grupoum.sgt.service.recurso;

import com.basis.grupoum.sgt.service.servico.ItemServico;
import com.basis.grupoum.sgt.service.servico.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemRecurso {
    private final ItemServico itemServico;

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listar(){
        List<ItemDTO> itens = itemServico.listar();
        return new ResponseEntity<>(itens, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> obterPorId(@PathVariable("id") Long idItem){
        ItemDTO item = itemServico.obterPorId(idItem);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDTO> salvar(@RequestBody ItemDTO itemDTO){
        ItemDTO item = itemServico.salvar(itemDTO);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ItemDTO> atualizar(@RequestBody ItemDTO itemDTO){
        ItemDTO item = itemServico.atualizar(itemDTO);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long idItem){
        itemServico.deletar(idItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
