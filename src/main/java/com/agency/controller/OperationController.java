package com.agency.controller;

import com.agency.dao.OperationDao;
import com.agency.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/operation")
public class OperationController {
    @Autowired
    private OperationDao operationDao;

    @GetMapping("/list")
    public List<Operation> listOperation(){
        return operationDao.findAll();
    }


    @GetMapping("/{id}")
    public Optional<Operation> getOneOperation(@PathVariable Long id){
        return operationDao.findById(id);
    }

    @PostMapping("/save")
    public Operation saveOperation (@RequestBody Operation operation){
        return operationDao.saveAndFlush(operation);
    }

    @DeleteMapping("/delete/{numOperation}")
    public boolean deleteOperation (@PathVariable Long numOperation){
        operationDao.deleteById(numOperation);
        return true;
    }
}
