package soft.co.books.domain.service;

import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.ShippingAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.collection.Country;
import soft.co.books.domain.collection.DhlPrice;
import soft.co.books.domain.collection.Document;
import soft.co.books.domain.collection.UIData;
import soft.co.books.domain.service.dto.CartDTO;
import soft.co.books.domain.service.session.CartSession;
import soft.co.books.domain.service.session.ShippingSession;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServices {

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private BookService bookService;
    @Autowired
    private MagazineService magazineService;
    @Autowired
    private UIDataService uiDataService;

    public CartDTO addToCart(CartSession toAdd) {
        int cant = 0;
        boolean exist = false;
        CartDTO cartDTO = new CartDTO();
        List<CartSession> sessionList = (List<CartSession>) httpSession.getAttribute("cartSessionList");

        Document document;
        if (toAdd.getBook())
            document = bookService.findByIdAndVisible(toAdd.getId(), true);
        else
            document = magazineService.findByIdAndVisible(toAdd.getId(), true);

        if (document == null)
            throw new CustomizeException(Constants.ERR_NOT_BOOK);

        toAdd.setPrice(document.getSalePrice());

        if (sessionList == null) {
            cant = 1;
            sessionList = new ArrayList<>();
            sessionList.add(toAdd);
        } else {
            for (CartSession cartSession : sessionList) {
                cant += cartSession.getCant();
                if (cartSession.getId().equals(toAdd.getId())) {
                    cartSession.setCant(toAdd.getCant());
                    cartSession.setPrice(toAdd.getPrice());
                    exist = true;
                }
            }
            if (!exist) {
                cant += 1;
                sessionList.add(toAdd);
            }
        }

        httpSession.setAttribute("cartSessionList", sessionList);
        cartDTO.setCant(cant);
        cartDTO.setExist(exist);
        return cartDTO;
    }

    public CartDTO removeFormCart(CartSession cartSession) {
        int cant = 0;
        CartDTO cartDTO = new CartDTO();
        List<CartSession> sessionList = (List<CartSession>) httpSession.getAttribute("cartSessionList");

        if (sessionList != null) {
            sessionList.remove(cartSession);
            httpSession.setAttribute("cartSessionList", sessionList);
            for (CartSession session : sessionList) {
                cant += session.getCant();
            }
            cartDTO.setCant(cant);
        }
        return cartDTO;
    }

    public List<CartSession> cartSessionList() {
        return (List<CartSession>) httpSession.getAttribute("cartSessionList");
    }

    public int elementsInCart() {
        int cant = 0;
        List<CartSession> sessionList = (List<CartSession>) httpSession.getAttribute("cartSessionList");

        if (sessionList != null) {
            for (CartSession cartSession : sessionList) {
                cant += cartSession.getCant();
            }
        }
        return cant;
    }

    public ShippingSession addShippingInfo(ShippingSession infoDTO) {
        ShippingSession shippingSession = new ShippingSession();
        shippingSession.setLine1(infoDTO.getLine1());
        shippingSession.setLine2(infoDTO.getLine2());
        shippingSession.setCity(infoDTO.getCity());
        shippingSession.setCountry(infoDTO.getCountry());
        shippingSession.setEmail(infoDTO.getEmail());
        shippingSession.setFullName(infoDTO.getFullName());
        shippingSession.setPhone(infoDTO.getPhone());
        shippingSession.setState(infoDTO.getState());
        shippingSession.setPostalCode(infoDTO.getPostalCode());

        httpSession.setAttribute("shipping", shippingSession);
        return shippingSession;
    }

    public ShippingSession getShippingInfo() {
        ShippingSession shippingSession = (ShippingSession) httpSession.getAttribute("shipping");
        return shippingSession;
    }

    public void clearSession() {
        httpSession.setAttribute("shipping", new ShippingSession());
        httpSession.setAttribute("cartSessionList", new ArrayList<>());
    }

    public void clearCartSessionList() {
        httpSession.setAttribute("cartSessionList", new ArrayList<>());
    }

    public Map<String, Object> shippingData(double totalKgs, double shippingCost, ItemList itemList) {
        Map<String, Object> result = new HashMap<>();
        ShippingSession shippingInfo = getShippingInfo();
        if (shippingInfo != null) {
            Country country = shippingInfo.getCountry();
            if (country != null) {
                for (DhlPrice dhlPrice : country.getPriceList()) {
                    if (totalKgs > dhlPrice.getMinKg() && totalKgs <= dhlPrice.getMaxKg()) {
                        shippingCost = dhlPrice.getPrice();
                        break;
                    }
                    if (totalKgs > 20) {
                        shippingCost = country.getPriceList().get(country.getPriceList().size() - 1).getPrice();
                        break;
                    }
                }
            } else
                throw new CustomizeException(Constants.ERR_SERVER);

            double part = 0;
            List<UIData> uiDataList = uiDataService.findAll();
            if (uiDataList.size() > 0) {
                UIData uiData = uiDataList.get(0);
                double total = shippingCost;
                double percent = uiData.getShipmentPercent();
                part = (percent / 100) * total;
            }
            shippingCost = shippingCost + part;

            if (itemList != null) {
                ShippingAddress shippingAddress = new ShippingAddress();
                shippingAddress.setRecipientName(shippingInfo.getFullName());
                shippingAddress.setPhone(shippingInfo.getPhone());
                shippingAddress.setCity(shippingInfo.getCity());
                shippingAddress.setCountryCode(shippingInfo.getCountry().getCode());
                shippingAddress.setLine1(shippingInfo.getLine1());
                shippingAddress.setLine2(shippingInfo.getLine2());
                shippingAddress.setPostalCode(shippingInfo.getPostalCode());
                shippingAddress.setState(shippingInfo.getState() != null ? shippingInfo.getState() : "");
                itemList.setShippingAddress(shippingAddress);
            }
        }
        result.put("totalKgs", totalKgs);
        result.put("shippingCost", shippingCost);
        result.put("itemList", itemList);

        return result;
    }
}
