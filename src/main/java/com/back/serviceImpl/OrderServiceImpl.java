package com.back.serviceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.exception.ResourceNotFoundException;
import com.back.models.Cart;
import com.back.models.CartItem;
import com.back.models.Order;
import com.back.models.OrderItem;
import com.back.models.User;
import com.back.payload.EmailService;
import com.back.payload.OrderDto;
import com.back.payload.OrderRequest;
import com.back.repo.CartRepository;
import com.back.repo.OrderRepo;
import com.back.repo.UserRepository;
import com.back.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private EmailService emailService;


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public OrderDto createOrder(OrderRequest request, String username) {

		User user = this.userRepository.findByEmail(username).orElseThrow(ResourceNotFoundException::new);

		String address = request.getAddress();
		int cartId = request.getCartId();

		Cart cart = this.cartRepository.findById(cartId).orElseThrow(ResourceNotFoundException::new);

		Set<CartItem> items = cart.getItems();
		Order order = new Order();
		AtomicReference<Double> totalOrderPrice = new AtomicReference<>(0.0);
		Set<OrderItem> orderItems = items.stream().map((cartItem) -> {

			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalProductPrice(cartItem.getTotalProductPrice());
			orderItem.setOrder(order);
			totalOrderPrice.set(totalOrderPrice.get() + orderItem.getTotalProductPrice());

			//
			int productId = orderItem.getProduct().getProductId();
			// product:- fetch

			// update the product quantity

			// save the product

			return orderItem;

		}).collect(Collectors.toSet());

		order.setItems(orderItems);
		order.setBillingAddress(address);
		order.setPaymentStatus("NOT PAID");
		order.setOrderAmount(totalOrderPrice.get());
		order.setOrderCreated(new Date());
		order.setOrderDelivered(null);
		order.setOrderStatus("CREATED");
		order.setUser(user);

		Order savedOrder = this.orderRepo.save(order);

		cart.getItems().clear();

		this.cartRepository.save(cart);

		return this.mapper.map(savedOrder, OrderDto.class);

	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, int orderId) {
		Order order = this.orderRepo.findById(orderId).orElseThrow(ResourceNotFoundException::new);
		order.setOrderStatus(orderDto.getOrderStatus());
		order.setBillingAddress(orderDto.getBillingAddress());
		order.setPaymentStatus(orderDto.getPaymentStatus());
		String email = order.getUser().getEmail();
		String name = order.getUser().getName();
		double orderAmount = order.getOrderAmount();
		String readRegEmailBody = this.readRegEmailBody(name, orderAmount);
		emailService.sendEmail(email,readRegEmailBody,
				"Thank For Shopping With Us");
		Order save = this.orderRepo.save(order);
		return this.mapper.map(save, OrderDto.class);
	}

	@Override
	public void deleteOrder(int orderId) {

		Order order = this.orderRepo.findById(orderId).orElseThrow(ResourceNotFoundException::new);
		this.orderRepo.delete(order);

	}

	@Override
	public List<OrderDto> getAll() {
		// TODO Auto-generated method stub
		List<Order> findAll = this.orderRepo.findAll();
		List<OrderDto> list = findAll.stream().map(i -> this.mapper.map(i, OrderDto.class))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public OrderDto get(int orderId) {
		Order order = this.orderRepo.findById(orderId).orElseThrow(ResourceNotFoundException::new);
		return this.mapper.map(order, OrderDto.class);
	}

	@Override
	public List<OrderDto> getOrderOfUser(String username) {
		User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found with given user"));
        List<Order> ordersOfUser = this.orderRepo.findByUser(user);
        return ordersOfUser.stream().map(order -> this.mapper.map(order, OrderDto.class)).collect(Collectors.toList());
		
	}
	
	private String readRegEmailBody(String name,double orderAmount) {
		String fileName="payment-successful-email.txt";
		String url="";
		String mailBody=null;
		try {
			FileReader fr=new FileReader(fileName);
			BufferedReader bf=new BufferedReader(fr);
			
			
			StringBuffer sb=new StringBuffer();
			
			String line=bf.readLine();
			while(line!=null) {
				sb.append(line);
				line=bf.readLine();
			}
			bf.close();
			
		 mailBody = sb.toString();
		 mailBody=mailBody.replace("{FULLNAME}", name);
		 mailBody=mailBody.replace("{ORDER-AMOUNT}", orderAmount+"");
		
		 
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return mailBody;
	}

}
