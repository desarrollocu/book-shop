package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.domain.service.MagazineService;
import soft.co.books.domain.service.dto.MagazineDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(description = "Magazine operations")
public class MagazineResource {

    private final MagazineService magazineService;

    public MagazineResource(MagazineService magazineService) {
        this.magazineService = magazineService;
    }

    @PostMapping("/magazines")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.MAGAZINE_LIST + "\")")
    public PageResultDTO<MagazineDTO> getAllMagazines(@RequestBody MagazineDTO magazineDTO, Pageable pageable) {
        return magazineService.findAll(magazineDTO, pageable);
    }

    @PostMapping("/magazine")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.MAGAZINE_MANAGEMENT + "\")")
    public MagazineDTO getMagazine(@RequestBody MagazineDTO magazineDTO) {
        if (magazineDTO.getId() != null)
            return magazineService.findOne(magazineDTO.getId())
                    .map(MagazineDTO::new).get();
        else
            return null;
    }

    @PostMapping("/saveMagazine")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.MAGAZINE_MANAGEMENT + "\")")
    public MagazineDTO saveMagazine(@Valid @RequestBody MagazineDTO magazineDTO) {
        if (magazineDTO.getId() == null || magazineDTO.getId().isEmpty())
            return magazineService.createMagazine(magazineDTO).get();
        else
            return magazineService.updateMagazine(magazineDTO).get();
    }

    @DeleteMapping("/deleteMagazine/{magazineId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.MAGAZINE_MANAGEMENT + "\")")
    public ResponseEntity<Void> deleteMagazine(@PathVariable String magazineId) {
        magazineService.delete(magazineId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
