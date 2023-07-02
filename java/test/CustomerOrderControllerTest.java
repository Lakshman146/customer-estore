package test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import eStoreProduct.DAO.OrderDAOView;
import eStoreProduct.model.OrdersViewModel;
import eStoreProduct.model.custCredModel;
import eStoreProduct.controller.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CustomerOrderControllerTest {

  @Mock
  private OrderDAOView orderDAOView;

  @Mock
  private HttpSession httpSession;

  @Mock
  private Model model;

  @InjectMocks
  private customerOrderController customerOrderController;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testShowOrders() {
    // Mock data
    custCredModel cust = new custCredModel();
    List<OrdersViewModel> orderProducts = new ArrayList<>();
    // Add order products to the list

    when(httpSession.getAttribute("customer")).thenReturn(cust);
    when(orderDAOView.getorderProds(cust.getCustId())).thenReturn(orderProducts);

    // Test the method
    String result = customerOrderController.showOrders(model, httpSession);

    // Verify the result
    verify(httpSession).getAttribute("customer");
    verify(orderDAOView).getorderProds(cust.getCustId());
    verify(model).addAttribute(eq("orderProducts"), anyList());
    AssertJUnit.assertEquals("orders", result);
  }

  @Test
  public void testGetProductDetails() {
    // Mock data
    custCredModel cust = new custCredModel();	
    int productId = 123;
    OrdersViewModel product = new OrdersViewModel(productId, "earphones", 1000, "these are earphones", "www.earphones.com", 2134, "order_placed");

    when(httpSession.getAttribute("customer")).thenReturn(cust);
    when(orderDAOView.OrdProductById(cust.getCustId(), productId)).thenReturn(product);

    // Test the method
    String result = customerOrderController.getProductDetails(productId, model, httpSession);

    // Verify the result
    verify(httpSession).getAttribute("customer");
    verify(orderDAOView).OrdProductById(cust.getCustId(), productId);
    verify(model).addAttribute(eq("product"), any());
    // Assert other expectations or behavior based on the expected result
    AssertJUnit.assertEquals("OrdProDetails", result);

  }

  @Test
  public void testCancelOrder() {
    // Mock data
    Integer productId = 123;
    int orderId = 456;

    // Mock method behavior
    when(orderDAOView.areAllProductsCancelled(orderId)).thenReturn(true);

    // Test the method
    String result = customerOrderController.cancelOrder(productId, orderId);

    // Verify the result
    verify(orderDAOView).cancelorderbyId(productId, orderId);
    verify(orderDAOView).areAllProductsCancelled(orderId);
    verify(orderDAOView).updateOrderShipmentStatus(orderId, "cancelled");
    // Assert the expected result
    Assert.assertEquals(result, "Order with ID " + productId + orderId + " has been cancelled.");
  }


  @Test
  public void testTrackOrder() {
    // Mock data
    int productId = 123;
    int orderId = 456;
    String shipmentStatus = "shipped";

    when(orderDAOView.getShipmentStatus(productId, orderId)).thenReturn(shipmentStatus);

    // Test the method
    String result = customerOrderController.trackOrder(productId, orderId);

    // Verify the result
    verify(orderDAOView).getShipmentStatus(productId, orderId);
    // Assert other expectations or behavior based on the expected result
    AssertJUnit.assertEquals(shipmentStatus, result);

  }

  
}

