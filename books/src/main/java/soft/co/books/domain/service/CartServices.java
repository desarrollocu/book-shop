package soft.co.books.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.collection.Document;
import soft.co.books.domain.service.dto.CartDTO;
import soft.co.books.domain.service.session.CartSession;

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

    public CartDTO addToCart(CartSession cartSession) {
        boolean elementExist = false;
        CartDTO cartDTO = new CartDTO();

        List<CartSession> sessionList = (List<CartSession>) httpSession.getAttribute("cartSessionList");
        if (sessionList == null) {
            sessionList = new ArrayList<>();
            sessionList.add(cartSession);
            httpSession.setAttribute("cartSessionList", sessionList);
        } else {
            for (CartSession session : sessionList) {
                if (session.getId().equals(cartSession.getId())) {
                    session.setCant(cartSession.getCant());
                    elementExist = true;
                    break;
                }
            }
            if (!elementExist) {
                Document document;
                if (cartSession.getBook())
                    document = bookService.findOne(cartSession.getId()).get();
                else
                    document = magazineService.findOne(cartSession.getId()).get();

                if (document != null)
                    cartSession.setPrice(document.getSalePrice());
                else
                    throw new CustomizeException(Constants.ERR_SERVER);

                sessionList.add(cartSession);
            }

            httpSession.setAttribute("cartSessionList", sessionList);
        }

        int cant = 0;
        for (CartSession session : sessionList) {
            cant += session.getCant();
        }
        cartDTO.setCant(cant);
        cartDTO.setExist(elementExist);
        return cartDTO;
    }

    public CartDTO removeFormCart(CartSession cartSession) {
        CartDTO cartDTO = new CartDTO();
        List<CartSession> sessionList = (List<CartSession>) httpSession.getAttribute("cartSessionList");
        if (sessionList != null) {
            sessionList.remove(cartSession);
            httpSession.setAttribute("cartSessionList", sessionList);

            int cant = 0;
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

    public int elemensInCart() {
        List<CartSession> sessionList = (List<CartSession>) httpSession.getAttribute("cartSessionList");
        int cant = 0;
        if (sessionList != null) {
            for (CartSession cartSession : sessionList) {
                cant += cartSession.getCant();
            }
        }

        return cant;
    }

    public void clearSession() {
        httpSession.removeAttribute("cartSessionList");
    }
}
