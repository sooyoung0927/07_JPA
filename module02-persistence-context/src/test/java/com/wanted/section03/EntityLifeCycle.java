package com.wanted.section03;

import com.wanted.section02.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityLifeCycle {

    private static EntityManagerFactory factory;
    private EntityManager manager;

    @BeforeAll
    static void initFactory(){
        factory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    void initManager(){
        manager = factory.createEntityManager();
    }

    @AfterEach
    void closeManager(){
        manager.close();
    }

    @AfterAll
    static void closeFactory(){
        factory.close();
    }

    @Test
    void 비영속_테스트_메서드(){

        /*comment
        *  객체를 생성하면(new)
        *  영속성 컨텍스트는 전혀 관련 없는 비영속 상태이다
        *  */

        // given
        Menu foundMenu = manager.find(Menu.class,1);
        Menu newMenu = new Menu();
        newMenu.setMenuCode(foundMenu.getMenuCode());
        newMenu.setMenuName(foundMenu.getMenuName());
        newMenu.setMenuPrice(foundMenu.getMenuPrice());
        newMenu.setCategoryCode(foundMenu.getCategoryCode());
        newMenu.setOrderableStatus(foundMenu.getOrderableStatus());
        // 자료형 같고 값도 똑같음

        // when
        boolean isTrue = (foundMenu == newMenu);

        // then
        Assertions.assertFalse(isTrue);
        System.out.println("isTrue = " + isTrue);
    }


    @Test
    void 영속성_테스트_메서드(){

        // given
        Menu foundMenu = manager.find(Menu.class,1);
        Menu newMenu = manager.find(Menu.class,1);

        // when
        boolean isTrue = (foundMenu == newMenu);

        // then
        Assertions.assertTrue(isTrue);
//        Assertions.assertFalse(isTrue); -> 이거는 틀린거니까 실패함
        System.out.println("isTrue = " + isTrue);
    }

    @Test
    void 준영속_detach_테스트(){
        // 준영속 : 관리하지 않는
        // given
        Menu foundMenu1 = manager.find(Menu.class,11);
        Menu foundMenu2 = manager.find(Menu.class,12);

        // when
        manager.detach(foundMenu2);  // 여기서 12번은 더이상 manager가 영속성을 가지지 않음
        foundMenu1.setMenuPrice(5000);
        foundMenu2.setMenuPrice(5000);

        // then
        assertEquals(5000,manager.find(Menu.class,11).getMenuPrice());
//        assertEquals(5000,manager.find(Menu.class,12).getMenuPrice());
        // 12번에서는 값이 5000원으로 바뀌지 않아서 false 뜨고 오류남


        // 여기서 commit이나 트랜잭션이 없어서 db에는 반영되지 않음

    }

    @Test
    void 삭제_remove_테스트(){
        /*comment
        *  remove() : 엔터티를 영속성 컨텍스트 및 DB에서 삭제한다
        *  단, 트랜잭션을 제어하지 않으면 영구 반영되지 않는다
        *  */

        Menu foundMenu = manager.find(Menu.class,2);
        // DB을 뒤져서 값을 찾아옴 -> select 문을 돎

        manager.remove(foundMenu);

        Menu refoundMenu = manager.find(Menu.class,2);
        // 영속성 컨텍스트 내에서 그 값을 뒤져 찾으니까 별도의 쿼리문이 없음

        assertEquals(2,foundMenu.getMenuCode());
        assertEquals(null,refoundMenu);
        // 둘 다 성공함
        // 영속성 컨텍스트 내에서 refoundMenu 는 remove로 없앤 값이라서 null 이 뜨는데
        // 이게 db에 반영되지는 않아서
        // 영속성 컨텍스트 내에서만 없는 값인 거임

    }

    @Test
    void 병합_merge_수정_테스트(){
        // 병합 : 다시 영속성 관리를 하게 만드는

        Menu detachMenu = manager.find(Menu.class,2);
        manager.detach(detachMenu);

        detachMenu.setMenuName("을지마라탕");
        Menu refoundMenu = manager.find(Menu.class,2);

        System.out.println("detachMenu.hashCode() = " + detachMenu.hashCode());
        System.out.println("refoundMenu.hashCode() = " + refoundMenu.hashCode());

        manager.merge(detachMenu);

        Menu mergeMenu = manager.find(Menu.class,2);
        System.out.println("mergeMenu.hashCode() = " + mergeMenu.hashCode());

        assertEquals("을지마라탕",mergeMenu.getMenuName());

        /* detach 하면 해시코드가 달라짐 - 다른 임시공간에 잠시 담아둔 느낌
        * detachMenu.hashCode() = 1231232251
        * refoundMenu.hashCode() = 220774932
        * mergeMenu.hashCode() = 220774932
        * 임시로 다른 공간에 보냈다가(=detach) 다시 가져오면(=merge) 원래 있던 곳으로 돌아옴
        * */
    }

}
