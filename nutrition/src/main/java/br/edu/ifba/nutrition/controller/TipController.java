package br.edu.ifba.nutrition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.nutrition.domain.dto.request.TipRequestDto;
import br.edu.ifba.nutrition.domain.dto.response.TipResponseDto;
import br.edu.ifba.nutrition.service.TipService;

@RestController
@RequestMapping(path = "/tips")
public class TipController {
    
    @Autowired
    private TipService tipService;

    @PostMapping
    public ResponseEntity<TipResponseDto> save(@RequestBody TipRequestDto data){
        var dataDto = tipService.save(data);
        return new ResponseEntity<TipResponseDto>(dataDto, HttpStatus.CREATED);
    } 

    @GetMapping
    public ResponseEntity<List<TipResponseDto>> find(@RequestParam(required = false) String title){
        var data = tipService.find(title).get();
        var isEmpty = data.isEmpty();     
        return isEmpty ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    } 

    @GetMapping("/{id}")
    public ResponseEntity<TipResponseDto> findById(@PathVariable Long id){
        return tipService.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<TipResponseDto> deleteById(@PathVariable Long id){
        return tipService.findById(id)
            .map(record -> {
                tipService.deleteById(id);
                return ResponseEntity.ok().body(record);
            }).orElse(ResponseEntity.notFound().build());
    }
}
