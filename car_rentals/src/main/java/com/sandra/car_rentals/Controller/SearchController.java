
package com.sandra.car_rentals.Controller;

import com.sandra.car_rentals.Services.SearchService;
import com.sandra.car_rentals.Model.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/api/search")
    public SearchResults search(@RequestParam String query) {
        return searchService.search(query);
    }
}