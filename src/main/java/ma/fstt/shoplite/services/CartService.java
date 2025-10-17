package ma.fstt.shoplite.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ma.fstt.shoplite.dtos.UserSessionResponse;
import ma.fstt.shoplite.entities.Cart;
import ma.fstt.shoplite.entities.CartItem;
import ma.fstt.shoplite.entities.Product;
import ma.fstt.shoplite.entities.User;
import ma.fstt.shoplite.enums.ErrorEnum;
import ma.fstt.shoplite.repositories.interfaces.ICartRepository;
import ma.fstt.shoplite.repositories.interfaces.IProductRepository;
import ma.fstt.shoplite.repositories.interfaces.IUserRepository;
import ma.fstt.shoplite.utils.BusinessException;

import java.util.HashSet;
import java.util.Optional;

@Named
@ApplicationScoped
public class CartService {

    @Inject
    ICartRepository cartRepository;

    @Inject
    IUserRepository userRepository;

    @Inject
    IProductRepository productRepository;

    public void saveOrUpdateCart(UserSessionResponse userFromSession, Long productId, Integer quantity) {
        User user = findUserBySession(userFromSession);
        Cart cart = findOrCreateCartForUser(userFromSession, user);
        Product product = validateAndFetchProduct(productId, quantity);
        addOrUpdateCartItem(cart, product, quantity);
        updateProductInventory(product, product.getInventoryQty() - quantity);
        persistCart(cart);
    }

    public Cart findByUserId(Long id) {
        return cartRepository.findByUserId(id);
    }

    private User findUserBySession(UserSessionResponse sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail());
        if (user == null) {
            throw new BusinessException(ErrorEnum.USER_NOT_FOUND);
        }
        return user;
    }

    private Cart findOrCreateCartForUser(UserSessionResponse sessionUser, User user) {
        Cart cart = findByUserId(sessionUser.getId());
        if (cart == null) {
            cart = createNewCart(user);
        } else if (cart.getCartItems() == null) {
            cart.setCartItems(new HashSet<>());
        }
        return cart;
    }

    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartItems(new HashSet<>());
        Long id = cartRepository.save(cart);
        cart.setId(id);
        return cart;
    }

    private Product validateAndFetchProduct(Long productId, Integer requestedQty) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new BusinessException(ErrorEnum.PRODUCT_NOT_FOUND);
        }

        if (product.getInventoryQty() < requestedQty) {
            throw new BusinessException(ErrorEnum.PRODUCT_INSUFFICIENT_QUANTITY);
        }

        return product;
    }

    private void addOrUpdateCartItem(Cart cart, Product product, Integer quantity) {
        Optional<CartItem> existingItemOpt = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            updateExistingItem(existingItemOpt.get(), product, quantity);
        } else {
            addNewCartItem(cart, product, quantity);
        }
    }

    private void updateExistingItem(CartItem item, Product product, int addedQty) {
        int newQty = addedQty;
        if (newQty > product.getInventoryQty()) {
            throw new BusinessException(ErrorEnum.PRODUCT_INSUFFICIENT_QUANTITY);
        }
        item.setQuantity(newQty);
    }

    private void addNewCartItem(Cart cart, Product product, int qty) {
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(qty);
        cart.getCartItems().add(newItem);
    }

    private void updateProductInventory(Product product, int newQty) {
        product.setInventoryQty(newQty);
        productRepository.save(product);
    }

    private void persistCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void deleteCartItem(UserSessionResponse userFromSession, Long productId) {
        User user = findUserBySession(userFromSession);
        Cart cart = findByUserId(user.getId());

        if (cart == null || cart.getCartItems() == null) {
            throw new BusinessException(ErrorEnum.CART_NOT_FOUND);
        }

        Optional<CartItem> itemOpt = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (itemOpt.isEmpty()) {
            throw new BusinessException(ErrorEnum.PRODUCT_NOT_FOUND_IN_CART);
        }

        CartItem item = itemOpt.get();

        Product product = item.getProduct();
        updateProductInventory(product, product.getInventoryQty() + item.getQuantity());

        cart.getCartItems().remove(item);
        cartRepository.save(cart);
    }

}
