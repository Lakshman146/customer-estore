package eStoreProduct.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.DAO.OrderDAOView;
import eStoreProduct.model.OrdersViewModel;
import eStoreProduct.model.custCredModel;

@Controller
public class customerOrderController {
	private static final Logger logger = LoggerFactory.getLogger(customerOrderController.class);

	@Autowired
	private OrderDAOView orderdaov;

	@RequestMapping("/CustomerOrdersProfile")
	// Method to show ordered products of the user
	public String showOrders(Model model, HttpSession session) {
				logger.info("eStoreProduct:customerOrderController::showing the customers orders profile");
		custCredModel cust = (custCredModel) session.getAttribute("customer");
		// Getting ordered products from the DAO
		List<OrdersViewModel> orderProducts = orderdaov.getorderProds(cust.getCustId());

		model.addAttribute("orderProducts", orderProducts);
		return "orders";
	}

	// Getting the details of the specific product when clicked on it
	@GetMapping("/productDetails")
	public String getProductDetails(@RequestParam("id") int productId, Model model, HttpSession session) {
	logger.info("eStoreProduct:customerOrderController::products details of ordered product");	
		custCredModel cust = (custCredModel) session.getAttribute("customer");
		OrdersViewModel product = orderdaov.OrdProductById(cust.getCustId(), productId);
		model.addAttribute("product", product);
		return "OrdProDetails";
	}

	// Cancelling the order
	@PostMapping("/cancelOrder")
	@ResponseBody
	public String cancelOrder(@RequestParam("orderproId") Integer productId, @RequestParam("orderId") int orderId) {
		logger.info("eStoreProduct:customerOrderController::cancelling the ordered ITEM");
		// Cancelling order in the orderproducts table and updating the status
		orderdaov.cancelorderbyId(productId, orderId);

		// Checking whether all the products in an order are cancelled or not
		boolean allProductsCancelled = orderdaov.areAllProductsCancelled(orderId);

		if (allProductsCancelled) {
			// Update the shipment status of the order in slam_Orders table
			orderdaov.updateOrderShipmentStatus(orderId, "cancelled");
		}

		return "Order with ID " + productId + orderId + " has been cancelled.";
	}

	// Method to track the order
	@RequestMapping(value = "/trackOrder", method = RequestMethod.GET)
	@ResponseBody
	public String trackOrder(@RequestParam("orderproId") int productId, @RequestParam("orderId") int orderId) {
	logger.info("eStoreProduct:customerOrderController::tracking the ordered product");
		// Retrieve the shipment status for the given order ID
		String shipmentStatus = orderdaov.getShipmentStatus(productId, orderId);
		return shipmentStatus;
	}

	
}
