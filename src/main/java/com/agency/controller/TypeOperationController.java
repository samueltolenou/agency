package com.agency.controller;

import com.agency.dao.TypeOperationDao;
import com.agency.model.TypeOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/type-operation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TypeOperationController {
    
	@Autowired
    private TypeOperationDao typeOperationDao;

    @GetMapping("/list")
    public List<TypeOperation> listTypeOperation(){
        return typeOperationDao.findAll();
    }


    @GetMapping("/{idTypeOperation}")
    public Optional<TypeOperation> getOneTypeOperation(@PathVariable Long idTypeOperation){
        return typeOperationDao.findById(idTypeOperation);
    }

    @PostMapping("/save")
    public TypeOperation saveTypeOpe(@RequestBody TypeOperation typeOperation){
        return typeOperationDao.saveAndFlush(typeOperation);
    }

    @DeleteMapping("/delete/{idTypeOperation}")
    public boolean deleteTypeOperation (@PathVariable Long idTypeOperation){
        typeOperationDao.deleteById(idTypeOperation);
        return true;
    }
}
