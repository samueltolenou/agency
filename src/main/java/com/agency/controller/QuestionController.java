package com.agency.controller;

import com.agency.dao.QuestionDao;
import com.agency.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/question")
public class QuestionController {
    @Autowired
    private QuestionDao questionDao;

    @GetMapping("/list")
    public List<Question> listQuestion(){
        return questionDao.findAll();
    }


    @GetMapping("/{id}")
    public Optional<Question> getOneQuestion(@PathVariable Long id){
        return questionDao.findById(id);
    }

    @PostMapping("/save")
    public Question saveQ(@RequestBody Question question){
        return questionDao.saveAndFlush(question);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteQ (@PathVariable Long id){
        questionDao.deleteById(id);
        return true;
    }
}
