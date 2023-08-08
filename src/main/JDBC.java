package main;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class JDBC {
	public static void main(String[] args) {
		startingPage();
	}
	
	public static void startingPage() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome! Please input the following:\n"
				+ "1: If you are an existing user"
				+ "\n2: If you are a new user"
				+ "\n3: If you want to exit");
		int choice = scan.nextInt();
		while(true) {
			if(choice == 1) {		
				existingUser();
				System.out.println("\nThanks for using MyBnB. Have a great day!");
				return;
			} else if(choice == 2) {
				newUser();
				System.out.println("\nThanks for using MyBnB. Have a great day!");
				return;
			} else if(choice == 3) {
				System.out.println("\nThanks for using MyBnB. Have a great day!");
				return;
			} else {
				System.out.println("Error! Please enter a valid answer.");
				System.out.println("1: If you are an existing user"
						+ "\n2: If you are a new user"
						+ "\n3: If you want to exit");
				choice = scan.nextInt();
			}
		}
		
	}
	
	public static void existingUser() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nPlease enter your username:");
		String username = scan.nextLine();
		System.out.println("\nPlease enter your password:");
		String password = scan.nextLine();
		int output = User.login(username, password); // 0 means incorrect, 1 means renter, 2 means host.
		while(output == 0) {
			System.out.println("\nError logging in, please re-enter you username:");
			username = scan.nextLine();
			System.out.println("\nPlease enter your password:");
			password = scan.nextLine();
			output = User.login(username, password); // 0 means incorrect, 1 means renter, 2 means host.
		}
		System.out.println("\nLogin Successful!");
		if(output == 1) {
			renter(username);
		} else if(output == 2) {
			host(username);
		}
	}
	
	public static void newUser() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nPlease enter your username:");
		String username = scan.nextLine();
		while(User.checkUser(username)) {
			System.out.println("\nError, current username is being used. \nPlease retry with a different username:");
			username = scan.nextLine();
		}
		System.out.println("\nPlease enter your password:");
		String password = scan.nextLine();
		System.out.println("\nPlease enter your first name:");
		String first = scan.nextLine();
		System.out.println("\nPlease enter your last name:");
		String last = scan.nextLine();
		System.out.println("\nPlease enter your DOB in the form DD/MM/YYYY:");
		String DOB = scan.nextLine();
		System.out.println("\nPlease enter your address:");
		String address = scan.nextLine();
		System.out.println("\nPlease enter your occupation:");
		String occupation = scan.nextLine();
		System.out.println("\nPlease enter your SIN:");
		String SIN = scan.nextLine();
		System.out.println("\nPlease enter 'host' if you want to be a host and 'renter' if you want to be a renter:");
		String host = scan.nextLine();
		int hostbool = 0;
		while(!host.equals("host") && !host.equals("renter")) {
			System.out.println("\nError, invalid answer. Please enter 'host' or 'renter':");
			host = scan.nextLine();
		}
		if(host.equals("host")) {
			hostbool = 1;
		} else if(host.equals("renter")) {
			hostbool = 0;
		}
		User.createUser(username,password,DOB,address,occupation,SIN,first,last,hostbool);
		if(hostbool == 1) {
			host(username);
		} else {
			renter(username);
		}
		
	}
	
	public static void host(String username) {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nWelcome to the MyBnB host homepage!"
				+ "\nYou are logged in as '" + username + "'");
		boolean run = true;
		while(run) {
			System.out.println("\nPlease choose what you would like to do today:"
					+ "\n1: Make a new listing"
					+ "\n2: Look at all your current listings"
					+ "\n3: Run Reports"
					+ "\n4: Host Toolkit"
					+ "\n5: Exit"
					+ "\n6: Delete Account");
			int choice = scan.nextInt();
			switch(choice) {
				case 1:
					makeNewListing(username);
					break;
				case 2:
					hostCurrentListings(username);
					break;
				case 3:
					reports();
					break;
				case 4:
					hostToolkit();
					break;
				case 5:
					run = false;
					break;
				case 6:
					deleteAccount(username, 0);
					run = false;
					break;
			}
		}
	}
	
	public static void renter(String username) {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nWelcome to the MyBnB renter homepage!"
				+ "\nYou are logged in as '" + username + "'");
		boolean run = true;
		while(run) {
			System.out.println("\nPlease choose what you would like to do today:"
					+ "\n1: Search up all current listings from the database"
					+ "\n2: Search up listings with postal code"
					+ "\n3: Search up listings with address"
					+ "\n4: Search up listings with latitude and longitude"
					+ "\n5: Check current bookings"
					+ "\n6: Run Reports"
					+ "\n7: Exit"
					+ "\n8: Delete Account");
			int choice = scan.nextInt();
			switch(choice) {
				case 1:
					allListings(username);
					break;
				case 2:
					postalCodeSearch(username);
					break;
				case 3:
					addressSearch(username);
					break;
				case 4:
					latlongSearch(username);
					break;
				case 5:
					currentBookings(username);
					break;
				case 6:
					reports();
					break;
				case 7:
					run = false;
					break;
				case 8:
					deleteAccount(username, 0);
					run = false;
					break;
			}
		}	
	}
	
	public static void makeNewListing(String username) {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nWelcome to the make new listing page!"
				+ "\n\nPlease enter address of new listing");
		String address = scan.nextLine();
		while(Listings.checkListing(username, address)) {
			System.out.println("Error! Listing already exists"
					+ "\nPlease try a different address");
			address = scan.nextLine();
		}
		
		System.out.println("\nPlease enter latitude of listing");
		String latitude = scan.nextLine();
		System.out.println("\nPlease enter longitude of listing");
		String longitude = scan.nextLine();
		System.out.println("\nPlease enter type of new listing(house, apartment, room)");
		String type = scan.nextLine();
		System.out.println("\nPlease enter postal code of listing");
		String postal = scan.nextLine();
		System.out.println("\nPlease enter city of listing");
		String city = scan.nextLine();
		System.out.println("\nPlease enter country of listing");
		String country = scan.nextLine();
		System.out.println("\nPlease enter the amenities of listing");
		String amenities = scan.nextLine();
		System.out.println("\nPlease enter price of listing");
		int price = Integer.parseInt(scan.nextLine());
		System.out.println("\nPlease enter start date of listing in form YYYY-MM-DD");
		String startstring = scan.nextLine();
		System.out.println("\nPlease enter end date of listing in form YYYY-MM-DD");
		String endstring = scan.nextLine();
		Date startdate = formatDate(startstring);
		Date enddate = formatDate(endstring);
		Listings.createListing(type, latitude, longitude, address, postal, city, country, amenities, username, price, startdate, enddate);
		System.out.println("\nNew Listing created!");
		
	}
	
	public static void hostCurrentListings(String username) {
		Scanner scan = new Scanner(System.in);
		int[] listingIDs = new int[11];
		int index = 0;
		boolean run = true;
		while(run) {
			System.out.println("\nBelow show the listings:");
			listingIDs = Listings.print10ListingsWithUsername(username,index);
			if(listingIDs == null) {
				run = false;
				return;
			}
			System.out.println("\nEnter the number of the listing you are interested in."
					+ "\nEnter 0 if you would like to return"
					+ "\nEnter 11 if you would like to check the next 10");
			int choice = scan.nextInt();
			if(choice == 0) {
				run = false;
				return;
			} else if(choice>0 && choice<=10) {
				if(choice <= listingIDs[10]) {
					Listings.printListingWithID(listingIDs[choice-1]);
					System.out.println("\nPlease select a following choice:"
							+ "\n1: Edit current listing"
							+ "\n2: Delete current listing"
							+ "\n3: Return");
					int bookingchoice = scan.nextInt();
					if(bookingchoice == 1) {
						editCurrentListing(username, listingIDs[choice-1]);
					} else if(bookingchoice == 2) {
						Listings.deleteListing(username, listingIDs[choice-1]);
						System.out.println("\nListing successfully deleted!");
					} else if(bookingchoice != 3) {
						System.out.println("\nInvalid Choice!");
					}
				} else {
					System.out.println("\nInvalid Choice!");
				}
			} else if(choice == 11) {
				index+=10;
			}
		}
	}
	
	public static void editCurrentListing(String username, int listingID) {
		
	}
	
	public static void allListings(String username) {
		Scanner scan = new Scanner(System.in);
		int[] listingIDs = new int[11];
		int index = 0;
		boolean run = true;
		while(run) {
			listingIDs = Listings.print10Listings(index);
			if(listingIDs == null) {
				run = false;
				return;
			}
			System.out.println("\nEnter the number of the listing you are interested in."
					+ "\nEnter 0 if you would like to return"
					+ "\nEnter 11 if you would like to check the next 10");
			int choice = scan.nextInt();
			if(choice == 0) {
				run = false;
				return;
			} else if(choice>0 && choice<=10) {
				if(choice <= listingIDs[10]) {
					Listings.printListingWithID(listingIDs[choice-1]);
					System.out.println("\nPlease select a following choice:"
							+ "\n1: Book the current listing"
							+ "\n2: Return");
					int bookingchoice = scan.nextInt();
					if(bookingchoice == 1) {
						bookListing(listingIDs[choice-1], username);
						run=false;
					} else if(bookingchoice != 2) {
						System.out.println("\nInvalid Choice!");
					}
				} else {
					System.out.println("\nInvalid Choice!");
				}
			} else if(choice == 11) {
				index+=10;
			}
		}
	}
	
	public static Date formatDate(String datestring) {
		Date date = new Date();
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(datestring);
			return date;
		} catch (ParseException e) {
			System.out.println(e);
			return null;
		}
	}
	
	public static void postalCodeSearch(String username) {
		Scanner scan = new Scanner(System.in);
		int[] listingIDs = new int[11];
		int index = 0;
		boolean run = true;
		
		System.out.println("\nPlease enter a Postal Code to search:");
		String postal = scan.nextLine();
		while(run) {
			listingIDs = Listings.filteredSearch10Listings(postal, null, "postal", index);
			if(listingIDs == null) {
				run = false;
				return;
			}
			System.out.println("\nEnter the number of the listing you are interested in."
					+ "\nEnter 0 if you would like to return"
					+ "\nEnter 11 if you would like to check the next 10");
			int choice = scan.nextInt();
			if(choice == 0) {
				run = false;
				return;
			} else if(choice>0 && choice<=10) {
				if(choice <= listingIDs[10]) {
					Listings.printListingWithID(listingIDs[choice-1]);
					System.out.println("\nPlease select a following choice:"
							+ "\n1: Book the current listing"
							+ "\n2: Return");
					int bookingchoice = scan.nextInt();
					if(bookingchoice == 1) {
						bookListing(listingIDs[choice-1], username);
						run=false;
					} else if(bookingchoice != 2) {
						System.out.println("\nInvalid Choice!");
					}
				} else {
					System.out.println("\nInvalid Choice!");
				}
			} else if(choice == 11) {
				index+=10;
			}
		}
	}
	
	public static void addressSearch(String username) {
		Scanner scan = new Scanner(System.in);
		int[] listingIDs = new int[11];
		int index = 0;
		boolean run = true;
		
		System.out.println("\nPlease enter an Address to search:");
		String address = scan.nextLine();
		while(run) {
			listingIDs = Listings.filteredSearch10Listings(address, null, "address", index);
			if(listingIDs == null) {
				run = false;
				return;
			}
			System.out.println("\nEnter the number of the listing you are interested in."
					+ "\nEnter 0 if you would like to return"
					+ "\nEnter 11 if you would like to check the next 10");
			int choice = scan.nextInt();
			if(choice == 0) {
				run = false;
				return;
			} else if(choice>0 && choice<=10) {
				if(choice <= listingIDs[10]) {
					Listings.printListingWithID(listingIDs[choice-1]);
					System.out.println("\nPlease select a following choice:"
							+ "\n1: Book the current listing"
							+ "\n2: Return");
					int bookingchoice = scan.nextInt();
					if(bookingchoice == 1) {
						bookListing(listingIDs[choice-1], username);
						run=false;
					} else if(bookingchoice != 2) {
						System.out.println("\nInvalid Choice!");
					}
				} else {
					System.out.println("\nInvalid Choice!");
				}
			} else if(choice == 11) {
				index+=10;
			}
		}
	}
	
	public static void latlongSearch(String username) {
		Scanner scan = new Scanner(System.in);
		int[] listingIDs = new int[11];
		int index = 0;
		boolean run = true;
		
		System.out.println("\nPlease enter a longitude to search:");
		String longitude = scan.nextLine();
		System.out.println("\nPlease enter a latitude to search:");
		String latitude = scan.nextLine();
		while(run) {
			listingIDs = Listings.filteredSearch10Listings(longitude, latitude, "longitude", index);
			if(listingIDs == null) {
				run = false;
				return;
			}
			System.out.println("\nEnter the number of the listing you are interested in."
					+ "\nEnter 0 if you would like to return"
					+ "\nEnter 11 if you would like to check the next 10");
			int choice = scan.nextInt();
			if(choice == 0) {
				run = false;
				return;
			} else if(choice>0 && choice<=10) {
				if(choice <= listingIDs[10]) {
					Listings.printListingWithID(listingIDs[choice-1]);
					System.out.println("\nPlease select a following choice:"
							+ "\n1: Book the current listing"
							+ "\n2: Return");
					int bookingchoice = scan.nextInt();
					if(bookingchoice == 1) {
						bookListing(listingIDs[choice-1], username);
						run=false;
					} else if(bookingchoice != 2) {
						System.out.println("\nInvalid Choice!");
					}
				} else {
					System.out.println("\nInvalid Choice!");
				}
			} else if(choice == 11) {
				index+=10;
			}
		}
	}
	
	public static void bookListing(int listingID, String username) {
		Scanner scan = new Scanner(System.in);
		Date start = new Date();
		Date end = new Date();
		System.out.println("\nPlease enter the start date of the booking in the form YYYY-MM-DD");
		String startdate = scan.nextLine();
		System.out.println("\nPlease enter the end date of the booking in the form YYYY-MM-DD");
		String enddate = scan.nextLine();
		start = formatDate(startdate);
		end = formatDate(enddate);
		while(!Bookings.checkBookingDate(listingID, start, end)) {
			System.out.println("\nListing is not available in those dates!"
					+ "\nPlease try a different date.");
			System.out.println("\nPlease enter the start date of the booking in the form YYYY-MM-DD");
			start = formatDate(scan.nextLine());
			System.out.println("\nPlease enter the end date of the booking in the form YYYY-MM-DD");
			end = formatDate(scan.nextLine());
		}
		Bookings.createBooking(username, listingID, start, end);
		System.out.println("\nBooking created successfully!"
				+ "\nBringing you back to the main page.");
	}
	
	public static void currentBookings(String username) {
		Scanner scan = new Scanner(System.in);
		int index = 0;
		boolean run = true;
		int[] bookingIDs = new int[11];
		while(run) {
			bookingIDs = Bookings.print10CurrentBookings(username, index);
			System.out.println("\nEnter the number of the booking you want to look into."
					+ "\nEnter 0 if you would like to return"
					+ "\nEnter 11 if you would like to check the next 10");
			int choice = scan.nextInt();
			if(choice == 0) {
				run = false;
				return;
			} else if(choice>0 && choice<=10) {
				if(choice<=bookingIDs[10]) {
					Bookings.printBookingWithID(username, bookingIDs[choice-1]);
					System.out.println("\nPlease select a following choice:"
							+ "\n1: Cancel the current booking"
							+ "\n2: Return");
					int bookingchoice = scan.nextInt();
					if(bookingchoice == 1) {
						Bookings.cancelBooking(username, bookingIDs[choice-1]);
						System.out.println("\nCancellation complete! Returning you to the bookings page.\n");
					} else if(bookingchoice != 2) {
						System.out.println("\nInvalid Choice!");
					}
				}
			} else if(choice == 11) {
				index+=10;
			}
		}
	}
	
	public static void deleteAccount(String username, int host) {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nAre you sure you would like to delete the account ':"+username+"'"
				+ "\n1: Yes"
				+ "\n2: No");
		int choice = scan.nextInt();
		switch(choice) {
			case 1:
				User.deleteUser(username);
				System.out.println("\nUser successfully deleted!"
						+ "\nThe program will now restart");
				startingPage();
			case 2:
				if(host == 0) {
					renter(username);
				} else {
					host(username);
				}
		}
	}
	
	public static void hostToolkit() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\n\nWelcome to the Host Toolkit page!"
				+ "\nHere, you can enter in some details to get a recommended price for the listing"
				+ "\n1: Continue with the host toolkit."
				+ "\n0: Return to main page");
		int choice = Integer.parseInt(scan.nextLine());
		switch(choice) {
			case 1:
				System.out.println("\nPlease rate the city of your listing from 1-10."
						+ "\n(10 being a very popular city such as New York, Los Angeles)"
						+ "\n(1 being a very unknown city)");
				int cityrating = Integer.parseInt(scan.nextLine());
				System.out.println("\n\nPlease rate how convenient your listing is, based on factors such as public transport, driving and etc."
						+ "\n(10 being right next to public transport, or easy to navigate to)"
						+ "\n(1 being little to no public transport, or very long drive)");
				int conveniencerating = Integer.parseInt(scan.nextLine());
				System.out.println("\n\nPlease list down the amenities of your listing, seperating each with a comma."
						+ "\nIf there isn't any, leave it blank.");
				String amenities = scan.nextLine();
				int amenitiescount = 1;
				if(amenities.length()==0) {
					amenitiescount = 0;
				}
		        for(int i = 0; i < amenities.length(); i++)
		        {
		            if(amenities.charAt(i) == ',')
		                amenitiescount++;
		        }
		        System.out.println("\n\nPlease enter the size of your listing in square meters");
		        int size = Integer.parseInt(scan.nextLine());
		        double recoprice = cityrating*10 + conveniencerating*3 + amenitiescount*3 + size*0.01;
		        System.out.println("\n\nAfter running all the information through the algorithm, "
		        		+ "\nthe recommended price of the listing is: $" + recoprice + " per day" +
		        		"\nIf you would like to increase the price of your listing, try to provide"
		        		+ "\nthe users with more amenities. With every extra amenity provided,"
		        		+ "\nthe recommended price of listing goes up by $3 a day.");
		        
		        System.out.println("\n\nThank you for using the host toolkit. Press enter to go back to the main page.");
		        String unused = scan.nextLine();
				break;
			case 2:
				System.out.println("\nBringing you back to the main page.");
				break;
		}
	}
	
	public static void reports() {
		Scanner scan = new Scanner(System.in);
		String unused;
		System.out.println("\n\nWelcome to the reports page!"
				+ "\nHere, you can pick choices to generate reports based on your choice"
				+ "\n1: Provide the total number of bookings in a specific date range by city and by city and postal"
				+ "\n2: Provide the total number of listings per country, per country and city, and per country, city and postal"
				+ "\n3: Rank the hosts by the total number of listings they have overall per country and city"
				+ "\n4: Rank the renters by the number of bookings in a time period, and also per city"
				+ "\n5: Largest Number of Cancellations for Host and Renter"
				+ "\n6: Return to main page");
		int choice = Integer.parseInt(scan.nextLine());
		switch(choice) {
			case 1:
				System.out.println("\nPlease enter the starting date for the date range");
				Date start = formatDate(scan.nextLine());
				System.out.println("\nPlease enter the ending date for the date range");
				Date end = formatDate(scan.nextLine());
				System.out.println("\nPlease enter a choice:"
						+ "\n1: Total Number of Bookings by City"
						+ "\n2: Total Number of Bookings by City and Postal Code");
				int choice2 = Integer.parseInt(scan.nextLine());
				switch(choice2) {
					case 1:
						Reports.totalBookingsCityAndDate(start, end);
						System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
				        unused = scan.nextLine();
						break;
					case 2:
						Reports.totalBookingsCityAndZipCodeAndDate(start, end);
						System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
				        unused = scan.nextLine();
						break;
					default:
						System.out.println("Error, Wrong Choice!");
						break;
				}
				break;
				
			case 2:
				System.out.println("\nPlease enter a choice:"
						+ "\n1: Total Number of Listings Per Country"
						+ "\n2: Total Number of Listings Per Country and City"
						+ "\n3: Total Number of Listings Per Country, City and Postal Code");
				int choice3 = Integer.parseInt(scan.nextLine());
				switch(choice3) {
					case 1:
						Reports.totalListingsPerCountry();
						System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
				        unused = scan.nextLine();
						break;
					case 2:
						Reports.totalListingsPerCountryAndCity();
						System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
				        unused = scan.nextLine();
						break;
					case 3:
						Reports.totalListingsPerCountryAndCityAndPostal();
						System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
				        unused = scan.nextLine();
						break;
					default:
						System.out.println("Error, Wrong Choice!");
						break;
				}
				break;
			case 3:
				System.out.println("\nPlease enter a choice:"
						+ "\n1: Rank Hosts by Number of Listings Per Country"
						+ "\n2: Rank Hosts by Number of Listings Per Country Per City");
				int choice5 = Integer.parseInt(scan.nextLine());
				switch(choice5) {
					case 1:
						Reports.rankHostsNumberOfListingsPerCountry();
						System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
				        unused = scan.nextLine();
						break;
					case 2:
						Reports.rankHostsNumberOfListingsPerCountryPerCity();
						System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
				        unused = scan.nextLine();
						break;
				}
				break;
			case 4:
				System.out.println("\nPlease enter the starting date for the date range");
				Date start2 = formatDate(scan.nextLine());
				System.out.println("\nPlease enter the ending date for the date range");
				Date end2 = formatDate(scan.nextLine());
				System.out.println("\nPlease enter a choice:"
						+ "\n1: Rank Renters by Number of Bookings in Time Period"
						+ "\n2: Rank Renters by Number of Bookings in Time Period per city");
				int choice4 = Integer.parseInt(scan.nextLine());
				switch(choice4) {
					case 1:
						Reports.rankRentersBookingsInTimePeriod(start2, end2);
						System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
				        unused = scan.nextLine();
						break;
					case 2:
						Reports.rankRentersBookingsInTimePeriod(start2, end2);
						System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
				        unused = scan.nextLine();
						break;
				}
				break;
			case 5:
				Reports.mostCancellations();
				System.out.println("\n\nThank you for using reports. Press enter to go back to the main page.");
		        unused = scan.nextLine();
				break;
			case 6:
				System.out.println("Returning to main page.");
			default:
				System.out.println("Error, Wrong Choice!");
				break;
		}
	}
	
}
