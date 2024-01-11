package com.softversko.aplikacija.service;

import com.softversko.aplikacija.model.Item;
import com.softversko.aplikacija.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public void addItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item getById(Long id){
        return itemRepository.getById(id);
    }

    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }

}
