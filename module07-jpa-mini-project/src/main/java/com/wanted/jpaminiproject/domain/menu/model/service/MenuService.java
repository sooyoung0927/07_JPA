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

    public List<MenuDTO> findMenuByPrice(int menuPrice) {

        // 엔터티 등장
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);

        System.out.println("menuList = " + menuList);

        return menuList.stream()
                .map(menu -> modelMapper.map(menu,MenuDTO.class))
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> findAllCategory() {
        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

    }
}
