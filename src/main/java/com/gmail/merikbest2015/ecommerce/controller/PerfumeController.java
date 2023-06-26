package com.gmail.merikbest2015.ecommerce.controller;

import com.gmail.merikbest2015.ecommerce.dto.request.PerfumeSearchRequest;
import com.gmail.merikbest2015.ecommerce.service.PerfumeService;
import com.gmail.merikbest2015.ecommerce.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.gmail.merikbest2015.ecommerce.constants.PathConstants.PERFUME;

@Controller
@RequiredArgsConstructor
@RequestMapping(PERFUME)
public class PerfumeController {

    private final PerfumeService perfumeService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/{perfumeId}")
    public String getPerfumeById(@PathVariable Long perfumeId, Model model) {
        model.addAttribute("perfume", perfumeService.getPerfumeById(perfumeId));
        return "perfume";
    }

    @GetMapping
    public String getPerfumesByFilterParams(PerfumeSearchRequest request, Model model, Pageable pageable) {
        controllerUtils.processModel(request, model, perfumeService.getPerfumesByFilterParams(request, pageable));
        return "perfumes";
    }

    @GetMapping("/search")
    public String searchPerfumes(PerfumeSearchRequest request, Model model, Pageable pageable) {
        controllerUtils.processModel(request, model, perfumeService.searchPerfumes(request, pageable));
        return "perfumes";
    }
}
