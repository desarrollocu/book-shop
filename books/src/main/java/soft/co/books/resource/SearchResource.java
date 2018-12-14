package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.domain.service.SearchService;
import soft.co.books.domain.service.dto.BookDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

@RestController
@RequestMapping("/api")
@Api(description = "Search operations")
public class SearchResource {

    private final SearchService searchService;

    public SearchResource(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/generalSearch")
    public PageResultDTO<BookDTO> generalSearch(@RequestBody BookDTO bookDTO, Pageable pageable) {
        return searchService.generalSearch(bookDTO, pageable);
    }
}
