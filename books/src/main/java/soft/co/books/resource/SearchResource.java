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
import soft.co.books.domain.service.dto.SearchDTO;

@RestController
@RequestMapping("/api")
@Api(description = "Search operations")
public class SearchResource {

    private final SearchService searchService;

    public SearchResource(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/searchBook")
    public PageResultDTO<BookDTO> searchBook(@RequestBody SearchDTO searchDTO, Pageable pageable) {
        return searchService.bookSearch(searchDTO, pageable);
    }

    @PostMapping("/searchMagazine")
    public PageResultDTO<BookDTO> searchMagazine(@RequestBody SearchDTO searchDTO, Pageable pageable) {
        return searchService.magazineSearch(searchDTO, pageable);
    }
}
