package com.wanted.section02;

import jakarta.persistence.*;

/*comment
*  다른 클래스에 동일한 Entity가 있으면 충돌이 나기 때문에
*  현재는 이름을 지정한다 */
@Entity(name = "section02_menu")
// 데이터베이스에 매핑이 될 테이블 이름 설정
@Table(name = "tbl_menu")
public class Menu {

    @Id // PK역할
    @Column(name= "menu_code") // 데이터베이스에 적용될 칼럼명
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 AutoIncrement 제약조건
    private int menuCode;

    @Column(name= "menu_name")
    private String menuName;

    @Column(name= "menu_price")
    private int menuPrice;

    @Column(name= "category_code")
    private int categoryCode;

    @Column(name= "orderable_status")
    private String orderableStatus;


    public Menu() {
    }

    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
