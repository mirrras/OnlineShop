package com.gmail.merikbest2015.ecommerce.controller;

import com.gmail.merikbest2015.ecommerce.constants.Pages;
import com.gmail.merikbest2015.ecommerce.constants.PathConstants;
import com.gmail.merikbest2015.ecommerce.dto.request.PerfumeRequest;
import com.gmail.merikbest2015.ecommerce.service.OrderService;
import com.gmail.merikbest2015.ecommerce.service.PerfumeService;
import com.gmail.merikbest2015.ecommerce.service.UserService;
import com.gmail.merikbest2015.ecommerce.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping(PathConstants.ADMIN)
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final PerfumeService perfumeService;
    private final UserService userService;
    private final OrderService orderService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/perfumes")
    public String getPerfumes(Pageable pageable, Model model) {
        controllerUtils.processModel(null, model, perfumeService.getPerfumes(pageable));
        return Pages.ADMIN_PERFUMES;
    }

    @GetMapping("/perfume/{perfumeId}")
    public String getPerfume(@PathVariable Long perfumeId, Model model) {
        model.addAttribute("perfume", perfumeService.getPerfumeById(perfumeId));
        return Pages.ADMIN_EDIT_PERFUME;
    }

    @PostMapping("/edit/perfume")
    public String editPerfume(@Valid PerfumeRequest perfume, BindingResult bindingResult, Model model,
                              @RequestParam("file") MultipartFile file) {
        if (controllerUtils.validateInputFields(bindingResult, model, "perfume", perfume)) {
            return Pages.ADMIN_EDIT_PERFUME;
        }
        perfumeService.savePerfume(perfume, file);
        return "redirect:/" + Pages.ADMIN_PERFUMES;
    }

    @GetMapping("/add/perfume")
    public String addPerfume() {
        return Pages.ADMIN_ADD_PERFUME;
    }

    @PostMapping("/add/perfume")
    public String addPerfume(@Valid PerfumeRequest perfume, BindingResult bindingResult, Model model,
                             @RequestParam("file") MultipartFile file) {
        if (controllerUtils.validateInputFields(bindingResult, model, "perfume", perfume)) {
            return Pages.ADMIN_ADD_PERFUME;
        }
        perfumeService.savePerfume(perfume, file);
        return Pages.ADMIN_ADD_PERFUME;
    }

    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrder(orderId));
        return Pages.ORDER;
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return Pages.ORDERS;
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return Pages.ADMIN_ALL_USERS;
    }

    @GetMapping("/user/{userId}")
    public String getUserById(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("orders", orderService.getOrdersByUserId(userId));
        return Pages.ADMIN_USER_DETAIL;
    }
}
