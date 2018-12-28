package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.domain.service.BookService;
import soft.co.books.domain.service.MagazineService;
import soft.co.books.domain.service.dto.BookDTO;
import soft.co.books.domain.service.dto.MagazineDTO;
import soft.co.books.domain.service.dto.PageResultDTO;
import soft.co.books.domain.service.dto.ShowDTO;

@RestController
@RequestMapping("/api")
@Api(description = "General operations")
public class GeneralResource {

    private final BookService bookService;

    private final MagazineService magazineService;

    public GeneralResource(BookService bookService, MagazineService magazineService) {
        this.bookService = bookService;
        this.magazineService = magazineService;
    }

    @PostMapping("/salesBooks")
    public PageResultDTO<BookDTO> mostSalesBooks(Pageable pageable) {
        BookDTO dto = new BookDTO();
        ShowDTO showDTO = new ShowDTO();
        showDTO.setVal(true);
        dto.setToShow(showDTO);
        return bookService.findAll(dto, pageable);
    }

    @PostMapping("/salesMagazines")
    public PageResultDTO<MagazineDTO> mostSalesMagazines(Pageable pageable) {
        MagazineDTO dto = new MagazineDTO();
        ShowDTO showDTO = new ShowDTO();
        showDTO.setVal(true);
        dto.setToShow(showDTO);
        return magazineService.findAll(dto, pageable);
    }
}
