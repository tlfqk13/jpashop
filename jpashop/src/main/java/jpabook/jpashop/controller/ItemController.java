package jpabook.jpashop.controller;

import jpabook.jpashop.item.Book;
import jpabook.jpashop.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/itmes/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId,Model model){
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);

        return "items/updateItemForm";

    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm bookForm) {
        Book book = new Book();

/*        book.setId(bookForm.getId());
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);*/

        itemService.updateItem(itemId,bookForm.getName(),bookForm.getPrice(),bookForm.getStockQuantity());

        return "redirect:/items";

    }
}

/*
*  변경 감지와 병합(merge)
*  준영속 엔티티 - 영속성 컨텍스트가 더는 관리하지 않는 엔티티
*  new로 해서 내가 만든거기 때문에 영속성 컨텍스트가 보고 있지 않다
*  변경감지 == dirty checking
*
*  병합 = 준영속 엔티티를 영속 상태로 변경할 때 사용하는 기능
*  넘어온 필드로 싹 바궈치기함 밀어넣기
*  ******* 병합시 값이 없으면 null로 업데이트 할 위헙도 있다 !!!!!!!!!!
* */























