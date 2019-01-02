package soft.co.books.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.configuration.storage.StorageService;
import soft.co.books.domain.service.MagazineService;
import soft.co.books.domain.service.dto.MagazineDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Api(description = "Magazine operations")
public class MagazineResource {

    @Autowired
    StorageService storageService;

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
    public MagazineDTO saveMagazine(@RequestPart("magazineDTO") String magazineDTOString, MultipartFile file)
            throws IOException {
        MagazineDTO magazineDTO = new ObjectMapper().readValue(magazineDTOString, MagazineDTO.class);
        String subDirectory;
        if (magazineDTO.getId() == null || magazineDTO.getId().isEmpty()) {
            subDirectory = UUID.randomUUID().toString();
        } else
            subDirectory = magazineDTO.getId();

        if (file != null) {
            storageService.store(file, subDirectory);
            magazineDTO.setImage(MvcUriComponentsBuilder
                    .fromMethodName(UploadResource.class, "getFile", file.getOriginalFilename(), subDirectory)
                    .build().toString());
        }

        if (magazineDTO.getId() == null || magazineDTO.getId().isEmpty()) {
            magazineDTO.setId(subDirectory);
            return magazineService.createMagazine(magazineDTO).get();
        } else
            return magazineService.updateMagazine(magazineDTO).get();
    }

    @DeleteMapping("/deleteMagazine/{magazineId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.MAGAZINE_MANAGEMENT + "\")")
    public ResponseEntity<Void> deleteMagazine(@PathVariable String magazineId) {
        magazineService.delete(magazineId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
