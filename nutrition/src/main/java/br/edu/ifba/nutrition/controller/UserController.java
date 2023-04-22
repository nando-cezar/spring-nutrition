package br.edu.ifba.nutrition.controller;

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
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.nutrition.domain.dto.request.UserRequestDto;
import br.edu.ifba.nutrition.domain.dto.response.UserResponseDto;
import br.edu.ifba.nutrition.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path = "/users")
@Tag(name = "Users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Save only one user")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201", 
                description = "Saved with success", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "406", 
                description = "Not Acceptable", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<UserResponseDto> save(@Parameter(description = "New user body content to be created") @RequestBody UserRequestDto data){
        var dataDto = userService.save(data);
        return new ResponseEntity<UserResponseDto>(dataDto, HttpStatus.CREATED);
    } 

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve user by id")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<UserResponseDto> findById(@Parameter(description = "User Id to be searched") @PathVariable Long id){
        return userService.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update only one user")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Updated with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<UserResponseDto> update(@Parameter(description = "User Id to be updated") @PathVariable Long id, @Parameter(description = "User Elements/Body Content to be updated") @RequestBody UserRequestDto data){
        return userService.findById(id)
        .map(record -> {
            var dataSaved = userService.update(id, data);
            return ResponseEntity.ok().body(dataSaved);
        }).orElse(ResponseEntity.notFound().build());
    } 
    
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete only one user")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Deleted with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponseDto.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponseDto.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<UserResponseDto> deleteById(@Parameter(description = "User Id to be deleted") @PathVariable Long id){
        return userService.findById(id)
            .map(record -> {
                userService.deleteById(id);
                return ResponseEntity.ok().body(record);
            }).orElse(ResponseEntity.notFound().build());
    }
}
