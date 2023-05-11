package jpabook.jpashop.service;

import jpabook.jpashop.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

/*    @Transactional
    public void updateItem(Long itemId, Book param){

        Item findItem = itemRepository.findOne(itemId);
        //findItem.change(price,name,stockQuantity); - 변경지점이 엔티티인 명확하게 나타내라
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());

        // 호출할 필요가없다. 찾아왔을때, 영속성 컨텍스트임 == 변경감지
        // itemRepository.save(findItem);

    }*/

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public void updateItem(Long itemId, String name, int price, int stockQuantity) {

        Item findItem = itemRepository.findOne(itemId);

        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

    }
}
