package com.example.demo.controller;

import com.example.demo.domain.Person;
import com.example.demo.repo.PersonRepo;
import com.example.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class PersonController {

    @Autowired
    public PersonService personService;

    @GetMapping("/personage/{id}")
    public Person getUserById(@PathVariable Integer id) {
        return personService.getUserById(id);
    }

    @PostMapping("/personage/create")
    public void createPerson(@RequestBody Person person) {
        personService.createUser(person);
    }

    @GetMapping("/personage/{id}/add-exp")
    public Person increaseExp(@PathVariable Integer id, @RequestParam Integer exp) {
        return personService.addExp(id, exp);
    }

}
