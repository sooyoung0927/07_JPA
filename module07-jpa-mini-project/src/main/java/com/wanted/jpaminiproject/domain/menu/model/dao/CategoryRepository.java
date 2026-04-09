package com.wanted.jpaminiproject.domain.menu.model.dao;

import com.wanted.jpaminiproject.domain.menu.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /*comment
    *  해당 메서드는 JPA 쿼리 메서드가 아니다
    *  내가 직접 만든 메서드이다
    *  Join 이 여러 개거나 조건이 복잡한 경우 JPA 쿼리메서드로 작성하게 되면
    *  세밀하게 조절이 안 되는 경우가 있다
    *  위 상황에서는 우이가 적접 sql구문을 작성할 수 았다
    *  - jpql : entity 캘르스를 대상으로 sql 구문을 작성한 것
    *  - native query : 실제 SQL 구문을 작성하는 것, From 식제테이뱌ㄹ먕  */

    @Query(value = "select * from tbl_category order by category_code", nativeQuery = true)
    List<Category> findAllCategory();
}


