package com.senlainc.warsaw.tyurin.controller;

import com.senlainc.warsaw.tyurin.dto.AdvertisementDto;
import com.senlainc.warsaw.tyurin.dto.UserDto;
import com.senlainc.warsaw.tyurin.mapper.AdvertisementMapper;
import com.senlainc.warsaw.tyurin.mapper.UserMapper;
import com.senlainc.warsaw.tyurin.service.IAdvertisementService;
import com.senlainc.warsaw.tyurin.service.IUserService;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import lombok.extern.slf4j.Slf4j;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RestController that handles requests to /users url
 * Contains operations with users
 */
@Slf4j
@RestController
@RequestMapping("users")
public class UserController {

    private IUserService userService;
    private IAdvertisementService advertisementService;

    public UserController(IUserService userService,
                          IAdvertisementService advertisementService) {
        this.userService = userService;
        this.advertisementService = advertisementService;
    }

    /**
     * Get user dto by id
     *
     * @param id the user id
     * @return the user dto
     * @throws NotFoundByIdException indicates that user with this id not exist
     */
    @GetMapping("{id}")
    public UserDto find(@PathVariable long id) throws NotFoundByIdException {
        return UserMapper.mapToDto(userService.find(id));
    }

    /**
     *
     * Update of user
     *
     * @param id      the user id
     * @param userDto the advertisement dto
     * @throws NotFoundByIdException indicates that advertisement with this id not exist
     * @throws EntityExistException  indicates that user with this email already exist
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody UserDto userDto) throws NotFoundByIdException, EntityExistException {
        userService.update(id, UserMapper.mapToEntity(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Create new user
     *
     * @param userDto the user dto
     * @return
     * @throws NotFoundByNameException        indicates that user with this email not exist
     * @throws NotFoundByIdException          indicates that user with this id not exist
     * @throws EntityExistException           indicates that user with this email already exist
     */
    @PostMapping("/register")
    public ResponseEntity<Void> create(@Valid @RequestBody UserDto userDto) throws NotFoundByIdException,
            NotFoundByNameException,
            EntityExistException {
        userService.create(UserMapper.mapToEntity(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete user by id
     *
     * @param id the user id
     * @throws NotFoundByIdException  indicates that user with this id not exist
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws NotFoundByIdException {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Add ADMIN role to user roles
     *
     * @param id the user id
     * @return
     * @throws NotFoundByNameException        indicates that user with this email not exist
     * @throws NotFoundByIdException          indicates that user with this id not exist
     */
    @PutMapping("{id}/add-admin-role")
    public ResponseEntity<Void> addAdminRole(@PathVariable long id) throws NotFoundByIdException, NotFoundByNameException {
        userService.addAdminRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete ADMIN role from user roles
     *
     * @param id the user id
     * @return
     * @throws NotFoundByNameException        indicates that user with this email not exist
     * @throws NotFoundByIdException          indicates that user with this id not exist
     */
    @PutMapping("{id}/delete-admin-role")
    public ResponseEntity<Void> deleteAdminRole(@PathVariable long id) throws NotFoundByIdException, NotFoundByNameException {
        userService.deleteAdminRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get sales history from user
     *
     * @param id the user id
     * @return
     */
    @GetMapping("{id}/sales-history")
    public List<AdvertisementDto> findSalesHistory(@PathVariable long id) {
        return advertisementService
                .findSalesHistory(id)
                .stream()
                .map(advertisement -> {
                            AdvertisementDto advertisementDto = AdvertisementMapper.mapToDto(advertisement);
                            advertisementDto.setCustomerId(advertisement.getCustomer().getId());
                            return advertisementDto;
                        }
                )
                .collect(Collectors.toList());
    }
}
