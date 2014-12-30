package com.framework.actions;

import static java.lang.String.format;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.net.UrlChecker.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.framework.exception.SeleniumException;
import com.framework.report.DetailedLogs;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;


/**
 * WebAction.java is for interacting with browser objects. Page objects re-use these utilities to perform functions relevant to test steps. 
 * 
 * @author : Abhay Bharti
 * @version 1.0 21/09/14
 */
public abstract class WebAction {
	protected WebDriver driver = null;
	protected WebDriver webDriver = null;
	protected DetailedLogs AppLogs = null;
	final int waitvalue = 10;
	final int timeout = 10;
	private static final long DEFAULT_POLL_MILLIS = 100;
	Actions builder = null;
	
	
	/**
	 * Constructor to initialize WebAction class objects
	 * @param driver
	 */
	public WebAction(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		builder = new Actions(this.driver);
		AppLogs = new DetailedLogs();
	}


	
	
	/**
	 * Purpose : This is used to fetch the CSS properties' values of the given element. CSS properties
	 * can be font-family, background-color, color, and so on.
	 * @param object
	 * @param ProprertyName
	 */
	public void ReadCSSFromWebElement(WebElement object, String ProprertyName) {
		AppLogs.info("ReadCSSFromWebElement starts.."+ "1st Arg : "+object.getTagName() + "2nd Arg : "+ ProprertyName);
		object.getCssValue(ProprertyName);
		// getCssValue("font-family"));
		// getCssValue("background-color"));
		AppLogs.info("ReadCSSFromWebElement ends..");
	}

	/***
	 * Purpose : This is used to get the relative position of an element where it is rendered on
	 * the web page.
	 * @param object
	 */
	public void WeGetLocation(WebElement object) {
		AppLogs.info("WEGetLocation starts.."+ "1st Arg : "+object.getTagName());
		object.getLocation();
		AppLogs.info("WEGetLocation ends..");
	}

	/***
	 * Purpose : It will return the width and height of the rendered WebElement.
	 * @param object
	 */
	public void WeGetSize(WebElement object) {
		AppLogs.info("ReadFromWebElement starts.."+ "1st Arg : "+object.getTagName());
		object.getSize();
		AppLogs.info("WeElement ends..");
	}

	/***
	 * Purpose : It will give the visible text if the element contains any text on it or else will return
	 * nothing.
	 * @param object
	 */
	public void WeGetText(WebElement object) {
		AppLogs.info("WeGetText starts.."+ "1st Arg : "+object.getTagName());
		object.getText();
		AppLogs.info("WeGetText ends..");
	}

	/***
	 * Purpose : This will return the tag name of the WebElement. For example, in the following HTML
	 * code, button is the tag name of the HTML element
	 * @param object
	 */
	public void WeGetTagName(WebElement object) {
		AppLogs.info("WeGetTagName starts.."+ "1st Arg : "+object.getTagName() );
		object.getTagName();
		AppLogs.info("WeGetTagName ends..");
	}

	/***
	 * Purpose : The verifies if an element is displayed on the web page and can be executed on all the WebElements.
	 * @param object
	 */
	public void iSElementDisplayed(WebElement object) {
		AppLogs.info("iSElementDisplayed starts.."+ "1st Arg : "+object.getTagName());
		object.isDisplayed();
		AppLogs.info("iSElementDisplayed ends..");
	}

	/***
	 * Purpose : TheisEnabled action verifies if an element is enabled on the web page and can be executed on all the WebElements.
	 * @param object
	 */
	public void iSElementEnabled(WebElement object) {
		AppLogs.info("iSElementEnabled starts.."+ "1st Arg : "+object.getTagName());
		object.isEnabled();
		AppLogs.info("iSElementEnabled ends..");
	}

	/***
	 * Purpose : Verifies if an element is selected right now on the web page and can be executed only on a radio button, options in select,
	 * and checkbox WebElements.
	 * @param object
	 */
	public void WeIsSelected(WebElement object) {
		AppLogs.info("WeIsSelected starts.."+ "1st Arg : "+object.getTagName());
		object.isSelected();
		AppLogs.info("WeIsSelected ends..");
	}

	// ################## Mouse Operation #################################
	/**
	 * Purpose : The moveByOffset() method is used to move the mouse from its current position to another point on the web page. User can specify the X
	 * distance and Y distance the mouse has to be moved. When the page is loaded, generally the initial position of a mouse would be (0, 0),
	 * @param object
	 * @param ToXPoint
	 * @param ToYPoint
	 */
	public void WeMouseOffset(WebElement object, int ToXPoint, int ToYPoint) {
		AppLogs.info("WeMouseOffset starts.."+ "1st Arg : "+object.getTagName() + "2nd Arg : "+ToXPoint+ "3rd Arg : "+ToYPoint);
		builder.moveByOffset(object.getLocation().getX() + ToXPoint, object.getLocation().getY() + ToYPoint);
		builder.perform();
		AppLogs.info("WeMouseOffset ends..");
	}

	/**
	 * Purpose : The click() method is used to simulate the left-click of your mouse at its current point of location. This method doesn't really realize where
	 * or on which element it is clicking. It just blindly clicks wherever it is at that point of time. Hence, this method is used in combination with
	 * some other action rather than independently, to create a composite action
	 * @param object
	 */
	public void WeMouseLeftClick(WebElement object) {
		AppLogs.info("WeMouseLeftClick starts.."+ "1st Arg : "+object.getTagName());
		builder.moveByOffset(object.getLocation().getX() + 1,object.getLocation().getY() + 1).click();
		builder.perform();
		AppLogs.info("WeMouseLeftClick ends..");
	}

	/**
	 * Purpose : The click() method is used to simulate the left-click of your mouse at its current point of location. This method doesn't really realize where
	 * or on which element it is clicking. It just blindly clicks wherever it is at that point of time. Hence, this method is used in combination with
	 * some other action rather than independently, to create a composite action
	 * @param object
	 * @param toXposition
	 * @param toYposition
	 */
	public void WeMouseLeftClick(WebElement object, int toXposition, int toYposition) {
		AppLogs.info("WeMouseLeftClick starts.."+ "1st Arg : "+object.getTagName() + "2nd Arg : "+toXposition+ "3rd Arg : "+toYposition);
		builder.moveByOffset(object.getLocation().getX() + toXposition,object.getLocation().getY() + toXposition).click();
		builder.perform();
		AppLogs.info("WeMouseLeftClick ends..");
	}
	
	/**
	 * Purpose : click() method to click directly on the WebElement.
	 * @param object
	 */
	public void ClickOnWebElement(WebElement object) {
		AppLogs.info("ClickOnWebElement starts.."+ "1st Arg : "+object.getTagName());
		builder.click(object);
		builder.build().perform();
		AppLogs.info("ClickOnWebElement ends..");
	}

	/***
	 * Purpose : The clickAndHold()method is another method of the Actions class that left-clicks on an element and holds it without releasing the left button
	 * of the mouse. This method will be useful when executing operations such as drag-and-drop.
	 * @param object
	 */
	public void ClickAndHold(WebElement object) {
		AppLogs.info("ClickAndHold starts.."+ "1st Arg : "+object.getTagName());
		builder.moveByOffset(200, 20).clickAndHold().moveByOffset(120, 0).perform();
		AppLogs.info("ClickAndHold ends..");
	}

	
	/**
	 * Purpose : taken on a held WebElement is to release it so that the element can be dropped or released from the mouse.
	 * @param source
	 * @param target
	 */
	public void ClickAndHoldAndRelease(WebElement source, WebElement target) {
		AppLogs.info("ClickAndHoldAndRelease starts.."+ "1st Arg : "+source.getTagName()+ " 2nd Arg : "+target.getTagName());
		builder.clickAndHold(source).release(target).perform();
		AppLogs.info("ClickAndHoldAndRelease ends..");
	}

