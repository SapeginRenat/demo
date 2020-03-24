package com.example.demo.service;

import com.example.demo.domain.Person;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repo.PersonRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class PersonService {

    private final static int DEFAULT_VALUE = 0;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    LevelUtil levelUtil;

    @PostConstruct
    public void creatDefault() {
        createUser(Person.builder().id(1).build());
        createUser(Person.builder().id(2).build());
        createUser(Person.builder().id(3).build());
        createUser(Person.builder().id(4).build());
        createUser(Person.builder().id(5).build());
    }

    public Person getUserById(Integer id) {
        return personRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    public void createUser(Person person) {
        person.setExp(DEFAULT_VALUE);
        person.setTotalExp(DEFAULT_VALUE);
        person.setLevel(levelUtil.getLevelByExperience(DEFAULT_VALUE));
        log.debug("Save personage with: id {} exp {} lvl {}", person.getId(), person.getExp(), person.getLevel());
        personRepo.save(person);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Person addExp(int id, int exp) {

        Person person = personRepo.getOne(id);

        int levelExp;

        if ((long) person.getTotalExp() >= Integer.MAX_VALUE || (long) (person.getTotalExp() + exp) >= Integer.MAX_VALUE) {
            log.debug("MAX VALUE: " + Integer.MAX_VALUE);
            log.debug(maxPerson(person).toString());
            return maxPerson(person);
        }

        person.setTotalExp(checkMaxExp(person.getTotalExp(), exp));
        person.setLevel(levelUtil.getLevelByExperience(person.getTotalExp()));

        if (person.getLevel() == levelUtil.getFirstLevel()) {
            levelExp = person.getTotalExp();
        } else {
            levelExp = (person.getTotalExp()) - (levelUtil.getExpByLevel(levelUtil.getFirstLevel()));
        }

        log.debug("total {} - curent {} equals {}", person.getTotalExp(), levelUtil.getExpByLevel(person.getLevel()), levelExp);

        person.setExp(levelExp);
        personRepo.save(person);

        log.debug("Person: id {} exp {} totalExp {} lvl {}", person.getId(), person.getExp(), person.getTotalExp(), person.getLevel());

        return getUserById(person.getId());
    }

    // Generate person with max parameters
    private Person maxPerson(Person person) {
        person.setExp(Integer.MAX_VALUE - levelUtil.getExpByLevel(levelUtil.getMaxLevel()));
        person.setTotalExp(Integer.MAX_VALUE);
        person.setLevel(levelUtil.getMaxLevel());
        return person;
    }

    private int checkMaxExp(int i, int j) {
        if (Long.valueOf(i) + Long.valueOf(j) >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else return i + j;
    }
}
