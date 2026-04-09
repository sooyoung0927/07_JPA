package com.wanted.entitymapping.section01.entity;


/*comment
*  JDBC 프로젝트에서 DB와 접근하는 객체는 DAO라고 표현했었다
*  Spring에서는 JPA를 사용할 때 DAO계층을 @Repository 로 표현한다
*  - Mybatis 기술을 사용하게 되면 @Mapper 라고 표현한다
* */

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {


    /*comment
    *  해당 클래스는 엔터티 메니저를 주입 받아
    *  영속성 컨텍스트가 Entity 클래스를 관리할 수 있도록 한다
    * */
    @PersistenceContext
    private EntityManager manager;


    public void save(Member member) {

        manager.persist(member);

    }
}
