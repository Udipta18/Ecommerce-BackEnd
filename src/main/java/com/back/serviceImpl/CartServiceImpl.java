package com.back.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.back.exception.ResourceNotFoundException;
import com.back.models.Cart;
import com.back.models.CartItem;
import com.back.models.Product;
import com.back.models.User;
import com.back.payload.CartDto;
import com.back.payload.ItemRequest;
import com.back.repo.CartRepository;
import com.back.repo.ProductRepository;
import com.back.repo.UserRepository;
import com.back.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private ModelMapper map;

	@Override
	public CartDto addItem(ItemRequest item, String userName) {
		int productId = item.getProductId();
		int quantity = item.getQuantity();

		if (quantity <= 0) {
			throw new ResourceNotFoundException("Invalid Quantity !!");
		}

		User user = this.userRepository.findByEmail(userName)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));

		Product product = this.productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));

		if (!product.isStock()) {
			throw new ResourceNotFoundException("Product is out of stock");
		}

		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);
		cartItem.setTotalProductPrice();

		Cart cart = user.getCart();

		if (cart == null) {
			// we will create new cart
			cart = new Cart();
			cart.setUser(user);

		}

		Set<CartItem> items = cart.getItems();

		AtomicReference<Boolean> flag = new AtomicReference<>(false);

		List<CartItem> newItems = items.stream().map(i -> {
			if (i.getProduct().getProductId() == productId) {
				i.setQuantity(quantity);
				i.setTotalProductPrice();
				flag.set(true);
			}
			return i;
		}).collect(Collectors.toList());

		if (flag.get() == true) {
			items.clear();
			items.addAll(newItems);
		} else {
			cartItem.setCart(cart);
			items.add(cartItem);
		}

		Cart save = this.cartRepo.save(cart);

		return this.map.map(save, CartDto.class);
	}

	@Override
	public CartDto get(String userName) {

		User user = this.userRepository.findByEmail(userName)
				.orElseThrow(() -> new ResourceNotFoundException("username not found"));

		Cart cart = this.cartRepo.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException(" cart not found with mentioned user"));

		return this.map.map(cart, CartDto.class);
	}

	@Override
	public CartDto removeItem(String username, int productId) {
  
		  User user = userRepository.findByEmail(username).orElseThrow(() ->new ResourceNotFoundException("use not found"));
		  
		  Cart cart = user.getCart();
		  
		  Set<CartItem> items = cart.getItems();
		  
		  boolean removeIf = items.removeIf( item -> item.getProduct().getProductId()==productId);
		
		  Cart save = cartRepo.save(cart);
		   
		return this.map.map(save,CartDto.class);
	}

}
