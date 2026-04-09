package com.wanted.associationmapping.section02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OneToManyService {

    @Autowired
    private OneToManyRepository repository;

    public Category findCategeory(int categoryCode) {

        Category category = repository.find(categoryCode);

        return category;

    }
}
