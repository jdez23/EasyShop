package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

// convert this class to a REST controller
// only logged in users should have access to these actions

@RestController
@RequestMapping("/cart")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            return shoppingCartDao.getByUserId(user.getId());
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("/products/{productId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart addProductToCart(@PathVariable int productId, Principal principal) {
        try {
            String username = principal.getName();
            User user = userDao.getByUserName(username);
            shoppingCartDao.addToCart(user.getId(), productId, 1);
            return shoppingCartDao.getByUserId(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add product to cart.");
        }
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("/products/{productId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateCartItem(@PathVariable int productId, @RequestBody ShoppingCartItem item, Principal principal) {
        try {
            String username = principal.getName();
            User user = userDao.getByUserName(username);
            shoppingCartDao.updateCartItem(user.getId(), productId, item.getQuantity());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update cart item.");
        }
    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    // POST /cart/products/{productId}
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public void clearCart(Principal principal, HttpServletResponse response) throws IOException {
        try {
            String username = principal.getName();
            User user = userDao.getByUserName(username);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
                return;
            }
            shoppingCartDao.clearCart(user.getId());} catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to clear shopping cart.");
        }
    }
}