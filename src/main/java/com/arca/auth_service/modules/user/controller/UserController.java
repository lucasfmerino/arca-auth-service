package com.arca.auth_service.modules.user.controller;

import com.arca.auth_service.modules.user.domain.dto.UserDisplayDto;
import com.arca.auth_service.modules.user.domain.dto.UserPasswordUpdateDto;
import com.arca.auth_service.modules.user.domain.dto.UserUpdateDto;
import com.arca.auth_service.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
//@SecurityRequirement(name = "bearer-key")
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    private UserService userService;


    /*
     *  GET USER BY ID
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDisplayDto> getById(@PathVariable UUID id)
    {
        return ResponseEntity.ok(userService.getById(id));
    }


    /*
     *  GET ALL USERS
     *
     */
    @GetMapping()
    public ResponseEntity<Page<UserDisplayDto>> getAll(
            @PageableDefault(size = 10, page = 0, sort = "username", direction = Sort.Direction.ASC)
            Pageable pageable
    )
    {
        return ResponseEntity.ok(userService.getAll(pageable));
    }


    /*
     *  DELETE USER BY ID
     *
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable UUID id)
    {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }



    /*
     *  UPDATE USER
     *
     */
    @PutMapping()
    @Transactional
    public ResponseEntity<UserDisplayDto> update(@RequestBody @Valid UserUpdateDto userUpdateDto)
    {
        return ResponseEntity.ok(userService.update(userUpdateDto));
    }


    /*
     * UPDATE PASSWORD
     *
     */
    @PutMapping("/password")
    @Transactional
    public ResponseEntity<UserDisplayDto> updatePassword(@RequestBody @Valid UserPasswordUpdateDto userPasswordUpdateDto)
    {
        return ResponseEntity.ok(userService.updatePassword(userPasswordUpdateDto));
    }

}