	/**
	 * Purpose : helps us to move the mouse cursor to a WebElement on the web page.
	 * @param object
	 */
	public void moveToElement(WebElement object) {
		AppLogs.info("moveToElement starts.." +"1st Arg : "+object.getTagName());
		builder.moveToElement(object).clickAndHold().moveByOffset(120, 0).perform();
		AppLogs.info("moveToElement ends..");
	}

	/**
	 * Purpose :  have to drag-and-drop components or WebElements of a web page.
	 * @param object
	 */
	public void dragMe(WebElement object) {
		AppLogs.info("dragMe starts.."+"1st Arg : "+object.getTagName());
		builder.dragAndDropBy(object, 300, 200).perform();
		AppLogs.info("dragMe ends..");
	}

	/**
	 * Purpose : The only difference being that instead of moving the WebElement by an offset, we move it on to a target element
	 * @param source
	 * @param target
	 */
	public void DragandDropToWE(WebElement source, WebElement target) {
		AppLogs.info("DragandDropToWE starts..");
		builder.dragAndDrop(source, target).perform();
		AppLogs.info("DragandDropToWE ends..");
	}

	/**
	 * Purpose : Moving on to another action that can be performed using mouse, doubleClick()is another out of the box method that WebDriver provides to
	 * emulate the double-clicking of the mouse.
	 * @param object
	 */
	public void DoubleClickOnWe(WebElement object) {
		AppLogs.info("DoubleClickOnWe starts.."+"1st Arg : "+object.getTagName());
		builder.moveToElement(object).doubleClick().perform();
		AppLogs.info("DoubleClickOnWe ends..");
	}

	/**
	 * Purpose : The contextClick() method, also known as right-click, is quite common on many web pages these days. The context is nothing but a menu; a list of
	 * items is associated to a WebElement based on the current state of the web page.
	 * @param object
	 * @param item4
	 */
	public void RightClickOnWe(WebElement object, String item4) {
		AppLogs.info("RightClickOnWe starts.."+"1st Arg : "+object.getTagName());
		builder.contextClick(object).click(driver.findElement(By.name("Item 4"))).perform();
		AppLogs.info("RightClickOnWe ends..");
	}
	
	
	/**
	 * Purpose : returns HTTP code for a given URL
	 * @param urlString
	 * @return
	 */
	public boolean getResponseCode(String urlString) throws SeleniumException{
		AppLogs.info("getResponseCode starts.. for URL : "+urlString);
	    boolean isValid = false;
	    int code = 0;
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnect = (HttpURLConnection)url.openConnection();
            httpURLConnect.setRequestMethod("GET");
            httpURLConnect.connect();
            code = httpURLConnect.getResponseCode();
            if (code == 200){
            	AppLogs.debug(urlString +" - "+httpURLConnect.getResponseMessage());
            	isValid = true;
            }
            if(code == HttpURLConnection.HTTP_NOT_FOUND)  
            {
            	AppLogs.debug(urlString +" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
            	isValid = true;
            }
         } catch (MalformedURLException e) {
            throw new SeleniumException("MalformedURLException Error : "+e +" , "+ urlString);
         } catch (IOException e) {
        	  throw new SeleniumException("IOException Error : "+e+" , "+ urlString);
         } catch (Exception e) {
        	  throw new SeleniumException("Exception Error : "+e+" , "+ urlString);
         }
        AppLogs.info("getResponseCode ends..");
        return isValid;
    }
	
	/**
	 * Purpose : This method is useful when you have multiple links on a page and instead of click each link and verifying the page 
	 * title or what's written on the page - you can just send an http request to the link and see what the response is  
	 * @param link
	 * @return
	 * @throws SeleniumException
	 */
	public boolean isFileDownloadable(String link) throws SeleniumException{
		AppLogs.info("isFileDownloadable starts.. for URL : "+link);
		boolean isValid = false;
		AppLogs.debug("Link: " + link);
		try {
			if (getResponseCode(link)){
				isValid = true;
			}
		} catch (Exception e) {
			throw new SeleniumException("isFileDownloadable ends.."+e);
		}
		AppLogs.info("isFileDownloadable ends..");
		return isValid;
	}
		
	

	/**
	 * Purpose : Verifies if link on a given page is broken
	 * @throws SeleniumException
	 */
	public String isLinkBroken() throws SeleniumException{
	   AppLogs.info("isLinkBroken starts.. for URL : "+driver.getCurrentUrl());
	   String link = null;
       List <WebElement>linksList = driver.findElements(By.tagName("a")); 
        for(WebElement linkElement: linksList){
          link =linkElement.getAttribute("href");
          if(link!=null){
            if (getResponseCode(link)){
            	Reporter.log("isLinkBroken : "+link + "  works fine");
            }else {
            	Reporter.log("isLinkBroken : "+link + "  works fine");
            }
          }
      }
        AppLogs.info("isLinkBroken starts.. for URL : "+driver.getCurrentUrl());
      return link;
  } 
  
	/**
	 * Purpose : Parameterize form of isLinkBroken method, verifies if link on a given page is broken
	 * @throws SeleniumException
	 */
	public boolean isLinkBroken(String urlString) throws SeleniumException{
	   AppLogs.info("isLinkBroken starts .. for URL : "+ urlString);
	    boolean isValid = false;
	      if(urlString!=null){
            if (getResponseCode(urlString)){
            	isValid = true;
            }else {
            	isValid = false;
            }
          }
	    AppLogs.info("isLinkBroken ends .. for URL : "+ urlString);
      return isValid;
  } 
	
	//---------------------Working on Multiple Browser/Switching Frame/Handling Alert-------------------------
	/**
	 * Purpose : This method switches to browser based on browser URL
	 * @param currentUrl
	 */
	public void SwitchToBrowser(String currentUrl) {
		AppLogs.info("ReadFromWebElement starts.. for URL : "+currentUrl);
		for(String winHandle :driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
			if(driver.getCurrentUrl().equals(currentUrl)){
				AppLogs.info("You are in required window : " + driver.getCurrentUrl());
				break;
            } 
			else{
				AppLogs.error("URL of the page after - switchingTo: " + driver.getCurrentUrl());
			}
		}
	}
	
	/**
	 * Purpose : switch target to a Frame of a browser
	 * @param frameIndex
	 */
	public void SwitchToFrame(String frameIndex){
		driver.switchTo().defaultContent();
		driver.switchTo().frame(0);      
	}      
	
