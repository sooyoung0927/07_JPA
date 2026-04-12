package com.wanted.jpaminiproject.domain.menu.controller;

import com.wanted.jpaminiproject.domain.menu.model.dto.CategoryDTO;
import com.wanted.jpaminiproject.domain.menu.model.dto.MenuDTO;
import com.wanted.jpaminiproject.domain.menu.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
// 필드에 final 키워드가 붙은 친구들을 자동으로 생성자 주입을 해주는 어노테이션
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

     private final MenuService menuService;

    //13을 메뉴 코드로서 쓰겠다
    @GetMapping("/{menuCode}")
    public ModelAndView findByMenuByPathVariable(@PathVariable int menuCode, ModelAndView mv){

        MenuDTO findMenu = menuService.findMenuByMenuCode(menuCode);

        mv.addObject("result",findMenu);
        mv.setViewName("menu/detail");

        return mv;
    }


    @GetMapping("/querymethod")
    public void queryPage(){
    }

    @GetMapping("/search")
    public ModelAndView findByMenuPrice(@RequestParam int menuPrice, ModelAndView mv){

        System.out.println("사용자가 입력한 메뉴 가격 = " + menuPrice);

        List<MenuDTO> menuList = menuService.findMenuByPrice(menuPrice);

        mv.addObject("menuList",menuList);
        mv.addObject("price",menuPrice);
        mv.setViewName("menu/searchResult");
        return mv;
    }

    @GetMapping("/regist")
    public String regist(){
        return "menu/regist";
    }

    // 해당 메서드는 비동기 방식으로 펭이지를 리넡하는 것이 아닌
    // 데이터만 리턴할 것이다
    @GetMapping("/category")
    /*comment
    *  ResponseBody 를 붙이게 되면 웹 페이지에 Json 형태로 데이터를 리턴하게 된ㄷ아
    *  Json은 js 객체 표기법으로 우리 Java 클래스와 비슷한 역할이라고 보면 된다
    *  @ResponseBody 는 1개의 페이지에 여러 데이터를 표현할 때
    *  1개의 핸들러 메서드에서 여러 데이터를 넣는 것이 아닌 비동기 방식으로
    *  각 핸들러메서드에서 전달되는 값을 조합할 때 유용하게 사용된다
    * */
    @ResponseBody
    public List<CategoryDTO> findCategoryList(){
        return menuService.findAllCategory();
    }

//    삭제 코드
    @GetMapping("/delete")
    public String delete(){
        return "menu/delete";
    }

    @DeleteMapping("delete/{menuCode}")
    @ResponseBody // 이거 없으면 delete 못 함 - json으로만 delete 가능
    public String deleteTestMethod(@PathVariable int menuCode){

        menuService.deleteMenu(menuCode);

        return menuCode+"번 메뉴 삭제 완료";
    }


    // 여거 개의 값을 한꺼번에 -> param여러 개 안 쓰고 ModelAttribute로
    @PostMapping("/regist")
    public ModelAndView registMenu(@ModelAttribute MenuDTO registMenu, ModelAndView mv){
        System.out.println("등록 시 화면에서 넘어오는 값 = " + registMenu);

        int menuCode = menuService.registNewMenu(registMenu);
        System.out.println("Service에서 전달받은 = " + menuCode);

        mv.setViewName("redirect:/menu/"+menuCode);
        return mv;
    }

    @GetMapping("/modify")
    public void modifyPage(){
    }

    @PostMapping("/modify")
    public ModelAndView modifyMenuName(@RequestParam int menuCode, @RequestParam String menuName, ModelAndView mv){

        menuService.modifyMenuName(menuCode,menuName);

        mv.setViewName("redirect:/menu/"+menuCode);

        return mv;
    }

    @GetMapping("/list")
    public ModelAndView showMenuList(ModelAndView mv){

        List<MenuDTO> menuList = menuService.showMenuList();

        mv.addObject("menuList",menuList);
        mv.setViewName("menu/list");

        return mv;
    }



}
