package com.capgemini.capstore;

import com.capgemini.capstore.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ListIterator;

@Controller
public class WelcomeController {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	RestTemplate rest;

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/")
	public String getResponse(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);
		System.out.println(request.getSession());
		// if(session != null){
		session = request.getSession(false);
		User user = new User();
		user = (User) session.getAttribute("user");
		if (session != null && user != null) {
			if (user.getRole().equalsIgnoreCase("customer")) {
				return "HomeCustomer";
			} else if (user.getRole().equalsIgnoreCase("merchant")) {
				return "MerchantHome";
			} else {
				return "Admin";
			}
			
		}
		return "Home";

	}

	@RequestMapping(value = "/Login")
	public String getSignUp() {
		return "Login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public String enterSignUpDetails(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);
		User user = new User();
		String email = request.getParameter("email");
		user.setEmailId(email);
		String password = request.getParameter("password");
		user.setPassword(password);
		String role = request.getParameter("a3");
		System.out.println(user.getEmailId() + user.getPassword());
		user.setRole(role);
		session = request.getSession(true);
		session.setAttribute("user", user);
		System.out.println("in login::::" + role);
		String str = rest.postForObject("http://localhost:8088/logIn", user, String.class);

		if (role.equals("CUSTOMER")) {
			Customer customer = rest.postForObject("http://localhost:8088/getCustomerObject", user, Customer.class);
			System.out.println(customer.getCustomerName());
			session.setAttribute("customer", customer);
		}
		if (role.equals("MERCHANT")) {
			Merchant merch = rest.postForObject("http://localhost:8088/getMerchantObject", user, Merchant.class);
			System.out.println(merch.getMerchantName()+"ID"+merch.getMerchantId());
			session.setAttribute("customer", merch);
		}
		if (role.equals("ADMIN")) {
			Admin admin = rest.postForObject("http://localhost:8088/getAdminObject", user, Admin.class);
			System.out.println(admin.getAdminName());
			session.setAttribute("admin", admin);
		}

		return str;
	}

	@RequestMapping(value = "/Ask")
	public String getSignUpDetails() {
		return "Ask";
	}

	@RequestMapping(value = "/ForgotPassword")
	public String forgotPassword() {

		return "ForgotPassword";
	}

	@RequestMapping(value = "/ForgotPasswordd", method = RequestMethod.POST, produces = "application/json")
	public String afterForgotPassword(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);
		User user = new User();
		String email = request.getParameter("n4");
		String securityQuestion = request.getParameter("a4");
		String securityAnswer = request.getParameter("n2");
		user.setEmailId(email);
		user.setSecurityAnswer(securityAnswer);
		user.setSecurityQuestion(securityQuestion);
		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
		String str = rest.postForObject("http://localhost:8088/forgotPassword", user, String.class);
		return str;

	}

	@RequestMapping(value = "/Home", method = RequestMethod.POST, produces = "application/json")
	public String successfullChagePassword(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);
		String password = request.getParameter("n3");
		String confirmPassword = request.getParameter("n4");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		user.setPassword(password);

		if (password.equals(confirmPassword)) {

			String str = rest.postForObject("http://localhost:8088/passwordChangePage", user, String.class);
			return str;

		}

		return "ForgotPasswordConfirmation";
	}

	@RequestMapping(value = "/SignUp", method = RequestMethod.POST, produces = "application/json")
	public String validateSignUpDetails(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);
		User user = new User();
		String email = request.getParameter("a1");
		user.setEmailId(email);
		String password = request.getParameter("a2");
		user.setPassword(password);
		String role = request.getParameter("a3");
		user.setRole(role);
		String securityQuestion = request.getParameter("a4");
		user.setSecurityQuestion(securityQuestion);
		String securityAnswer = request.getParameter("a5");
		user.setSecurityAnswer(securityAnswer);
		String str = rest.postForObject("http://localhost:8088/signUp", user, String.class);
		if (str.equals("ask")) {
			// set msg
		}
		return str;
	}

	@RequestMapping(value = "/Customer_OTP", method = RequestMethod.POST, produces = "application/json")
	public String enterMoreSignUpDetailsCustomer() {

		Customer customer = new Customer();
		String name = request.getParameter("n2");
		String email = request.getParameter("n5");
		String mobileNo = request.getParameter("n4");
		String address = request.getParameter("n8");
		String pincode = request.getParameter("n6");
		customer.setCustomerAddress(address);
		customer.setCustomerEmail(email);
		customer.setCustomerMobileNumber(mobileNo);
		customer.setCustomerName(name);
		customer.setCustomerPincode(pincode);
		String str = rest.postForObject("http://localhost:8088/registerCustomer", customer, String.class);
		return str;

	}

	@RequestMapping(value = "/Merchant_OTP", method = RequestMethod.POST, produces = "application/json")
	public String enterMoreSignUpDetailsMerchant() {

		Merchant merchant = new Merchant();
		String name = request.getParameter("n1");
		String email = request.getParameter("n2");
		String storeName = request.getParameter("n3");
		String mobileNo = request.getParameter("n4");
		String address = request.getParameter("n5");
		merchant.setMerchantAddress(address);
		merchant.setMerchantEmail(email);
		merchant.setMerchantMobileNumber(mobileNo);
		merchant.setMerchantName(name);
		merchant.setMerchantStoreName(storeName);
		String str = rest.postForObject("http://localhost:8088/registerMerchant", merchant, String.class);
		return str;
	}

	@RequestMapping(value = "/ChangePassword")
	public String getChangePasswordPage() {
		return "ChangePassword";
	}

	@RequestMapping("**/**/logOut")
	public String logOut(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);
		session.invalidate();
		return "Home";

	}
	
	@RequestMapping("/admin")
	public String adminLogin(Model model) {
		RestTemplate restTemplate = new RestTemplate();
	Admin admin = restTemplate.getForObject("http://localhost:8088/getAdmin", Admin.class);
		//model.addAttribute("ad", admin);
		session.setAttribute("ad", admin);
		return "Admin";

	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePasword() {

		String str = null;
		String currentPassword = request.getParameter("n2");
		String newPassword = request.getParameter("n3");
		String confirmNewPassword = request.getParameter("n4");
		ChangePasswordDummy dummy = new ChangePasswordDummy();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println(user);
		user.setPassword(confirmNewPassword);
		dummy.setEmail(user.getEmailId());
		dummy.setOldPassword(currentPassword);
		dummy.setNewPassword(newPassword);
		dummy.setRole(user.getRole());
		System.out.println("hii");
		if (newPassword.equals(confirmNewPassword)) {
			str = rest.postForObject("http://localhost:8088/passwordChangePage", user, String.class);
			return str;
		}
		return "Home";
	}

	@RequestMapping("**/HomeCustomer")
	public String openCustomerHome(HttpServletRequest request, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);
		return "HomeCustomer";
	}

	@RequestMapping("/CustomerHome")
	public String showAllProduct(HttpServletRequest request, Model model) {

		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);

		return "Home";
	}

	@RequestMapping("/ProductPage/{productId}")
	public String showProduct(@PathVariable int productId, HttpServletRequest request, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		Product product = restTemplate.getForObject("http://localhost:8088/showProduct/" + productId, Product.class);
		System.out.println("Product display"+product.getProductBrand()+"m::"+product.getProductDiscount());
		model.addAttribute("product", product);
		return "ProductPage";
	}

	
	
	
	
	
	@RequestMapping("/ProductPage/DisplayCart/{productId}")
	public String getResponseDisplayCart(@PathVariable int productId, HttpServletRequest request, ModelMap model) {
		System.out.println("" + productId);
		User customerUser = (User) session.getAttribute("user");
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("User data :" + "**********"+customerUser);
		 int quantity = Integer.parseInt(request.getParameter("quant"));
		Customer customer = rest.postForObject("http://localhost:8088/getCustomerObject", customerUser,Customer.class);
		System.out.println("http://localhost:8088/displayCart/" + productId +"/"+ customer.getCustomerId() + "/" + 2000 + "/" + quantity);
List<Cart> cartList =restTemplate.getForObject("http://localhost:8088/displayCart/" + productId +"/"+ customer.getCustomerId() + "/" + 2000 + "/" + quantity, List.class);
ListIterator<Cart> iterator = cartList.listIterator();
while (iterator.hasNext()) {
	System.out.println("deleting is :" + iterator.next());
}
		/*
		 *  int merchantId =
		 * restTemplate.getForObject("http://localhost:8088/getMerchantId/" + productId,
		 * Integer.class); 
		 */
		model.addAttribute("itemlist", cartList);
		return "DisplayCart";
	}

	@RequestMapping("/RemoveProductFromCart/{cartId}")
	public String removeProductFromCart(@PathVariable int cartId, Model model) {
		User customerUser = (User) session.getAttribute("user");
		RestTemplate restTemplate = new RestTemplate();
		Customer customer = rest.postForObject("http://localhost:8088/getCustomerObject", customerUser, Customer.class);
		restTemplate.getForObject("http://localhost:8088/removeProductFromCart/" + cartId, Void.class);

		List<Cart> cartList = restTemplate.getForObject("http://localhost:8088/getAllCart/" + customer.getCustomerId(),
				List.class);
		model.addAttribute("itemlist", cartList);
		return "DisplayCart";
	}

	@RequestMapping("/ProductPage/DisplayCart/CheckOut")
	public String checkOutProduct( HttpServletRequest request, ModelMap model) {
		
		User customerUser = (User) session.getAttribute("user");
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("User data :" + "**********"+customerUser);
		
		Customer customer = rest.postForObject("http://localhost:8088/getCustomerObject", customerUser,Customer.class);
		List<Cart> cartList = restTemplate.getForObject("http://localhost:8088/getAllCart/" + customer.getCustomerId(),List.class);
		ListIterator<Cart> iterator = cartList.listIterator();
		while (iterator.hasNext()) {
			System.out.println("cart list :" + iterator.next());
		}

		/*
		 *  int merchantId =
		 * restTemplate.getForObject("http://localhost:8088/getMerchantId/" + productId,
		 * Integer.class); 
		 */
		model.addAttribute("itemlist", cartList);
		return "CheckOut";
	}
	
	
	@RequestMapping("/ProductPage/DisplayCart/Purchase")
	public String checktProduct(@RequestParam(value = "n4", required = false) String q, Model model) {
		System.out.println("^^^^^^^^^^^^^^^^^PURCHASED in WELCOME CONTROll^^^^^^^^^^^^^^^^^^^^^^^"+q);
		RestTemplate restTemplate = new RestTemplate();

		User customerUser = (User) session.getAttribute("user");
		System.out.println("^^^^^^^^^^^^^^^^^PURCHASED in WELCOME CONTROll^^^^^^^^^^^^^^^^^^^^^^^"+customerUser);
		
		Customer customer = rest.postForObject("http://localhost:8088/getCustomerObject", customerUser,Customer.class);
		List<Cart> cartList = restTemplate.getForObject("http://localhost:8088/getAllCart/" + customer.getCustomerId(),List.class);
		restTemplate.getForObject("http://localhost:8088/removeProductsFromCartForCustomer/" +customer.getCustomerId(), Void.class);
		

		
		ListIterator<Cart> iterator = cartList.listIterator();
		while (iterator.hasNext()) {
			System.out.println("deleting:" + iterator.next());

		}

		/*
		 *  int merchantId =
		 * restTemplate.getForObject("http://localhost:8088/getMerchantId/" + productId,
		 * Integer.class); 
		 */
		model.addAttribute("itemlist", cartList); 
		return "OrderPlaced";
	}
	
	

	@RequestMapping("/MyOrders")
	public String openMyOrdersPage() {
		return "MyOrders";
	}

	@RequestMapping(value = "/MyOrders", method = RequestMethod.GET)
	public String getOrdersDetailsPage(ModelMap map) {
		User customerUser = (User) session.getAttribute("user");
		RestTemplate restTemplate = new RestTemplate();
		Customer customer = rest.postForObject("http://localhost:8088/getCustomerObject", customerUser, Customer.class);
		List<Order> list = restTemplate.getForObject("http://localhost:8088/viewOrders/" + customer.getCustomerId(),
				List.class);
		map.addAttribute("list", list);
		return "MyOrders";
	}

	@RequestMapping(value = "/ViewWishList", method = RequestMethod.GET)
	public String getWishlistDetailsPage(ModelMap map) {
		RestTemplate restTemplate = new RestTemplate();
		User customerUser = (User) session.getAttribute("user");
		Customer customer = rest.postForObject("http://localhost:8088/getCustomerObject", customerUser, Customer.class);
		List<Order> list = restTemplate.getForObject("http://localhost:8088/getWishList/" + customer.getCustomerId(),
				List.class);
		map.addAttribute("list", list);
		return "ViewWishList";
	}

	@RequestMapping(value = "/ViewWishList/{productId}")
	public String addProductToWishlist(Model model, ModelMap map, @PathVariable int productId) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);
		User customerUser = (User) session.getAttribute("user");
		Customer customer = rest.postForObject("http://localhost:8088/getCustomerObject", customerUser, Customer.class);
		int merchantId = restTemplate.getForObject("http://localhost:8088/getMerchantId/" + productId, Integer.class);
		Wishlist wish = restTemplate.getForObject("http://localhost:8088/addProductToWishList/"
				+ customer.getCustomerId() + "/" + merchantId + "/" + productId, Wishlist.class);
		return "Home";
	}

	@RequestMapping(value = "")
	public String deleteProductFromWishlist(Model model, ModelMap map) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		model.addAttribute("productList", product);
		User customerUser = (User) session.getAttribute("user");
		Customer customer = rest.postForObject("http://localhost:8088/getCustomerObject", customerUser, Customer.class);
		List<Wishlist> wish = restTemplate
				.getForObject("http://localhost:8088/deleteFromWishlist/" + customer.getCustomerId(), List.class);
		map.addAttribute("list", wish);
		return "ViewWishList";
	}

	@RequestMapping(value = "ProductPage/FeedbackParticularProduct/{productId}", method = RequestMethod.GET)
	public String getHomepage(ModelMap map, @PathVariable int productId) {

		RestTemplate rest = new RestTemplate();

		List<ProductFeedback> feedbackList = rest.getForObject("http://localhost:8088/getAll/" + productId, List.class);
		map.addAttribute("list", feedbackList);
		map.addAttribute("productId", productId);
		return "FeedbackProduct";
	}

	@RequestMapping(value = "ProductPage/FeedbackParticularProduct/giveFeedbackParticularProduct/{productId}", method = RequestMethod.GET)
	public String getFeedbackForm(Model map, @PathVariable int productId) {
		map.addAttribute("productId", productId);
		return "FeedbackParticularProduct";
	}

	@RequestMapping(value = "/Merchant")
	public String getmerchantFeedback(ModelMap map) {
		RestTemplate rest = new RestTemplate();

		List<ProductFeedback> MerchantfeedbackList = rest.getForObject("http://localhost:8088/getAllMerchant",
				List.class);
		map.addAttribute("list", MerchantfeedbackList);
		return "FeedbackMerchant";
	}

	@RequestMapping(value = "/ProductPage/MerchantFeedback")
	public String addMerchantFeedback() {
		return "FeedbackParticularMerchant";
	}

	@RequestMapping(value = "ProductPage/FeedbackParticularProduct/giveFeedbackParticularProduct/addProductFeedback/{productId}", method = RequestMethod.GET)
	public String setFeedbackForParticularProduct(HttpServletRequest request, @PathVariable int productId) {

		RestTemplate restTemplate = new RestTemplate();
		String feedback = request.getParameter("feedback");
		String rating = request.getParameter("rating");
		User customerUser = (User) session.getAttribute("user");
		Customer customer = restTemplate.postForObject("http://localhost:8088/getCustomerObject", customerUser,
				Customer.class);

		System.out.println("test:" + customer.getCustomerName());
		System.out.println("feedback: " + feedback + "\n" + "rating: " + rating);

		ProductFeedback str = rest.getForObject("http://localhost:8088/addFeedback/" + feedback + "/" + rating + "/"
				+ productId + "/" + customer.getCustomerId(), ProductFeedback.class);
		System.out.println("testting: " + str);
		return "FeedbackProduct";

	}

	@RequestMapping(value = "/AddProductsByMerchant", method = RequestMethod.GET)
	public String AddProductsByMerchant(HttpServletRequest request) {
		return "AddProductsByMerchant";
	}

	@RequestMapping(value = "/fileinsertmerchant", method = RequestMethod.POST, produces = "application/json")
	public String fileinsertmerchant() {

		Product p = new Product();
		String productName = request.getParameter("n1");
		String productCategory = request.getParameter("n2");
		String productBrand = request.getParameter("n3");
		String productModel = request.getParameter("n4");
		String productType = request.getParameter("n5");
		String productDiscount = request.getParameter("n6");
		String productPrice = request.getParameter("n7");
		String productQuantity = request.getParameter("n8");


		p.setProductName(productName);
		p.setProductCategory(productCategory);
		p.setProductBrand(productBrand);
		p.setProductModel(productModel);
		p.setProductType(productType);
		p.setProductDiscount(productDiscount);
		p.setProductPrice(productPrice);
		p.setProductQuantiy(productQuantity);
		// System.out.println("s:brnd:"+p.getProductBrand()+"scategory:"+p.getProductCategory()+"s:disc::"+
		// p.getProductDiscount()+"s:Model"+p.getProductModel()+"s::name:"+p.getProductName()+"s:price:"+
		// p.getProductPrice()+"s:type:"+p.getProductType());
		String str = rest.postForObject("http://localhost:8088/fileitmerchant", p, String.class);
		return str;

	}

	@RequestMapping(value = "/DeleteProductsByMerchant", method = RequestMethod.GET)
	public String DeleteProductsByMerchant(HttpServletRequest request, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		ListIterator<Product> iterator = product.listIterator();
		while (iterator.hasNext()) {
			System.out.println("deleting is :" + iterator.next());
		}

		model.addAttribute("pro", product);

		return "DeleteProductsByMerchant";
	}

	@RequestMapping(value = "/UpdateProductByMerchant", method = RequestMethod.GET)
	public String UpdateProductByMerchant(HttpServletRequest request) {
		return "UpdateProductByMerchant";
	}

	@RequestMapping(value = "/ShowProductsForMerchant", method = RequestMethod.GET)
	public String ShowProductsForMerchant(HttpServletRequest request, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<Product> product = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
		ListIterator<Product> iterator = product.listIterator();

		// Printing the iterated value
		System.out.println("\nUsing ListIterator:\n");
		while (iterator.hasNext()) {
			System.out.println("Value is : " + iterator.next());
		}

		model.addAttribute("pro", product);

		return "ShowProductsForMerchant";
	}

	@RequestMapping(value = "/CheckOrdersByMerchant", method = RequestMethod.GET)
	public String CheckOrdersByMerchant(HttpServletRequest request) {
		return "CheckOrdersByMerchant";
	}

	@RequestMapping(value = "/DiscountByMerchant", method = RequestMethod.GET)
	public String DiscountByMerchant(HttpServletRequest request) {
		return "DiscountByMerchant";
	}

	@RequestMapping(value = "/MerchantFeedbackMerchant", method = RequestMethod.GET)
	public String MerchantFeedbackMerchant(HttpServletRequest request) {
		return "MerchantFeedbackMerchant";
	}

	@RequestMapping(value = "/MerchantFeedbackProduct", method = RequestMethod.GET)
	public String MerchantFeedbackProduct(HttpServletRequest request) {
		return "MerchantFeedbackProduct";
	}

	 @RequestMapping(value = "/tsearch", method = RequestMethod.GET)
	    public String search(@RequestParam(value = "query", required = false) String q, Model model) {
	      System.out.println("searched for"+q);
	      RestTemplate restTemplate = new RestTemplate();
			List<Product> products = restTemplate.getForObject("http://localhost:8088/showAllProduct", List.class);
			ListIterator<Product> iterator = products.listIterator();
			int productFlag=-1;
			ListIterator<Product> iterator1 = products.listIterator();
			while (iterator.hasNext()) {
				System.out.println("searching ius is :" + iterator.next());
			}

			/*while (iterator.hasNext()) {
				if( iterator.next().getProductName().equals(q))
					productFlag= iterator.next().getProductId();

			}
			if  (productFlag==-1)
			System.out.println("Not found");
			
				
		
		  List<Product> result = products.stream().filter(item ->
		 item.getProductName().equals(q)).collect(Collectors.toList()); RestTemplate
		 restTemplate2 = new RestTemplate();
		 
			Product product = restTemplate.getForObject("http://localhost:8088/showProduct/" +productFlag , Product.class);
			System.out.println("Product Searchd"+product.getProductBrand()+""+product.getProductName());
			model.addAttribute("product", product);*/
			return "ProductPage";

	    }
}