	/**
	 * Purpose : switch target to a second Frame of a browser where target is set to first frame
	 * @param frameIndex
	 */
	public void SwitchToFrame(String frameIndex1,String frameIndex2){
		driver.switchTo().frame(frameIndex1);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frameIndex2);      
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean AcceptAlert() throws SeleniumException {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return true;
		}catch(NoAlertPresentException e){
			throw new SeleniumException("WebAction -> AcceptAlert() , "+e);
		}
		catch (Exception e) {
			throw new SeleniumException("WebAction -> AcceptAlert() , "+e);
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean DeclineAlert() throws SeleniumException {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			return true;
		} catch(NoAlertPresentException e){
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
		catch (Exception e) {
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws SeleniumException
	 */
	public boolean SetTextOnAlert(String keysToSend) throws SeleniumException {
		try {
			Alert alert = driver.switchTo().alert();
			alert.sendKeys(keysToSend);
			return true;
		} catch(NoAlertPresentException e){
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
		catch (Exception e) {
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws SeleniumException
	 */
	public String GetTextOfAlert() throws SeleniumException {
		try {
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		} catch(NoAlertPresentException e){
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
		catch (Exception e) {
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws SeleniumException
	 */
	public void BrowserRefresh() throws SeleniumException {
		try {
			driver.navigate().refresh();
		} catch(NoAlertPresentException e){
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
		catch (Exception e) {
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws SeleniumException
	 */
	public void BrowserForward() throws SeleniumException {
		try {
			driver.navigate().forward();
		} catch(NoAlertPresentException e){
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
		catch (Exception e) {
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws SeleniumException
	 */
	public void BrowserBack() throws SeleniumException {
		try {
			driver.navigate().back();
		} catch(NoAlertPresentException e){
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
		catch (Exception e) {
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws SeleniumException
	 */
	public boolean OpenURL(String URL) throws SeleniumException {
		boolean isValid = false;
		try {
			driver.navigate().to(URL);
			if (getResponseCode(URL)){
				AppLogs.info(URL + " successfully opened");
				isValid = true;
			}else {
				AppLogs.info(URL + " not working");
				isValid = false;
			}
		} catch(NoAlertPresentException e){
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
		catch (Exception e) {
			throw new SeleniumException("WebAction -> DeclineAlert() , "+e);
		}
		return isValid;
	}
	
	
	
    public void acceptAlert(int timeout) {
        waitOnExpectedCondition(ExpectedConditions.alertIsPresent(),
                "Waiting for javascript alert to be present before accepting alert.", timeout);
        webDriver.switchTo().alert().accept();
    }

    
    public void dismissAlert(int timeout) {
        waitOnExpectedCondition(ExpectedConditions.alertIsPresent(),
                "Waiting for javascript alert to be present before dismissing alert.", timeout);
        webDriver.switchTo().alert().dismiss();
    }
    
    
    public <T> T waitOnExpectedCondition(ExpectedCondition<T> expectedCondition, String message, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout); //Default of web element presence timeout
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds, DEFAULT_POLL_MILLIS);
        wait.withMessage(message)
            .ignoring(StaleElementReferenceException.class);
        AppLogs.info("Waiting on expected condition, using timeout of {} seconds", waitSeconds);
        return wait.until(expectedCondition);
    }

    private <T> T waitOnExpectedConditionForSeconds(ExpectedCondition<T> expectedCondition, String message, int timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout, DEFAULT_POLL_MILLIS);
        wait.withMessage(message)
            .ignoring(StaleElementReferenceException.class);
        AppLogs.info("Waiting on expected condition, using timeout of {} seconds", timeout);
        return wait.until(expectedCondition);
    }

    
    public WebElement clearText(By locator) {
        WebElement el = verifyElementPresented(locator, timeout);
        try {
            el.clear();
        } catch (Exception e) {
            throw new RuntimeException(format("Error clearing text from element with Locator '%s': %s",
                    locator.toString(), e.getMessage()), e);
        }
        AppLogs.info("Cleared text from element with Locator '{}'", locator);
        return el;
    }

    
    public WebElement clearText(@Nonnull WebElement el) {
        String tag = el.getTagName();
        try {
            el.clear();
        } catch (Exception e) {
            throw new RuntimeException(format("Error clearing text from element <%s>: %s", tag, e.getMessage()), e);
        }
        AppLogs.info("Cleared text from element <{}>", tag);
        return el;
    }

    
    public WebElement clickNoWait(By locator) throws SeleniumException {
        WebElement el = getElement(locator);
        if (!isClickable(el)) {
            throw new SeleniumException("Element is not clickable: " + locator.toString());
        }
        el.click();
        AppLogs.info("Clicked element with locator '{}', no waiting.", locator);
        return el;
    }

    
    public WebElement click(By locator, int timeout) {
        WebElement el = waitUntilClickable(locator, timeout);
        try {
            el.click();
        } catch (StaleElementReferenceException e) {
        	AppLogs.warn("Element was stale immediately after waiting to be clickable in BaseSeleniumActions#click. Waiting for element to be clickable again.");
            el = waitUntilClickable(locator, timeout);
            el.click();
        }
        AppLogs.info("Clicked element with locator '{}'", locator);
        return el;
    }

    
    public WebElement click(WebElement el, int timeout) {
        waitUntilClickable(el, timeout);
        String tag = el.getTagName();
        el.click();
        AppLogs.info("Clicked element <{}>", tag);
        return el;
    }
    
    
    public WebElement waitUntilClickable(By locator, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getClickTimeoutSeconds(), timeout);
        final String errorMessage = "Element '%s' never became clickable after '%d' seconds" + locator+ waitSeconds;
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds);
        wait.withMessage(errorMessage).ignoring(StaleElementReferenceException.class);
        AppLogs.info("Waiting for locator element '{}' to be clickable, using timeout of {} seconds", locator, waitSeconds);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    
    public WebElement waitUntilClickable(final WebElement el, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getClickTimeoutSeconds(), timeout);
        final String message = "Element never became clickable after '%d' seconds"+waitSeconds;
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds);
        wait.withMessage(message).ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedCondition<WebElement>() {
            
            public WebElement apply(WebDriver webDriver) {
                if (isClickable(el)) {
                    return el;
                }
                return null;
            }
        });
        return el;
    }
    
    
    public boolean isClickable(By locator) {
        WebElement el = getElement(locator);
        if (el == null) {
            return false;
        }
        return isClickable(el);
    }
    
    
    public boolean isClickable(WebElement el) {
        if (el == null) {
            return false;
        }
        try {
            if (!el.isDisplayed()) { //If not visible, element isn't clickable
                return false;
            }
            if (el.getSize().getHeight() <= 0 || el.getSize().getWidth() <= 0) { // If width or height is 0, element is not clickable
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
    @Nullable
    public WebElement getElement(By locator) {
        List<WebElement> elements = findElements(locator, null);
        if (elements.size() > 0) {
            return elements.get(0);
        }
        return null;
    }
    
    
    public WebElement verifyElementPresented(By locator, int timeout) {
        int waitSeconds = timeout;
        		//getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String errorMessage =
                format("Failure in verifyElementPresented: element '%s' never became presented after %d seconds!",
                        locator.toString(), waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds);
        wait.withMessage(errorMessage).ignoring(StaleElementReferenceException.class);
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//        AppLogs.trace("SUCCESS: Verified element with Locator '{}' is present", locator.toString());
        AppLogs.info("SUCCESS: Verified element with Locator '{}' is present", locator.toString());
        return el;
    }
    
    //////////////////////////////////// helpers ////////////////////////////////////////////////

    /**
     * Convenient helper to Find Elements from either top page element or from parent element.
     *
     * @param locator      - Locator defining the input element
     * @param parentEl - Parent web element. If this is provided the search will be from the parent element. If null, search will be from top element
     * @return - List of elements
     */
    protected List<WebElement> findElements(By locator, WebElement parentEl) {
        if (parentEl == null) {
            return webDriver.findElements(locator);
        } else {
            return parentEl.findElements(locator);
        }
    }

    protected WebElement findElement(By locator, WebElement parentEl) {
        if (parentEl == null) {
            return webDriver.findElement(locator);
        } else {
            return parentEl.findElement(locator);
        }
    }

    protected WebElement enterTextAndSelectFromList(WebElement inputField, String value, By popoverLocator,
                                                    int withRetryCount, boolean slowly) throws SeleniumException {
        boolean done = false;
        int initialCount = withRetryCount;

        do {
            try {
                enterTextAndSelectFromList(inputField, value, popoverLocator, slowly);
                done = true;
            } catch (Exception ex) {
                AppLogs.error("Caught an exception " + ex.getMessage());
            }
            withRetryCount--;
        } while (!done && withRetryCount > 0);

        // Need to subtract 1, so that we have 0 retries if we succeeded on the first try.
        int numberOfUsedRetries = initialCount - withRetryCount - 1;
        if (numberOfUsedRetries > 0) {
            AppLogs.warn(done ?
                    format("Entered text successfully and selected locator '%s' from list after %d retries", popoverLocator, numberOfUsedRetries) :
                    format("Failed to enter text and select locator '%s' from list.", popoverLocator));
        }
        if (!done) {
            throw new SeleniumException(format("Failed to inputTextAndSelectFromList after %d retries", numberOfUsedRetries));
        }
        return inputField;
    }

    protected void enterTextAndSelectFromList(WebElement inputField, String value, By popoverLocator, boolean slowly) {
        clearText(inputField);
        if (slowly) {
            inputTextSlowly(inputField, value);
        } else {
            inputText(inputField, value);
        }
        verifyElementPresented(popoverLocator, timeout);
        click(popoverLocator, timeout);
    }

    protected void invokeMenuItemAndSelect(WebElement clickable, By popoverLocator) {
        Preconditions.checkNotNull(clickable, "Input WebElement cannot be null");
        waitUntilClickable(clickable, timeout);
        click(clickable, timeout);
        verifyElementPresented(popoverLocator, timeout);
        waitUntilClickable(popoverLocator, timeout);
        click(popoverLocator, timeout);
    }

    
    public WebElement inputTextSlowly(By locator, String text) {
        WebElement el = getElementWithWait(locator);
        AppLogs.info("Inputting text '{}' into web element with locator '{}'", text, locator);
        return inputTextSlowly(el, text);
    }

    
    @Nonnull
    public WebElement getElementWithWait(By locator) {
        return getChildElementWithWait(locator, null);
    }

    
    public WebElement inputTextSlowly(@Nonnull WebElement el, String text) {
        AppLogs.info("Inputting text {} slowly into web element {}", text, el.getTagName());
        for (Character c : text.toCharArray()) {
            el.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                // don't care
            }
        }
        return el;
    }
    
    
    @Nonnull
    public WebElement getChildElementWithWait(By locator, WebElement parentEl) {
        try {
            WebElement el = findElement(locator, parentEl);
            AppLogs.info("Successfully found web element by locator '{}'", locator);
            return el;
        } catch (NoSuchElementException e) {
            //long implicitWait = browser.getImplicitWaitTimeoutMillis();
        	long implicitWait = timeout;
            throw new RuntimeException(
                    format("Timeout using implicit wait of %d ms waiting to find web element with locator '%s' ", implicitWait, locator));
        }
    }

    
    @Nonnull
    public WebElement getParentElement(WebElement el) {
        return el.findElement(By.xpath(".."));
    }

    
    @Nonnull
    public WebElement inputText(By locator, String text) {
    	AppLogs.info("Inputting text '{}' into element with locator '{}'", text, locator);
        WebElement el = getElementWithWait(locator);
        try {
            el.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException(format("Error inputting text '%s' into element with locator '%s': %s", text, locator, e.getMessage()), e);
        }
        return el;
    }

    
    @Nonnull
    public WebElement inputText(@Nonnull WebElement el, String text) {
        AppLogs.info("Inputting text '{}' into web element <{}>", text, el.getTagName());
        try {
            el.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException(format("Error inputting text '%s' into element <%s>: %s", text, el.getTagName(), e.getMessage()), e);
        }
        return el;
    }

	/**
	 * Purpose : Parameterized version, This method Type special key like Shift,Backspace, Enter, Tab, Shift along with text
	 * @param object
	 * @param input
	 */
	public void inputText(@Nonnull WebElement el,Keys theKey, String input) throws SeleniumException{
		AppLogs.info("EnterValueTextWithShit starts.." + "1st Arg : "+el.getTagName() + "2nd Arg : "+ input);
		 try{
			 el.sendKeys(Keys.chord(Keys.SHIFT,input));
		 }catch(IllegalArgumentException e){
			 throw new SeleniumException("WebAction -> EnterValueText(WebElement object,Keys theKey, String input)" + e);
		 }
			 AppLogs.info("EnterValueTextWithShit ends..");
	}

	/**
	 * Purpose: This method get all the attributes of a WebElement
	 * @param object
	 * @param attribute
	 */
	public void ReadFromWebElement(WebElement object, String attribute) {
		AppLogs.info("ReadFromWebElement starts.. "+ "1st Arg : "+object.getText() + "2nd Arg : "+ attribute);
		object.getAttribute(attribute);
		AppLogs.info("ReadFromWebElement ends..");
	}


	/**
     * Conditions according to selenium Javadoc for an element to be clickable
     */
    
    public boolean isVisible(By locator) {
        WebElement el = getElement(locator);
        return isVisible(el);
    }

    
    public boolean isVisible(WebElement el) {
        if (el == null) {
            return false;
        }
        try {
            return el.isDisplayed() && el.getSize().getHeight() > 0 && el.getSize().getWidth() > 0;
        } catch (StaleElementReferenceException e) {
            // If the element becomes stale during the check, after we got it, then return false.
            return false;
        }
    }

    
    public void scrollIntoView(By locator) {
        WebElement el = verifyElementPresented(locator,timeout);
        scrollIntoView(el);
    }

    
    public void scrollIntoView(WebElement el) {
        int scrollHeight = ((WebDriver) webDriver).manage().window().getSize().getHeight();
        int y = Math.max(0, el.getLocation().getY() - scrollHeight / 2); //Subtract half the window height so its in the middle of the viewable area.
        executeJavascript(format("window.scrollTo(%d, %d)", 0, y));
    }

    
    public void scrollIntoView(By scrollContainerLocator, By locator) {
        WebElement parent = verifyElementPresented(scrollContainerLocator, timeout);
        WebElement el = verifyElementPresented(locator, timeout);
        int currentScrollTop = ((Long) executeJavascript(format("return $('%s').scrollTop()", scrollContainerLocator))).intValue();
        int y = el.getLocation().getY();
        int parentY = parent.getLocation().getY();
        int scrollTo = Math.max(0, y - parentY + currentScrollTop);
        executeJavascript(format("$('%s').scrollTop(%d)", scrollContainerLocator, scrollTo));
    }

    
    public void scrollIntoView(By scrollContainerLocator, WebElement el) {
        WebElement parent = verifyElementPresented(scrollContainerLocator, timeout);
        int currentScrollTop = ((Long) executeJavascript(format("return $('%s').scrollTop()", scrollContainerLocator))).intValue();
        int y = el.getLocation().getY();
        int parentY = parent.getLocation().getY();
        int scrollTo = Math.max(0, y - parentY + currentScrollTop);
        executeJavascript(format("$('%s').scrollTop(%d)", scrollContainerLocator, scrollTo));
    }

    
    public void verifyElementContainsText(final By locator, final String text, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String errorMessage = "Failure in verifyElementContainsText: an element with Locator '%s' was never found containing text '%s'!"+locator+ text;
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds);
        wait.withMessage(errorMessage).ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        AppLogs.info("SUCCESS: Verified element with Locator '{}' contains text '{}'", locator, text);
    }

    public void verifyElementNotPresented(By locator, long timeout) {
        long waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String errorMessage = format("Failure in verifyElementNotPresented: element '%s' never became not presented after %d seconds!",
                locator, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds);
        wait.withMessage(errorMessage)
            .ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        AppLogs.info("SUCCESS: Verified element with locator '{}' is NOT present", locator);
    }

    
    public void verifyElementWithTextNotPresented(By locator, String text, int timeout) {
        try {
            findElementContainingTextWithWait(locator, text, timeout);
            throw new RuntimeException(
                    format("Error in verifyElementWithTextNotPresented: found element with locator '%s' containing text '%s'!", locator, text));
        } catch (Exception e) {
            return;
        }
    }

    
    public void verifyElementSelected(By locator, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getClickTimeoutSeconds(), timeout);
        final String errorMessage = format("Failure in verifyElementSelected: Element '%s' never became selected after %d seconds!",
                locator, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds);
        wait.withMessage(errorMessage)
            .ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.elementToBeSelected(locator));
    }

    
    public void verifyElementSelected(WebElement el, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getClickTimeoutSeconds(), timeout);
        final String errorMessage = format("Failure in verifyElementSelected: Element '%s' never became selected after %d seconds!",
                el.getTagName(), waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds);
        wait.withMessage(errorMessage);
        wait.until(ExpectedConditions.elementToBeSelected(el));
        AppLogs.info("SUCCESS: Verified element <{}> is selected", el.getTagName());
    }

    
    public void verifyElementNotSelected(By locator, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getClickTimeoutSeconds(), timeout);
        final String errorMessage = format("Failure in verifyElementNotSelected: Element '%s' never became deselected after %d seconds!",
                locator, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds);
        wait.withMessage(errorMessage).ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.elementSelectionStateToBe(locator, false));
        AppLogs.info("SUCCESS: Verified element with locator '{}' is NOT selected", locator);
    }

    
    public void verifyElementNotSelected(WebElement el, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getClickTimeoutSeconds(), timeout);
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds);
        wait.until(ExpectedConditions.elementSelectionStateToBe(el, false));
        AppLogs.info("SUCCESS: Verified element <{}> is NOT selected", el.getTagName());
    }

    
    public WebElement verifyElementVisible(final By locator, int timeout) {
        final String errorMessage = format("Error in verifyElementVisible: element with locator '%s' never became visible", locator);
        return waitOnExpectedCondition(ExpectedConditions.visibilityOfElementLocated(locator), errorMessage, timeout);
    }

    
    public void verifyElementInvisible(By locator, int timeout) {
        waitOnExpectedCondition(ExpectedConditions.invisibilityOfElementLocated(locator),
                format("Failure in verifyElementInvisible waiting for element with locator '%s' to be invisible", locator), timeout);
    }

    
    public void verifyElementWithTextIsInvisible(By locator, String text, int timeout) {
        try {
            WebElement visibleEl = findVisibleElementContainingTextWithWait(locator, text, timeout);
            throw new RuntimeException(
                    format("Error in verifyElementWithTextIsInvisible: found element by locator '%s' containing text '%s'", locator, text));
        } catch (Exception e) {
            return; // OK - we didn't find a visible element containing the given text
        }
    }

    
    public void verifyElementRemoved(WebElement element, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        AppLogs.info("Waiting for element to become stale (removed from the DOM) using timeout of {} seconds", waitSeconds);
        waitOnExpectedConditionForSeconds(ExpectedConditions.stalenessOf(element),
                "Timeout waiting for web element to become stale (removed from the DOM).",
                waitSeconds);
        AppLogs.info("Verified web element became stale (removed from the DOM).");
    }

    
    public WebElement verifyPageRefreshed(WebElement elementFromBeforeRefresh, By locatorAfterRefresh, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getPageRefreshTimeoutSeconds(), timeout);
        AppLogs.info("Waiting for locator '{}' to be present after page refreshes, using timeout of {} seconds", locatorAfterRefresh, waitSeconds);
        waitOnExpectedConditionForSeconds(ExpectedConditions.stalenessOf(elementFromBeforeRefresh),
                "Timeout waiting for web element to become stale (waiting for page to reload).",
                waitSeconds);
        AppLogs.info("Verified web element became stale (page is reloading).");
        WebElement el = verifyElementPresented(locatorAfterRefresh, timeout);
        AppLogs.info("Successfully verified page refreshed by finding web element with locator '{}'.", locatorAfterRefresh);

        return el;
    }

    public boolean exists(By locator) {
        List<WebElement> elements = findElements(locator, null);
        return elements.size() > 0;
    }

    
    public boolean exists(By locator, WebElement parentEl) {
        List<WebElement> elements = findElements(locator, parentEl);
        return elements.size() > 0;
    }
    
    public Object executeJavascript(String script) {
		return null;
	}
    /*
    
    public <T, V> V waitOnFunction(Function<T, V> function, T input, String message, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getMediumTimeoutSeconds(), timeout);
        FluentWait<T> fluentWait = new FluentWait<T>(input)
                .withTimeout(waitSeconds, TimeUnit.SECONDS)
                .pollingEvery(DEFAULT_POLL_MILLIS, TimeUnit.MILLISECONDS)
                .withMessage(message)
                .ignoring(NotFoundException.class)
                .ignoring(StaleElementReferenceException.class);
        return fluentWait.until(function);
    }

    
    public <T> void waitOnPredicate(Predicate<T> predicate, T input, String message, TimeoutType timeout) {
        int waitSeconds = getTimeout(timeoutsConfig.getMediumTimeoutSeconds(), timeout);
        FluentWait<T> fluentWait = new FluentWait<T>(input)
                .withTimeout(waitSeconds, TimeUnit.SECONDS)
                .pollingEvery(DEFAULT_POLL_MILLIS, TimeUnit.MILLISECONDS)
                .withMessage(message)
                .ignoring(NotFoundException.class)
                .ignoring(StaleElementReferenceException.class);
        fluentWait.until(predicate);
    }

    
    public void waitOnPredicate(Predicate predicate, String message, TimeoutType timeout) {
        waitOnPredicate(predicate, new Object(), message, timeout);
    }

 /*   
    public <T> void waitOnPredicateWithRefresh(final Predicate<T> predicate, final T input, String message, int timeout) {
        int waitSeconds = timeout;
        		//getTimeout(timeoutsConfig.getMediumTimeoutSeconds(), timeout);
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds, DEFAULT_POLL_MILLIS);
        wait.withMessage(message)
            .ignoring(StaleElementReferenceException.class);

        AppLogs.info("Waiting on expected condition, using timeout of {} seconds", waitSeconds);
        wait.until(new Predicate<WebDriver>() {
            
            public boolean apply(@Nullable WebDriver webDriver) {
                if (predicate.apply(input)) {
                    return true;
                }
                browser.refreshPage(BaseTopLevelPage.class);
                return false;
            }
        });
    }
 
    
    public void waitOnPredicateWithRefresh(final Predicate predicate, String message, int timeout) {
        waitOnPredicateWithRefresh(predicate, new Object(), message, timeout);
    }
    
    
    public Object executeJavascript(String script) {
        AppLogs.info("Executing javascript: '{}'", script);
        try {
            return ((JavascriptExecutor) webDriver).executeScript(script);
        } catch (Exception e) {
            throw new RuntimeException(format("Exception executing Javascript '%s':", script), e);
        }
    }
    
    public void waitForJavascriptSymbolToBeDefined(final String symbol, int timeout) {
        int waitSeconds = timeout; 
        		//getTimeout(timeoutsConfig.getPageLoadTimeoutSeconds(), timeout);
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds, DEFAULT_POLL_MILLIS); //Check every 100ms
        wait.ignoring(StaleElementReferenceException.class);
        try {
            wait.until(new ExpectedCondition<Object>() {
                @Nullable
                
                public Object apply(@Nullable WebDriver input) {
                    Object jsResult = executeJavascript(format("return (typeof %s != 'undefined') && (%s != null)", symbol, symbol));
                    AppLogs.debug("javascript result: " + jsResult);
                    return jsResult;
                }
            });
        } catch (TimeoutException e) {
            throw new RuntimeException(
                    format("Timeout waiting for javascript symbol '%s' to be defined with %d seconds timeout used", symbol, waitSeconds), e);
        }
        AppLogs.info("Success verifying javascript symbol '{}' is defined!", symbol);
    }

    
    public void waitForJavascriptSymbolToHaveValue(final String symbol, final String value, int timeout) {
        int waitSeconds = timeout;
        WebDriverWait wait = new WebDriverWait(webDriver, waitSeconds, DEFAULT_POLL_MILLIS); //Check every 100ms
        wait.ignoring(StaleElementReferenceException.class);
        try {
            wait.until(new ExpectedCondition<Object>() {
                @Nullable
                
                public Object apply(@Nullable WebDriver input) {
                    Object jsResult = executeJavascript(format("return (%s) === (%s)", symbol, value));
                    AppLogs.debug("javascript result: " + jsResult);
                    return jsResult;
                }
            });
        } catch (TimeoutException e) {
            throw new RuntimeException(
                    format("Timeout waiting for javascript symbol '%s' to have value '%s' with %d seconds timeout used", symbol, value, waitSeconds), e);
        }
        AppLogs.info("Success verifying javascript symbol '{}' has value '{}'!", symbol, value);
    }
**/
	/**
	 * Purpose : Download a file
	 */
	/**
	Load the page
	Get the URL of the first download link
	Perform a GET action against the URL with an HTTP library
	Store the response
	Assert that the file type is correct
	Assert that the file is not empty
	*/
	
	
	/**
	 * Purpose : Upload a file
	 */
	/**
	 * Load the page
		Find the form element that stores the path of the file we want to upload
		Inject the path of the file we want to upload
		Find the form submit button and click it
		Grab the text from the uploaded file list that gets rendered
		Assert that the uploaded file is what we expect
	 */
	
	/**
	 * 
	 */
	/**
	 * Find all images on the page
Iterate through each image, finding a match for the src attribute with a 404 status code
Store the broken images in a collection
Assert that the broken images collection is empty
	 */
	
	//==============Page Synchronization Methods===============
	public void waitForElementPresent(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(
		ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
	
	public boolean isElementPresent(String xpath) {
		return driver.findElements(By.xpath(xpath)).size() > 0;
	}
	
	public boolean isElementPresent(By by) {
		return driver.findElements(by).size() > 0;
	}
	
	/*
    public WebElement clickAndVerifyPresent(By locatorToClick, By locatorToVerifyPresent, TimeoutType timeout) {
        click(locatorToClick, timeout);
        logger.info("After click, waiting for '{}' to be present.", locatorToVerifyPresent);
        int waitSeconds = getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String errorMessage =
                format("Failure in clickAndVerifyPresent: element '%s' never became present after %d seconds!",
                        locatorToVerifyPresent, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.withMessage(errorMessage)
            .ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locatorToVerifyPresent));
    }

    
    public WebElement clickAndVerifyPresent(WebElement el, By locatorToVerifyPresent, TimeoutType timeout) {
        click(el, timeout);
        logger.info("After click, waiting for '{}' to be present.", locatorToVerifyPresent);
        int waitSeconds = getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String errorMessage =
                format("Failure in clickAndVerifyPresent: element '%s' never became present after %d seconds!",
                        locatorToVerifyPresent, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.withMessage(errorMessage)
            .ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locatorToVerifyPresent));
    }

    
    public WebElement clickAndVerifyVisible(By locatorToClick, By locatorToVerifyVisible, TimeoutType timeout) {
        click(locatorToClick, timeout);
        logger.info("After click, waiting for '{}' to be visible.", locatorToVerifyVisible);
        return verifyElementVisible(locatorToVerifyVisible, timeout);
    }

    
    public WebElement clickAndVerifyVisible(WebElement el, By locatorToVerifyVisible, TimeoutType timeout) {
        click(el, timeout);
        logger.info("After click, waiting for '{}' to be visible.", locatorToVerifyVisible);
        return verifyElementVisible(locatorToVerifyVisible, timeout);
    }

    
    public void clickAndVerifyNotPresent(By locatorToClick, By locatorToVerifyNotPresent, TimeoutType timeout) {
        click(locatorToClick, timeout);
        logger.info("After click, waiting for '{}' to NOT be present.", locatorToVerifyNotPresent);
        int waitSeconds = getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String errorMessage = format("Failure in clickAndVerifyNotPresent: element '%s' never became removed from the DOM after %d seconds!",
                locatorToVerifyNotPresent, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.withMessage(errorMessage)
            .ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.not(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locatorToVerifyNotPresent)));
    }

    
    public void clickAndVerifyNotPresent(WebElement el, By locatorToVerifyNotPresent, TimeoutType timeout) {
        click(el, timeout);
        logger.info("After click, waiting for '{}' to NOT be present.", locatorToVerifyNotPresent);
        int waitSeconds = getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String errorMessage = format("Failure in clickAndVerifyNotPresent: element '%s' never became removed from the DOM after %d seconds!",
                locatorToVerifyNotPresent, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.withMessage(errorMessage)
            .ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.not(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locatorToVerifyNotPresent)));
    }

    
    public void clickAndVerifyNotVisible(By locatorToClick, By locatorToVerifyNotVisible, TimeoutType timeout) {
        click(locatorToClick, timeout);
        logger.info("After click, waiting for '{}' to NOT be visible.", locatorToVerifyNotVisible);
        verifyElementInvisible(locatorToVerifyNotVisible, timeout);
    }

    
    public void clickAndVerifyNotVisible(WebElement el, By locatorToVerifyNotVisible, TimeoutType timeout) {
        click(el, timeout);
        logger.info("After click, waiting for '{}' to NOT be visible.", locatorToVerifyNotVisible);
        verifyElementInvisible(locatorToVerifyNotVisible, timeout);
    }

    
    public void clickAndSelectFromList(By locatorToClick, By popoverLocator) {
        invokeMenuItemAndSelect(getElement(locatorToClick), popoverLocator);
    }

    
    public void clickAndSelectFromList(WebElement clickable, By popoverLocator) {
        invokeMenuItemAndSelect(clickable, popoverLocator);
    }
    

    
    public String getWebPageReadyState() throws Exception {
        return (String) executeJavascript("return document.readyState;");
    }

    
    public void waitForWebPageReadyStateToBeComplete() {
        final int waitSeconds = timeoutsConfig.getPageLoadTimeoutSeconds();
        waitOnPredicate(new Predicate() {
            
            public boolean apply(@Nullable Object o) {
                try {
                    String readyState = getWebPageReadyState();
                    return Objects.equals(readyState, "complete");
                } catch (Exception e) {
                    return false;
                }
            }
        },
        String.format("Error - web page never reached document.readyState='complete' after %d seconds", waitSeconds),
        TimeoutType.PAGE_LOAD_TIMEOUT);
        logger.info("Success - Waited for document.readyState to be 'complete' on page: " + webDriver().getCurrentUrl());
    }


    /**
     * According to Selenium Javadoc, this is the correct way to check for existence of an element.
     */
	/*
    
    

    
    public WebElement findElementContainingText(By locator, String text) {
        List<WebElement> matches = findElements(locator, null);
        for (WebElement el : matches) {
            try {
                if (el.getText().contains(text)) {
                    logger.info("SUCCESS: Found web element containing text '{}' with locator '{}'", text, locator);
                    return el;
                }
            } catch (Exception e) { //Don't fail just because one web element was stale. Continue searching for the text.
                logger.debug("Exception while searching for web elements containing text '{}' with locator '{}'", text, locator);
                logger.debug(Throwables.getStackTraceAsString(e));
            }
        }
        return null;
    }

    
    public WebElement findVisibleElementContainingText(By locator, String text) {
        List<WebElement> matches = findElements(locator, null);
        for (WebElement el : matches) {
            try {
                if (el.getText().contains(text) && el.isDisplayed()) {
                    logger.info("SUCCESS: Found visible web element containing text '{}' with locator '{}'", text, locator);
                    return el;
                }
            } catch (Exception e) { //Don't fail just because one web element was stale. Continue searching for the text.
                logger.debug("Exception while searching for web elements containing text '{}' with locator '{}'", text, locator);
                logger.debug(Throwables.getStackTraceAsString(e));
            }
        }
        return null;
    }

    
    public WebElement findVisibleElementContainingTextWithWait(final By locator, final String text, TimeoutType timeout) {
        int waitSeconds = getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String message = String.format("Timeout waiting %d seconds to find element containing text '%s' with locator '%s'",
                waitSeconds, text, locator.toString());
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.ignoring(StaleElementReferenceException.class)
                .withMessage(message);

        return wait.until(new ExpectedCondition<WebElement>() {
            
            public WebElement apply(@Nullable WebDriver input) {
                WebElement el = findVisibleElementContainingText(locator, text);
                if (el == null) {
                    GeneralUtils.waitOneSecond();
                }
                return el;
            }
        });
    }

    
    public WebElement findElementWithRefresh(final By locator, TimeoutType timeout) {
        return findElementContainingTextWithRefresh(locator, "", timeout);
    }

    
    public WebElement findElementContainingTextWithRefresh(final By locator, final String text, TimeoutType timeout) {
        int waitSeconds = getTimeout(timeoutsConfig.getPollingWithRefreshTimeoutSeconds(), timeout);
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.ignoring(StaleElementReferenceException.class);

        logger.info("Waiting for element containing text '{}' defined by locator '{}', timeout of {} seconds", new Object[]{text, locator, waitSeconds});
        try {
            WebElement found = wait.until(new ExpectedCondition<WebElement>() {
                
                public WebElement apply(@Nullable WebDriver input) {
                    long start = new Date().getTime();
                    while ((new Date().getTime() - start) / 1000 < timeoutsConfig.getPauseBetweenRefreshSeconds()) {
                        WebElement el = findElementContainingText(locator, text);
                        if (el != null) {
                            return el;
                        }
                        GeneralUtils.waitMillis(timeoutsConfig.getPauseBetweenTriesMillis());
                    }
                    getBrowser().refreshPage();
                    return null;
                }
            });
            logger.info("Success finding element containing text '{}' defined by locator '{}'!", text, locator);
            return found;
        } catch (TimeoutException e) {
            logger.error("Timeout waiting to find text '{}' in an element matching locator '{}'", text, locator);
            throw new TimeoutException(
                    format("Timeout waiting to find text '%s' in an element matching locator '%s'", text, locator));
        }
    }

    
    public WebElement findVisibleElementWithRefresh(final By locator, TimeoutType timeout) {
        return findVisibleElementContainingTextWithRefresh(locator, "", timeout);
    }

    
    public WebElement findVisibleElementContainingTextWithRefresh(final By locator, final String text, TimeoutType timeout) {
        int waitSeconds = getTimeout(timeoutsConfig.getPollingWithRefreshTimeoutSeconds(), timeout);
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.ignoring(StaleElementReferenceException.class);

        logger.info("Waiting for element containing text '{}' defined by locator '{}', timeout of {} seconds", new Object[]{text, locator, waitSeconds});
        try {
            WebElement found = wait.until(new ExpectedCondition<WebElement>() {
                
                public WebElement apply(@Nullable WebDriver input) {
                    long start = new Date().getTime();
                    while ((new Date().getTime() - start) / 1000 < timeoutsConfig.getPauseBetweenRefreshSeconds()) {
                        WebElement el = findVisibleElementContainingText(locator, text);
                        if (el != null) {
                            return el;
                        }
                        GeneralUtils.waitMillis(timeoutsConfig.getPauseBetweenTriesMillis());
                    }
                    getBrowser().refreshPage();
                    return null;
                }
            });
            logger.info("Success finding element containing text '{}' defined by locator '{}'!", text, locator);
            return found;
        } catch (TimeoutException e) {
            logger.error("Timeout waiting to find text '{}' in an element matching locator '{}'", text, locator);
            throw new TimeoutException(
                    format("Timeout waiting to find text '%s' in an element matching locator '%s'", text, locator));
        }
    }

    
    public WebElement findElementContainingTextWithWait(final By locator, final String text, TimeoutType timeout) {
        final int waitSeconds = getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String msg = format("Failure in findElementContainingTextWithWait: never found text '%s' in element " +
                                  "with locator '%s' with timeout of %d seconds", text, locator, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.ignoring(StaleElementReferenceException.class)
            .withMessage(msg);

        return wait.until(new ExpectedCondition<WebElement>() {
            
            public WebElement apply(@Nullable WebDriver input) {
                WebElement el = findElementContainingText(locator, text);
                if (el == null) {
                    GeneralUtils.waitOneSecond();
                }
                return el;
            }
        });
    }

    
    public WebElement findElementContainingChild(final By parentLocator, final By childLocator) {
        List<WebElement> parents = webDriver().findElements(parentLocator);
        for (WebElement el: parents) {
            try {
                List<WebElement> subChildren = el.findElements(childLocator);
                if (subChildren.size() > 0) {
                    return el;
                }
            } catch (WebDriverException e) {
                logger.debug("Exception occurred finding sub-children in findElementContainingChild:", e);
            }
        }
        return null;
    }

    
    public List<WebElement> findElementsContainingChild(final By parentLocator, final By childLocator) {
        List<WebElement> parents = webDriver().findElements(parentLocator);
        List<WebElement> parentsWithChild = Lists.newArrayList();
        for (WebElement el: parents) {
            try {
                List<WebElement> subChildren = el.findElements(childLocator);
                if (subChildren.size() > 0) {
                    parentsWithChild.add(el);
                }
            } catch (WebDriverException e) {
                logger.debug("Exception occurred finding sub-children in findElementsContainingChild:", e);
            }
        }
        return parentsWithChild;
    }

    
    public WebElement findElementContainingChildWithWait(final By parentLocator, final By childLocator, TimeoutType timeout) {
        final int waitSeconds = getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String msg = format("Failure in findElementContainingChildWithWait: never found element " +
                "with locator '%s' having child with locator '%s' with timeout of %d seconds", parentLocator, childLocator, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.ignoring(StaleElementReferenceException.class)
                .withMessage(msg);

        return wait.until(new ExpectedCondition<WebElement>() {
            
            public WebElement apply(@Nullable WebDriver input) {
                return findElementContainingChild(parentLocator, childLocator);
            }
        });
    }

    
    public List<WebElement> findElementsContainingChildWithWait(final By parentLocator, final By childLocator, TimeoutType timeout) {
        final int waitSeconds = getTimeout(timeoutsConfig.getWebElementPresenceTimeoutSeconds(), timeout);
        final String msg = format("Failure in findElementContainingChildWithWait: never found element " +
                "with locator '%s' having child with locator '%s' with timeout of %d seconds", parentLocator, childLocator, waitSeconds);
        WebDriverWait wait = new WebDriverWait(webDriver(), waitSeconds);
        wait.ignoring(StaleElementReferenceException.class)
                .withMessage(msg);

        return wait.until(new ExpectedCondition<List<WebElement>>() {
            
            public List<WebElement> apply(@Nullable WebDriver input) {
               List<WebElement> parents = findElementsContainingChild(parentLocator, childLocator);
               if (parents.size() > 0) {
                   return parents;
               }
               return null;
            }
        });
    }

    
    @Nullable
    public WebElement getChildElement(By locator, WebElement parentEl) {
        List<WebElement> elements = findElements(locator, parentEl);
        if (elements.size() > 0) {
            return elements.get(0);
        }
        return null;
    }

 

    
    @Nonnull
    public List<WebElement> getChildElements(By locator, WebElement parentEl) {
        return findElements(locator, parentEl);
    }

    
    @Nonnull
    public List<WebElement> getElements(By locator) {
        return findElements(locator, null);
    }

    public WebElement inputTextAndSelectFromList(WebElement inputField, String value, By popoverLocator) throws SeleniumActionsException {
        return inputTextAndSelectFromList(inputField, value, popoverLocator, 0);         // default is no retries
    }

    
    public WebElement inputTextAndSelectFromList(WebElement inputField, String value, By popoverLocator,
                                                 int withRetryCount) throws SeleniumActionsException {
        return enterTextAndSelectFromList(inputField, value, popoverLocator, withRetryCount, false);
    }

   
    public WebElement inputTextSlowlyAndSelectFromList(WebElement inputField, String value, By popoverLocator) throws SeleniumActionsException {
        return inputTextSlowlyAndSelectFromList(inputField, value, popoverLocator, 0);      // default is no retries
    }

    
    public WebElement inputTextSlowlyAndSelectFromList(WebElement inputField, String value, By popoverLocator,
                                                       int withRetryCount) throws SeleniumActionsException {
        return enterTextAndSelectFromList(inputField, value, popoverLocator, withRetryCount, true);
    }

    
    public void enterTextForAutoCompleteAndSelectFirstMatch(By inputLocator, String text, By popoverLocator,
                                                            String requiredPopupText) {
        enterTextForAutoCompleteAndSelectFirstMatch(inputLocator, 0, text, popoverLocator, requiredPopupText);
    }

    
    public void enterTextForAutoCompleteAndSelectFirstMatch(By inputLocator, int minChars, String text, By popoverLocator,
                                                            String requiredPopupText) {
        if (minChars > text.length()) {
            throw new RuntimeException(format("Minimum characters to enter (%d) is greater than the length of the input text '%s'!", minChars, text));
        }
        scrollIntoView(inputLocator);
        if (minChars > 0) {
            inputText(inputLocator, text.substring(0, minChars));
        }
        for (int i = minChars; i < text.length(); i++) {
            String oneChar = String.valueOf(text.charAt(i));
            inputText(inputLocator, oneChar);

            // If the last char is being entered, wait 5 full seconds for the expected popup. Otherwise, wait 1 second.
            TimeoutType timeout = (i == text.length() - 1) ? TimeoutType.FIVE_SECONDS : TimeoutType.ONE_SECOND;
            try {
                WebElement matchingPopup = findElementContainingTextWithWait(popoverLocator, requiredPopupText, timeout);
                if (matchingPopup != null) {
                    try {
                        getActionsBuilder().moveToElement(matchingPopup)
                                .pause(500) // Sometimes javascript needs a moment to register that it's being hovered.
                                .click()
                                .perform();
                        logger.info("Success - clicked popup for autocomplete text \"{}\"", text);
                        return;
                    } catch (Exception e) {
                        logger.debug("Exception clicking popup from autocomplete.", e);
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        throw new RuntimeException(format("No popup defined by Locator  '%s' found with required text '%s'", popoverLocator, requiredPopupText));
    }

    
    public void inputTinyMceText(String text) {
        waitForTinyMceToBeReady();
        ((JavascriptExecutor) webDriver()).executeScript(format("tinyMCE.activeEditor.setContent(\"%s\")", text));
    }

    
    public void waitForTinyMceToBeReady() {
        waitForJavascriptSymbolToBeDefined("tinyMCE", TimeoutType.DEFAULT);
        waitForJavascriptSymbolToBeDefined("tinyMCE.activeEditor", TimeoutType.DEFAULT);
        waitForJavascriptSymbolToHaveValue("tinyMCE.activeEditor.initialized", "true", TimeoutType.DEFAULT);
    }

    //////////////////////////////////////Timeouts//////////////////////////////////////////////
    
    public TimeoutsConfig getTimeoutsConfig() {
        return timeoutsConfig;
    }

    protected int getTimeout(int defaultTimeout, TimeoutType timeout) {
        return timeout == TimeoutType.DEFAULT ? defaultTimeout : timeoutsConfig.getTimeoutInSeconds(timeout);
    }

    //////////////////////////////////// helpers ////////////////////////////////////////////////

    /**
     * Convenient helper to Find Elements from either top page element or from parent element.
     *
     * @param locator      - Locator defining the input element
     * @param parentEl - Parent web element. If this is provided the search will be from the parent element. If null, search will be from top element
     * @return - List of elements
     */
	
	/*
    protected List<WebElement> findElements(By locator, WebElement parentEl) {
        if (parentEl == null) {
            return webDriver().findElements(locator);
        } else {
            return parentEl.findElements(locator);
        }
    }

    protected WebElement findElement(By locator, WebElement parentEl) {
        if (parentEl == null) {
            return webDriver().findElement(locator);
        } else {
            return parentEl.findElement(locator);
        }
    }

    protected WebElement enterTextAndSelectFromList(WebElement inputField, String value, By popoverLocator,
                                                    int withRetryCount, boolean slowly) throws SeleniumActionsException {
        boolean done = false;
        int initialCount = withRetryCount;

        do {
            try {
                enterTextAndSelectFromList(inputField, value, popoverLocator, slowly);
                done = true;
            } catch (Exception ex) {
                logger.error("Caught an exception " + ex.getMessage());
            }
            withRetryCount--;
        } while (!done && withRetryCount > 0);

        // Need to subtract 1, so that we have 0 retries if we succeeded on the first try.
        int numberOfUsedRetries = initialCount - withRetryCount - 1;
        if (numberOfUsedRetries > 0) {
            logger.warn(done ?
                    format("Entered text successfully and selected locator '%s' from list after %d retries", popoverLocator, numberOfUsedRetries) :
                    format("Failed to enter text and select locator '%s' from list.", popoverLocator));
        }
        if (!done) {
            throw new SeleniumActionsException(format("Failed to inputTextAndSelectFromList after %d retries", numberOfUsedRetries));
        }
        return inputField;
    }

    protected void enterTextAndSelectFromList(WebElement inputField, String value, By popoverLocator, boolean slowly) {
        clearText(inputField);
        if (slowly) {
            inputTextSlowly(inputField, value);
        } else {
            inputText(inputField, value);
        }
        verifyElementPresented(popoverLocator, TimeoutType.DEFAULT);
        click(popoverLocator, TimeoutType.DEFAULT);
    }

    protected void invokeMenuItemAndSelect(WebElement clickable, By popoverLocator) {
        Preconditions.checkNotNull(clickable, "Input WebElement cannot be null");
        waitUntilClickable(clickable, TimeoutType.DEFAULT);
        click(clickable, TimeoutType.DEFAULT);
        verifyElementPresented(popoverLocator, TimeoutType.DEFAULT);
        waitUntilClickable(popoverLocator, TimeoutType.DEFAULT);
        click(popoverLocator, TimeoutType.DEFAULT);
    }

    //**********~~~~~~~~~~~~~ Verify Class Actions ~~~~~~~~~~~~~~~*************
    public boolean doesElementHaveClass(By locator, String locatorClass) {
        WebElement el = verifyElementPresented(locator, TimeoutType.DEFAULT);
        return WebElementHelpers.webElementHasClass(el, locatorClass);
    }

    public WebElement verifyElementHasClass(final By locator, final String locatorClass, TimeoutType timeout) {
        return waitOnFunction(new Function<SeleniumActions, WebElement>() {
                                  @Nullable
                                  
                                  public WebElement apply(SeleniumActions input) {
                                      WebElement el = input.verifyElementPresented(locator, TimeoutType.DEFAULT);
                                      if (WebElementHelpers.webElementHasClass(el, locatorClass)) {
                                          return el;
                                      }
                                      return null;
                                  }
                              }, this,
                format("Waiting for element that matches locator '%s' to have class '%s'", locator, locatorClass),
                timeout);
    }

    public WebElement verifyElementDoesNotHaveClass(final By locator, final String locatorClass, TimeoutType timeout) {
        return waitOnFunction(new Function<SeleniumActions, WebElement>() {
                                  @Nullable
                                  
                                  public WebElement apply(SeleniumActions input) {
                                      WebElement el = input.verifyElementPresented(locator, TimeoutType.DEFAULT);
                                      if (!WebElementHelpers.webElementHasClass(el, locatorClass)) {
                                          return el;
                                      }
                                      return null;
                                  }
                              }, this,
                format("Waiting for element that matches locator '%s' to NOT have class '%s'", locator, locatorClass),
                timeout);
    }*/
    

	public WebElement findElementContainingTextWithWait(By locator, String text, int timeout){
		System.out.println("TBD");
		return null;
		
	}
	
	public WebElement findVisibleElementContainingTextWithWait(final By locator, final String text, int timeout){
		System.out.println("TBD");
		return null;
	}
}
