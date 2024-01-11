package com.softversko.aplikacija.controller;

import com.softversko.aplikacija.model.Item;
import com.softversko.aplikacija.service.AuthService;
import com.softversko.aplikacija.service.ItemService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Paths;

@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    AuthService authService;


    @RequestMapping(value = "/process_item", method = RequestMethod.POST)
    public String processItem(@RequestParam("newimage") MultipartFile multipartFile, Item item) throws Exception {
        if(!multipartFile.isEmpty()) {
            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String fileName = item.getName();
            String userDirectory = Paths.get("")
                    .toAbsolutePath()
                    .toString();
            String saveTo = userDirectory + "/src/main/resources/static/images/" + fileName + "." + extension;

            item.setImage(fileName + "." + extension);
            multipartFile.transferTo(new File(saveTo));
        } else if(item.getId()!=null) {
            item.setImage(itemService.getById(item.getId()).getImage());
        }
        itemService.addItem(item);
        return "redirect:/";
    }

    @RequestMapping("/item/{id}")
    public ModelAndView showItem(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("item");
        Item item = itemService.getById(id);
        mav.addObject("item",item);

        if(authService.isAuthenticated())
            mav.addObject("username",authService.getUsername());

        return mav;
    }

    @RequestMapping(value = "/delete_item", method = RequestMethod.POST)
    public String deleteItem(@RequestParam("id") Long id) throws Exception {
        itemService.deleteItem(id);
        return "redirect:/";
    }

}
