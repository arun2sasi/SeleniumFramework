package com.rediff.pages;

import org.openqa.selenium.WebDriver;

import com.framework.actions.WebAction;

public class ShoppingCart extends WebAction {

	public ShoppingCart(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	protected void shoppingMethod(){
		//element(:unit_price) {|b| b.element(class: "special-price")}
		//element(:total_price) {|b| b.element(id: "total_price")}
		//element(:proceed_to_checkout) {|b| b.links.find {|link| link.text == "Proceed to checkout"}}
	}
}
