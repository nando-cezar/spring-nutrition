package br.edu.ifba.nutrition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/comments")
@Tag(name = "Comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping
    @Operation(summary = "Save only one comment")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201", 
                description = "Saved with success", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "406", 
                description = "Not Acceptable", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<CommentResponseDto> save(
            @Parameter(description = "New comment body content to be created")
            @RequestBody CommentRequestDto data,
            UriComponentsBuilder builder){

        return commentService.save(data)
            .map(record -> {
                var uri = builder.path("/comments/{id}").buildAndExpand(record.id()).toUri();
                return ResponseEntity.created(uri).body(record);
            }).orElse(ResponseEntity.badRequest().build());
    } 

    @GetMapping("/tip")
    @Operation(summary = "Retrieve comment by TIP ID")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<List<CommentResponseDto>> findByTidId(
            @Parameter(description = "Tip Id to be searched")
            @RequestParam(required = false) Long tipId
    ){
        var data = commentService.findByTipId(tipId).get();
        var isEmpty = data.isEmpty();     
        return isEmpty ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    } 

    @GetMapping("/user")
    @Operation(summary = "Retrieve comment by USER ID")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<List<CommentResponseDto>> findByUserId(
            @Parameter(description = "User Id to be searched")
            @RequestParam(required = false) Long userId
    ){
        var data = commentService.findByUserId(userId).get();
        var isEmpty = data.isEmpty();     
        return isEmpty ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    } 

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve comment by id")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<CommentResponseDto> findById(
            @Parameter(description = "Comment Id to be searched")
            @PathVariable Long id
    ){
        return commentService.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update only one comment")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Updated with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<CommentResponseDto> update(
            @Parameter(description = "Comment Id to be updated")
            @PathVariable Long id,
            @Parameter(description = "Comment Elements/Body Content to be updated")
            @RequestBody CommentRequestDto data
    ){
        return commentService.findById(id)
            .map(record -> {
                var dataSaved = commentService.update(id, data).orElseThrow(() -> new IllegalStateException("Operation to find user and hint failed."));
                return ResponseEntity.ok().body(dataSaved);
            }).orElse(ResponseEntity.notFound().build());
    } 
    
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete only one comment")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Deleted with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CommentResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<CommentResponseDto> deleteById(
            @Parameter(description = "Comment Id to be deleted")
            @PathVariable Long id
    ){
        return commentService.findById(id)
            .map(record -> {
                commentService.deleteById(id);
                return ResponseEntity.ok().body(record);
            }).orElse(ResponseEntity.notFound().build());
    }
}
