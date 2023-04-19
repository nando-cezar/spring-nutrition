package br.edu.ifba.nutrition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.nutrition.domain.dto.request.CommentRequestDto;
import br.edu.ifba.nutrition.domain.dto.response.CommentResponseDto;
import br.edu.ifba.nutrition.service.CommentService;

@RestController
@RequestMapping(path = "/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> save(@RequestBody CommentRequestDto data){
        var dataDto = commentService.save(data);
        return new ResponseEntity<CommentResponseDto>(dataDto, HttpStatus.CREATED);
    } 

    @GetMapping("/tip")
    public ResponseEntity<List<CommentResponseDto>> findByTidId(@RequestParam(required = false) Long tipId){
        var data = commentService.findByTipId(tipId).get();
        var isEmpty = data.isEmpty();     
        return isEmpty ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    } 

    @GetMapping("/user")
    public ResponseEntity<List<CommentResponseDto>> findByUserId(@RequestParam(required = false) Long userId){
        var data = commentService.findByUserId(userId).get();
        var isEmpty = data.isEmpty();     
        return isEmpty ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    } 

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id){
        return commentService.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable Long id, @RequestBody CommentRequestDto data){
        return commentService.findById(id)
        .map(record -> {
            var dataSaved = commentService.update(id, data);
            return ResponseEntity.ok().body(dataSaved);
        }).orElse(ResponseEntity.notFound().build());
    } 
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CommentResponseDto> deleteById(@PathVariable Long id){
        return commentService.findById(id)
            .map(record -> {
                commentService.deleteById(id);
                return ResponseEntity.ok().body(record);
            }).orElse(ResponseEntity.notFound().build());
    }
}
