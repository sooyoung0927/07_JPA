package com.wanted.jpaminiproject.domain.menu.model.service;

import com.wanted.jpaminiproject.domain.menu.model.dao.CategoryRepository;
import com.wanted.jpaminiproject.domain.menu.model.dao.MenuRepository;
import com.wanted.jpaminiproject.domain.menu.model.dto.CategoryDTO;
import com.wanted.jpaminiproject.domain.menu.model.dto.MenuDTO;
import com.wanted.jpaminiproject.domain.menu.model.entity.Category;
import com.wanted.jpaminiproject.domain.menu.model.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 생성자 주입 구문
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    /* 1. 메뉴코드로 특정 메뉴 조회하기  */
    public MenuDTO findMenuByMenuCode(int menuCode) {

        // Entity 등장
        Menu foundMenu = menuRepository.findById(menuCode)
                                       .orElseThrow(IllegalArgumentException::new);

        // map(변환 대상, 변환 할 타입)
        MenuDTO menuDTO = modelMapper.map(foundMenu,MenuDTO.class);

        return menuDTO;
    }

    //가격으로 메뉴 조회
    public List<MenuDTO> findMenuByPrice(int menuPrice) {

        // 엔터티 등장
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);

        System.out.println("menuList = " + menuList);

        return menuList.stream()
                .map(menu -> modelMapper.map(menu,MenuDTO.class))
                .collect(Collectors.toList());
    }

    // 전체 카테고리 조회
    public List<CategoryDTO> findAllCategory() {
        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    // 등록
    @Transactional
    public int registNewMenu(MenuDTO registMenu) {
        // save 시에는 Entity 타입을 넣어야한다
        // 하지만 전달받고 있는 개체 타입은 DTO이기 때문에
        // modelMapper에서 DTO 타입을 Entity타입으로 바꿔준다

        // menuRepository.save(modelMapper.map(registMenu, Menu.class));

        Menu menu = modelMapper.map(registMenu, Menu.class);
        System.out.println("menu = " + menu);
        menuRepository.save(menu);

        return menu.getMenuCode();
    }

    // 수정
    @Transactional
    public void modifyMenuName(int menuCode, String menuName) {

        // 수정 대상 엔터티 객체 찾아오기
        Menu foundMenu = menuRepository.findById(menuCode)
                .orElseThrow(IllegalArgumentException::new); // optional 타입의 예외처리

        System.out.println("영속성 컨텍스트에서 찾아온 foundMenu = " + foundMenu);
        // foundMenu 변수에는 수정 대상 엔터티가 담김

        /* 1. setter 메서드 사용해서 update -> setter 사용은 지양 */
        // foundMenu.setMenuName(menuName);

        /* 2. @Builder 어노테이션을 활용한 update */
        // foundMenu = foundMenu.toBuilder()
        //                      .menuName(menuName).build();
        // 새로운 인스턴스 값이 대입되는 건 영속성 컨텡스트가 아직 새로운 인스턴스를 알 지 못하는 상황이다
        // menuRepository.save(foundMenu); // 재등록

        /* 3. 엔터티 내부에 직접 Builder 패턴을 구현 */
        foundMenu = foundMenu.changeMenuName(menuName).builder();
        menuRepository.save(foundMenu);

    }


    // 전체 메뉴 조회
    public List<MenuDTO> showMenuList() {

        List<Menu> menuList = menuRepository.findAll();

        return menuList.stream()
                .map(menu -> modelMapper.map(menu,MenuDTO.class))
                .collect(Collectors.toList());

    }

    // 메뉴 삭제
    @Transactional
    public void deleteMenu(int menuCode) {
        menuRepository.deleteById(menuCode);
    }
}
