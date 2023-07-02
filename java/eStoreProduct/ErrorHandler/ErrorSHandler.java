package eStoreProduct.ErrorHandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import eStoreProduct.ExceptionUser.Emptywalletexception;

@ControllerAdvice
public class ErrorSHandler {
	@ExceptionHandler(value = Emptywalletexception.class)
	public String OtherHandler(Emptywalletexception ex, Model theModel) {
		System.out.println("controller advicer");
		theModel.addAttribute("err", ex);

		return "error";
	}
}
