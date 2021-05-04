package com.basis.grupoum.sgt.service.recurso;

import com.basis.grupoum.sgt.service.servico.OfertaServico;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/oferta")
@RequiredArgsConstructor
public class OfertaRecurso {

    private final OfertaServico ofertaServico;

    @GetMapping
    public ResponseEntity<List<OfertaDTO>> listar(){
        List<OfertaDTO> ofertas = ofertaServico.listar();
        return new ResponseEntity<>(ofertas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaDTO> obterPorId(@PathVariable("id") Long idOferta) {
        OfertaDTO oferta = ofertaServico.obterPorId(idOferta);
        return new ResponseEntity<>(oferta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OfertaDTO> salvar (@RequestBody OfertaDTO ofertaDTO) {
        OfertaDTO oferta = ofertaServico.salvar(ofertaDTO);
        return new ResponseEntity<>(oferta, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<OfertaDTO> atualizar (@RequestBody OfertaDTO ofertaDTO) {
        OfertaDTO oferta = ofertaServico.atualizar(ofertaDTO);
        return new ResponseEntity<>(oferta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long idOferta){
        ofertaServico.deletar(idOferta);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* aceitar
    @PatchMapping("/aceitar/{id}")
    public ResponseEntity<Void> aceitarOferta (@PathVariable("id") Long idOferta){

    }

    //recusar
    @PatchMapping("/recusar/{id}")
    public ResponseEntity<Void> recusarOferta (@PathVariable("id") Long idOferta){

    }*/
}
