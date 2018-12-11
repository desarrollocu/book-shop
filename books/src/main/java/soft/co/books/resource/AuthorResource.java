package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.domain.service.AuthorService;
import soft.co.books.domain.service.dto.AuthorDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(description = "Author operations")
public class AuthorResource {

    private final AuthorService authorService;

    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/authors")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.AUTHOR_LIST + "\")")
    public PageResultDTO<AuthorDTO> getAllAuthors(@RequestBody AuthorDTO authorDTO, Pageable pageable) {
        return authorService.findAll(authorDTO, pageable);
    }

    @PostMapping("/author")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.AUTHOR_MANAGEMENT + "\")")
    public AuthorDTO getAuthor(@RequestBody AuthorDTO authorDTO) {
        if (authorDTO.getId() != null)
            return authorService.findOne(authorDTO.getId())
                    .map(AuthorDTO::new).get();
        else
            return null;
    }

    @PostMapping("/saveAuthor")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.AUTHOR_MANAGEMENT + "\")")
    public AuthorDTO saveAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        if (authorDTO.getId() == null || authorDTO.getId().isEmpty())
            return authorService.createAuthor(authorDTO).get();
        else
            return authorService.updateAuthor(authorDTO).get();
    }

    @DeleteMapping("/deleteAuthor/{authorId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.AUTHOR_MANAGEMENT + "\")")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String authorId) {
        authorService.delete(authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
