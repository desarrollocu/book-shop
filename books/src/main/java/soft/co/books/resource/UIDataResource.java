package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.domain.collection.UIData;
import soft.co.books.domain.service.UIDataService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "UIData operations")
public class UIDataResource {

    private final UIDataService uiDataService;

    public UIDataResource(UIDataService uiDataService) {
        this.uiDataService = uiDataService;
    }

    @PostMapping("/uiData")
    public UIData getUiData(@RequestBody UIData uiData) {
        if (uiData.getId() != null)
            return uiDataService.findOne(uiData.getId())
                    .map(UIData::new).get();
        else {
            List<UIData> uiDataList = uiDataService.findAll();
            if (uiDataList.size() > 0)
                return uiDataList.get(0);
            else
                return null;
        }
    }

    @PostMapping("/saveUIData")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.UIDATA_MANAGEMENT + "\")")
    public UIData saveUIData(@Valid @RequestBody UIData uiData) {
        if (uiData.getId() == null || uiData.getId().isEmpty())
            return uiDataService.createUIData(uiData);
        else
            return uiDataService.updateUIData(uiData);
    }
}
