package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.collection.UIData;
import soft.co.books.domain.repository.UIDataRepository;


/**
 * Service class for managing UIData.
 */
@Service
@Transactional
public class UIDataService extends CustomBaseService<UIData, String> {

    private final Logger log = LoggerFactory.getLogger(UIDataService.class);

    private final UIDataRepository uiDataRepository;

    public UIDataService(UIDataRepository uiDataRepository) {
        super(uiDataRepository);
        this.uiDataRepository = uiDataRepository;
    }

    public UIData createUIData(UIData uiData) {
        log.debug("Created Information for UIData: {}", uiData);
        return uiDataRepository.save(uiData);
    }

    /**
     * Update all information for a specific uiData, and return the modified uiData.
     *
     * @param uiData uiData to update
     * @return updated uiData
     */
    public UIData updateUIData(UIData uiData) {
        UIData data = uiDataRepository.findById(uiData.getId()).get();
        if (data != null) {
            data.setAddress(uiData.getAddress());
            data.setEmail(uiData.getEmail());
            data.setMainTextEnglish(uiData.getMainTextEnglish());
            data.setMainTextSpanish(uiData.getMainTextSpanish());
            data.setNameSpanish(uiData.getNameSpanish());
            data.setNameEnglish(uiData.getNameEnglish());
            data.setPhone(uiData.getPhone());
            data.setTwitter(uiData.getTwitter());
            data.setFacebook(uiData.getFacebook());
            data.setInstagram(uiData.getInstagram());
            data.setGoogle(uiData.getGoogle());

            return uiDataRepository.save(data);
        } else {
            throw new CustomizeException(Constants.ERR_NOT_FOUND);
        }
    }

    public void delete(String id) {
        uiDataRepository.findById(id).ifPresent(uiData -> {
            uiDataRepository.delete(uiData);
            log.debug("Deleted uiData: {}", uiData);
        });
    }
}
