package com.example.gateway.controllers;

import com.example.lib.client.UserClient;
import com.example.lib.dto.user.UserDto;
import com.example.lib.dto.user.UserReadAllDto;
import com.example.lib.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.gateway.constants.Constants.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserClient userClient;

    @Operation(
            summary = "Find all users",
            description = "Find all users with pagination. " +
                    "Default values: page - 0, size - 5. " +
                    "This method available for users with permission: 'READ_ALL'.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully loaded users!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access Denied!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized/Invalid token!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Connection Refused",
                            content = @Content(
                                    mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "page",
                            in = ParameterIn.PATH,
                            schema = @Schema(
                                    requiredMode = Schema.RequiredMode.REQUIRED,
                                    description = "Page number",
                                    type = "Integer",
                                    example = "1"
                            )
                    ),
                    @Parameter(
                            name = "size",
                            in = ParameterIn.PATH,
                            schema = @Schema(
                                    requiredMode = Schema.RequiredMode.REQUIRED,
                                    description = "Size of entities per page",
                                    type = "Integer",
                                    example = "15"
                            )
                    ),
                    @Parameter(
                            name = "sort",
                            in = ParameterIn.PATH,
                            schema = @Schema(
                                    requiredMode = Schema.RequiredMode.REQUIRED,
                                    description = "Sort predicate",
                                    type = "String",
                                    example = "username"
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<UserReadAllDto>> getAll(@RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "size", required = false) Integer size,
                                                       @RequestParam(value = "sort", required = false) String sort,
                                                       @RequestHeader(AUTHORIZATION) String authorizationHeader) {
        log.info("UserController: getAll()");
        return new ResponseEntity<>(userClient.getAll(page, size, sort, authorizationHeader), HttpStatus.OK);
    }

    @Operation(
            summary = "Find one user",
            description = "Find one user by id. " +
                    "This method available for user with id, that used in parameter. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully loaded user!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access Denied!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized/Invalid token!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Connection Refused",
                            content = @Content(
                                    mediaType = "application/json")
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@Parameter(name = "id", example = "1") @PathVariable("id") Long id,
                                                @RequestHeader(AUTHORIZATION) String authorizationHeader) {
        log.info("UserController: getById()");
        return new ResponseEntity<>(userClient.getById(id, authorizationHeader), HttpStatus.OK);
    }

    @Operation(
            summary = "Update one user",
            description = "Update one user by id. " +
                    "This method available for user with id, that used in parameter. " +
                    "This method is used to update all user fields. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated user!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access Denied!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized/Invalid token!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Connection Refused",
                            content = @Content(
                                    mediaType = "application/json")
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Object with all user fields",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )
            )

    )
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@Parameter(name = "id", example = "1") @PathVariable("id") Long id,
                                             @RequestBody @Valid UserDto dto,
                                             @RequestHeader(AUTHORIZATION) String authorizationHeader) {
        userClient.update(id, dto, authorizationHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Delete one user",
            description = "Delete one user by id. " +
                    "This method is available for users with role 'ADMIN' and 'READER'. " +
                    "This method available for user with id, that used in parameter. ",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successfully deleted user!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access Denied!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized/Invalid token!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Connection Refused",
                            content = @Content(
                                    mediaType = "application/json")
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@Parameter(name = "id", example = "1") @PathVariable("id") Long id,
                                             @RequestHeader(AUTHORIZATION) String authorizationHeader) {
        userClient.delete(id, authorizationHeader);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
