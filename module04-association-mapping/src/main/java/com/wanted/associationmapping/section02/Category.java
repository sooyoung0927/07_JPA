package com.wanted.associationmapping.section02;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
//@ToString
@Entity(name="section02category")
@Table(name="tbl_category")
public class Category {

    @Id
    @Column(name="category_code")
    private int categoryCode;

    @Column(name="category_name")
    private String categoryName;

    @Column(name="ref_category_code")
    private Integer refCategoryCode;

    /*comment
    *  @OneToMany
    *  어노테이션의 경우 1개의 카테고리를  find 할 때
    *  자식 == 필드로 가지고 있는 menuList 도 같이 영속성 컨텍스트에서 꺼내와야한다
    *  OneToMany의 경우 성능상 굉장히 주의해야 한다
    *  카테고리만 궁금할 뿐인데
    *  해당 카테고리에 해당하는 여러 개의 메뉴를 조회하는 select 구문이 동작하기 때문이다  */

    /*comment
    *  Cascade 옵션
    *  - 영속성 전파 옵션
    *  - ALL : 카테고리 저장 시 해당 카에고리에 속산 매뉴도 함께 저장
    *  - PERSIST : 카테고리 저장 시 메뉴도 저장
    *  - MERGE : 카테고리 병합 시 메뉴도 병합
    *  - REFRESH : 카테고리 새로 고침 시 메뉴도 새로 고침
    *  - NON : 영속성 전파 없음
    *  .
    *  ★ Fetch 옵션
    *  - Lazy 로딩 : 게으른 로딩
    *   - Category 조회 시 menuList 를 조회하지 않고 실제로 menuList가 필요한 시점에 select 쿼리문 동작
    *  - Eager 로딩 : 이른 로딩
    *   - Category 조회 시 menuList 를 바로 조회한다
    *  @OneToMany -> fetch 기본 값 -> Lazy
    *  @ManyToOne -> fetch 가본 값 -> Eager
    *  .
    *  Orphan Removal 옵션
    *  - orphanRemoval= true;
    *  - 해당 속성은 특정 카테고리를 제거하게 되면 해당 카테고리를 가지고 있는 메뉴들도 DB에서 함께 삭제된다
    *  - 부모 엔터티와 연관된 자식 엔터티는 부모가 삭제되면 고아 데이터가 되기 때문에 자동으로 삭제하기 위함이다
    * */

    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name="category_code")
    private List<Menu> menuList = new ArrayList<>();


}
