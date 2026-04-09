package com.wanted.associationmapping.section02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OneToManyTest {

    @Autowired
    private OneToManyService service;

    @Test
    @DisplayName("1:N 연관관계 객체 그래프 탐색 조회")
    void oneToManyTest(){

        // 카테고리 코드 10번을 전달하면, 10번 카테고리에 속하는 전체 메뉴 조회
        int categoryCode = 10;

        // 10번 카테고리를 조회함과 동시에 그에 해당하는 Menu들 조회
        Category category = service.findCategeory(categoryCode);

//        System.out.println("category.getMenuList() = " + category.getMenuList());
        System.out.println(category);

        // 검증
        Assertions.assertNotNull(category);
    }

}
