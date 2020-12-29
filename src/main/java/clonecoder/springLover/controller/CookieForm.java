package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Cart;
import lombok.Getter;
import lombok.ToString;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Getter
@ToString
public class CookieForm {
    private Long productId;
    private int quantity;
    private String tempId;

    public static CookieForm create(Long productId, int quantity, String tempId) {
        CookieForm cookieForm = new CookieForm();
        cookieForm.productId = productId;
        cookieForm.quantity = quantity;
        cookieForm.tempId = tempId;
        return cookieForm;
    }

    public static void setTempIdToCookie(HttpServletRequest request,
                                         HttpServletResponse response) {
        if(CookieForm.getTempIdFromCookie(request).equals("")) {
            Cookie cookie = new Cookie("tempId", UUID.randomUUID().toString());
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public static String getTempIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("tempId")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    public static List<CookieForm> parseCookie(String str) {
        List<CookieForm> list = new CopyOnWriteArrayList<>();

        String[] split = str.split("/");
        for(String item : split) {
            String[] parts = item.split("--");
            list.add(CookieForm.create(Long.parseLong(parts[0]), Integer.parseInt(parts[1]), parts[2]));
        }
        return list;
    }

    public static String makeCookie(List<CookieForm> list) {
        String str = "";

        for(CookieForm item : list) {
            str += item;
        }
        return str;
    }

    public static void setCartToCookie(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Long productId,
                                       int quantity) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies) {
            List<CookieForm> cookieForms = new ArrayList<>();
            if(cookie.getName().equals("bm_sv")) {
                cookieForms.addAll(parseCookie(cookie.getValue()));
            }

            boolean bool = true;
            for(CookieForm cookieForm: cookieForms) {
                if(cookieForm.getProductId().equals(productId)) {
                    cookieForm.quantity += quantity;
                    bool = false;
                    break;
                }
            }
            if(bool) {
                cookieForms.add(CookieForm.create(productId, quantity, getTempIdFromCookie(request)));
            }
            Cookie cartCookie = new Cookie("bm_sv", makeCookie(cookieForms));
            cartCookie.setPath("/");
            cartCookie.setMaxAge(-1);
            response.addCookie(cartCookie);
        }
    }

    public static List<CookieForm> getCartFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("bm_sv")) {
                    String value = cookie.getValue();
                    return parseCookie(value);
                }
            }
        }
        return new ArrayList<CookieForm>();
    }

    public static List<CookieForm> getCartFromCookieAndRemove(HttpServletRequest request,
                                                              HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    continue;
                }
                if (cookie.getName().equals("bm_sv")) {
                    String value = cookie.getValue();
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return parseCookie(value);
                }
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return new ArrayList<CookieForm>();
    }

    public static void adjustCartToCookie(HttpServletRequest request,
                                          HttpServletResponse response,
                                          Long productId,
                                          int quantity) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies) {
            List<CookieForm> cookieForms = new ArrayList<>();
            if(cookie.getName().equals("bm_sv")) {
                cookieForms.addAll(parseCookie(cookie.getValue()));
            }

            boolean bool = true;
            for(CookieForm cookieForm: cookieForms) {
                if(cookieForm.getProductId().equals(productId)) {
                    cookieForm.quantity = quantity;
                    bool = false;
                    break;
                }
            }
            if(bool) {
                cookieForms.add(CookieForm.create(productId, quantity, getTempIdFromCookie(request)));
            }
            Cookie cartCookie = new Cookie("bm_sv", makeCookie(cookieForms));
            cartCookie.setPath("/");
            cartCookie.setMaxAge(-1);
            response.addCookie(cartCookie);
        }
    }

    public static void deleteCartToCookie(HttpServletRequest request,
                                          HttpServletResponse response,
                                          List<Long> productIds) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies) {
            List<CookieForm> cookieForms = new ArrayList<>();
            if(cookie.getName().equals("bm_sv")) {
                cookieForms.addAll(parseCookie(cookie.getValue()));

                for(Long productId : productIds) {
                    for(CookieForm cookieForm : cookieForms) {
                        if(cookieForm.getProductId().equals(productId)) {
                            cookieForm = null;
                            break;
                        }
                    }
                }
                String operatedString = makeCookie(cookieForms);
                cookie.setValue(operatedString);
                response.addCookie(cookie);
            }
        }
    }

    @Override
    public String toString() {
        return  productId + "--" + quantity + "--" + tempId + "/";
    }
}
