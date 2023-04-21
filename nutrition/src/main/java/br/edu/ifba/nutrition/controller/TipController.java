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

import br.edu.ifba.nutrition.domain.dto.request.TipRequestDto;
import br.edu.ifba.nutrition.domain.dto.response.TipResponseDto;
import br.edu.ifba.nutrition.service.TipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/tips")
@Tag(name = "Tips")
public class TipController {
    
    @Autowired
    private TipService tipService;

    @PostMapping
    @Operation(summary = "Save only one tip")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201", 
                description = "Saved with success", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "406", 
                description = "Not Acceptable", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<TipResponseDto> save(@Parameter(description = "New tip body content to be created") @RequestBody TipRequestDto data){
        var dataDto = tipService.save(data);
        return new ResponseEntity<TipResponseDto>(dataDto, HttpStatus.CREATED);
    } 

    @GetMapping
    @Operation(summary = "Retrieve all hints with or without filter")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful hints", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<List<TipResponseDto>> find(@Parameter(description = "Title for tip to be found (optional)") @RequestParam(required = false) String title){
        var data = tipService.find(title).get();
        var isEmpty = data.isEmpty();     
        return isEmpty ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    } 

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve tip by id")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<TipResponseDto> findById(@Parameter(description = "Tip Id to be searched") @PathVariable Long id){
        return tipService.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update only one tip")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Updated with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<TipResponseDto> update(@Parameter(description = "Tip Id to be updated") @PathVariable Long id, @Parameter(description = "Tip Elements/Body Content to be updated") @RequestBody TipRequestDto data){
        return tipService.findById(id)
        .map(record -> {
            var dataSaved = tipService.update(id, data);
            return ResponseEntity.ok().body(dataSaved);
        }).orElse(ResponseEntity.notFound().build());
    } 
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete only one tip")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Deleted with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TipResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<TipResponseDto> deleteById(@Parameter(description = "Tip Id to be deleted") @PathVariable Long id){
        return tipService.findById(id)
            .map(record -> {
                tipService.deleteById(id);
                return ResponseEntity.ok().body(record);
            }).orElse(ResponseEntity.notFound().build());
    }
}
