package me.synology.murali.eaglebook;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.synology.murali.eaglebook.model.Booking;
import me.synology.murali.eaglebook.model.Court;
import me.synology.murali.eaglebook.model.CourtPref;
import me.synology.murali.eaglebook.model.Duration;


public class BookingHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingHandler.class);
	
	private final Booking booking;
	
	private WebDriver driver;
	
	
	public BookingHandler(Booking booking) {
		this.booking = booking;
	}
	
	
	public Booking getBooking() {
		return this.booking;
	}
	
	
	public void openBrowser() {
		LOGGER.info("Open browser for " + booking.getName());
		if (booking.isUseProxy()) {
			Proxy proxy = new Proxy();
			proxy.setProxyType(Proxy.ProxyType.PAC);
			proxy.setProxyAutoconfigUrl(booking.getProxyConfig());
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.PROXY, proxy);
			driver = new FirefoxDriver(capabilities);
			
		} else {
			driver = new FirefoxDriver();
		}
		driver.get(booking.getUrl());
		LOGGER.info("Completed opening browser for " + booking.getName());
	}
	
	
	public void login() {
		
		LOGGER.info("Login for " + booking.getName());
		
		WebElement idField = driver.findElement(By.id("ctl00_MainContent_InputLogin"));
		idField.sendKeys(booking.getUserId());
		WebElement pinField = driver.findElement(By.id("ctl00_MainContent_InputPassword"));
		pinField.sendKeys(booking.getUserPin());
		
		WebElement submitBtn = driver.findElement(By.id("ctl00_MainContent_btnLogin"));
		submitBtn.click();
		
		LOGGER.info("Completed Login for " + booking.getName());
	}
	
	
	public final void getToSelectSlot() {
		LOGGER.info("Get to select slot for " + booking.getName());
		WebElement makeBookBtn = driver.findElement(By.id("ctl00_ctl11_Li1"));
		makeBookBtn.click();
		
	}
	
	
	public final void getCentreAndHall() {
		
		// WebElement becontreeBtn = driver.findElement(By.id("ctl00_MainContent_sitesGrid_ctrl1_lnkListCommand"));
		// becontreeBtn.click();
		
		WebElement sportHallBtn = driver
			.findElement(By.id("ctl00_MainContent_activityGroupsGrid_ctrl0_lnkListCommand"));
		sportHallBtn.click();
		
		LOGGER.info("Completed get to centre and hall for " + booking.getName());
		
	}
	
	
	public final void selectDuration() {
		LOGGER.info("Selecting duration for " + booking.getName());
		
		String id = null;
		if (booking.getDuration() == Duration.SIXTY) {
			id = "ctl00_MainContent_activitiesGrid_ctrl1_lnkListCommand";
		} else if (booking.getDuration() == Duration.THIRTY) {
			id = "ctl00_MainContent_activitiesGrid_ctrl0_lnkListCommand";
		}
		WebElement durationBtn = driver.findElement(By.id(id));
		durationBtn.click();
		LOGGER.info("Completed selecting duration for " + booking.getName());
		
	}
	
	
	public void refresh() {
		LOGGER.info("Refreshing page for " + booking.getName());
		driver.navigate().refresh();
	}
	
	
	public final void checkDateAvailable1() {
		
		LOGGER.info("Checking date (1) for " + booking.getName());
		
		WebElement forwardBtn = driver.findElement(By.id("ctl00_MainContent_dateForward1"));
		forwardBtn.click();
		
	}
	
	
	public final SlotAvailable checkDateAvailable2() {
		
		LOGGER.info("Checking date (2) for " + booking.getName());
		
		SlotAvailable slotAvailable = SlotAvailable.DATE_UNAVAILABLE;
		
		String newestDate = driver.findElements(By.xpath("//table[@id='slotsGrid']//th[2]")).get(0).getText();
		
		LOGGER.info("Checking for date for " + booking.getDate() + ", latest date is " + newestDate);
		
		if (Utils.formatDayDate(newestDate).startsWith(booking.getDate())) {
			slotAvailable = SlotAvailable.DATE_AVAILABLE;
		}
		
		LOGGER.info("Completed checking date for " + booking.getName());
		
		return slotAvailable;
		
	}
	
	
	public final SlotAvailable selectSlot() {
		LOGGER.info("Selecting slot " + booking.getName());
		SlotAvailable slotAvailable = SlotAvailable.SLOT_UNAVAILABLE;
		WebElement table = driver.findElement(By.id("slotsGrid"));
		List<WebElement> dates = table.findElements(By.tagName("th"));
		int sCol = 0;
		for (int i = (dates.size() - 1); i >= 0; i--) {
			if (Utils.formatDayDate(dates.get(i).getText()).startsWith(booking.getDate())) {
				sCol = i;
				break;
			}
		}
		
		int sRow = 0;
		List<WebElement> slots = table.findElements(By.tagName("tr"));
		outer: for (int i = (slots.size() - 1); i >= 0; i--) {
			if (slots.get(i) != null) {
				List<WebElement> cells = slots.get(i).findElements(By.tagName("td"));
				if (cells != null && cells.size() > 0) {
					String timeSlot = cells.get(0).getText();
					LOGGER.info("Checking select slot against " + timeSlot);
					if (timeSlot.startsWith(booking.getSlotTime())) {
						LOGGER.info("Matched time slot against " + timeSlot);
						
						WebElement slot = cells.get(sCol);
						if ("itemavailable".equals(slot.getAttribute("class"))) {
							slotAvailable = SlotAvailable.SLOT_AVAILABLE;
							slot.click();
							sRow = i;
						}
						break outer;
					}
				}
			}
		}
		if (slotAvailable == SlotAvailable.SLOT_AVAILABLE) {
			LOGGER.info("Found a slot at column " + (sCol + 1) + " and row " + sRow);
		}
		LOGGER.info("Finished selecting slot " + booking.getName());
		
		return slotAvailable;
		
	}
	
	
	public final SlotAvailable selectSpecific() {
		
		LOGGER.info("Selecting sepcific for " + booking.getName());
		
		String specificTime = booking.getSpecificTime();
		
		SlotAvailable specificAvailable = SlotAvailable.SPECIFIC_UNAVAILABLE;
		
		WebElement table = driver.findElement(By.id("ctl00_MainContent_grdResourceView"));
		
		List<WebElement> specifics = table.findElements(By.tagName("tr"));
		
		outer: for (int i = (specifics.size() - 1); i >= 0; i--) {
			specificAvailable = selectSpecific(i, specificTime, specifics);
			if (specificAvailable == SlotAvailable.SPECIFIC_UNAVAILABLE) {
				LOGGER.info("Specific not found " + specificAvailable + ", continuing ...");
				continue outer;
			} else if (specificAvailable == SlotAvailable.COURT_AVAILABLE) {
				break outer;
			} else if (specificAvailable == SlotAvailable.COURT_UNAVAILABLE) {
				String altSpecificTime = booking.getAltSpecificTime();
				Integer rowMoves = booking.getRowMoves();
				
				LOGGER.info("Court unavialble for specific time " + specificTime + " trying alternative "
					+ booking.getAltSpecific()
					+ " of " + altSpecificTime + " with " + rowMoves + " row moves");
					
				specificAvailable = selectSpecific((i + rowMoves), altSpecificTime, specifics);
				
				break outer;
			}
			
		}
		LOGGER.info("Finished selecting sepcific for " + booking.getName());
		
		return specificAvailable;
		
	}
	
	
	private boolean onRightRow(String specificTime, List<WebElement> cells, List<WebElement> availables) {
		
		boolean onRightRow = false;
		
		for (WebElement cell : cells) {
			if (specificTime.equals(cell.getText())) {
				onRightRow = true;
				return onRightRow;
			}
		}
		
		for (WebElement available : availables) {
			if (specificTime.equals(available.getAttribute("value"))) {
				onRightRow = true;
				return onRightRow;
			}
		}
		return onRightRow;
		
	}
	
	
	private SlotAvailable selectSpecific(int row, String specificTime,
		List<WebElement> specifics) {
		SlotAvailable specificAvailable = SlotAvailable.SPECIFIC_UNAVAILABLE;
		
		LOGGER.info("Checking select specific on row " + row);
		
		outer: if (specifics.get(row) != null) {
			List<WebElement> cells = specifics.get(row).findElements(By.tagName("td"));
			List<WebElement> availables = specifics.get(row).findElements(By.tagName("input"));
			if (cells != null && cells.size() > 0) {
				// LOGGER.info("Checking select specific against " + availables.get(0).getText());
				if (onRightRow(specificTime, cells, availables)) {
					specificAvailable = SlotAvailable.SPECIFIC_AVAILABLE;
					// LOGGER.info("Matched specific against " + availables.get(0).getText());
					for (int j_ = 0; j_ < availables.size(); j_++) {
						int courtNum = getCourtNum(booking, j_, availables.size());
						LOGGER.info("Checking court in column " + courtNum);
						WebElement court = availables.get(courtNum);
						court.click();
						specificAvailable = SlotAvailable.COURT_AVAILABLE;
						break outer;
						// try {
						// if ("itemavailable".equals(court.getAttribute("class"))) {
						// court.click();
						// specificAvailable = SlotAvailable.COURT_AVAILABLE;
						// LOGGER.info("Found court " + Court.ordinal(courtNum)
						// + " @ " + specificTime + " for " + booking.getUserName());
						// break outer;
						// } else {
						// LOGGER.info("Court unavailable in column " + courtNum + " - trying next court");
						// }
						//
						// } catch (Exception e) {
						// LOGGER.info("Court selection exception in column " + courtNum + "- trying next court");
						// }
					}
					specificAvailable = SlotAvailable.COURT_UNAVAILABLE;
					break outer;
				}
			}
		}
		
		return specificAvailable;
	}
	
	
	private int getCourtNum(Booking booking, int iterPoint, int size) {
		CourtPref courtPref = booking.getCourtPref();
		if (courtPref == CourtPref.LOW) {
			return iterPoint;
		}
		if (courtPref == CourtPref.HIGH) {
			return size - 1 - iterPoint;
		}
		// Should not get here ..
		return -1;
	}
	
	
	public void book() {
		LOGGER.info("Booking for " + booking.getName());
		WebElement idField = driver.findElement(By.id("ctl00_MainContent_txtAddInfo"));
		idField.sendKeys("M booked for " + booking.getUserName());
		
		WebElement bookBtn = driver.findElement(By.id("ctl00_MainContent_btnBasket"));
		bookBtn.click();
		LOGGER.info("Finished booking for " + booking.getName());
		
	}
	
	
	public void back() {
		LOGGER.info("Navigating back for " + booking.getName());
		driver.navigate().back();
	}
	
}
