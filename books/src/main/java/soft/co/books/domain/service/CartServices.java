package soft.co.books.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft.co.books.domain.collection.Document;
import soft.co.books.domain.service.dto.CartDTO;
import soft.co.books.domain.service.session.CartSession;
import soft.co.books.domain.service.session.ShippingSession;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServices {

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private BookService bookService;
    @Autowired
    private MagazineService magazineService;

    public CartDTO addToCart(CartSession toAdd) {
        int cant = 0;
        boolean exist = false;
        CartDTO cartDTO = new CartDTO();
        List<CartSession> sessionList = (List<CartSession>) httpSession.getAttribute("cartSessionList");

        Document document;
        if (toAdd.getBook())
            document = bookService.findOne(toAdd.getId()).get();
        else
            document = magazineService.findOne(toAdd.getId()).get();

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
        shippingSession.setAddress(infoDTO.getAddress());
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
}
