

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.*;  

public class Product {
	private static List<Item> items;
    public static void main(String... args) {
        items = readBooksFromCSV("products.csv");
        // let's print all the person read from CSV file
        Scanner sc= new Scanner(System.in);
        //int opt;
        while(true)
        {
        	System.out.println("1. list of all the products across all the categories");
        	System.out.println("2. list of all the products by category or of a price less than or greater than a specified price for that product");
        	System.out.println("3. list of all the products by company or of a price less than or greater than a specified price for that company");
        	System.out.println("4. discounted price of all the products by category");
        	System.out.println("5. place a dummy order");
        	System.out.println("6. get the available stock details across products.");
        	System.out.println("7. delete the dummy order(s)");
        	System.out.println("10. Exit");
        	System.out.println("Enter Options");
        	int cmd= sc.nextInt();
        	
        	switch(cmd) {
        	case 1: 
        		productListByCategory(items);
        		break;
        	case 2:
        		System.out.println("Enter Price");
        		int price = sc.nextInt();
        		productbycategoryPricelessgreater(items,price);
        		break;
        	case 3:
        		System.out.println("Enter Price");
        		int price1 = sc.nextInt();
        		productbycompanyPricelessgreater(items,price1);
        		break;
        	case 4:
        		discountedPricebyCategory(items);
        		break;
        	case 5:
        		System.out.println("-Place a dummy Order-");
        		System.out.println("Enter Category");
        		sc.nextLine();
        		String category = sc.nextLine(); 
        		
        		System.out.println("Enter Product");
        		String Product = sc.nextLine();
        		
        		System.out.println("Enter Quantity");
        		int quantity = sc.nextInt();
        		SubmitDummyOrder(category,Product,quantity);
        		break;        		  	
        	case 6:
        		System.out.print("Enter ProductID for which you want to check available stock");
        		sc.nextLine();
        		String ProductId = sc.nextLine();
        		PrintavailableStock(ProductId);
        		break;
        	case 7:
        		System.out.print("Enter ProductID for which you want to cancle dummy Order");
        		sc.nextLine();
        		String ProductId1 = sc.nextLine();
        		System.out.println("Enter the Quantity to cancle dummy order");
        		int qty = sc.nextInt();
        		DeleteDummyOrder(ProductId1,qty);
        		break;
        	case 10:
        		return;
        	default:
        		break;
        	}
        }
    }
    
    private static void DeleteDummyOrder(String productId1, int qty) {
		// TODO Auto-generated method stub
    	for (Item b : items) {
            if (productId1.equals(b.getProduct()))
            {
            	b.setNoOfItemsInStock(b.getNoOfItemsInStock()+qty);
            	System.out.println("Dummy Order deleted avaialble Stock increased");
            }
    	}
		
	}
    
	private static void PrintavailableStock(String productId) {
		// TODO Auto-generated method stub
    	for (Item b : items) {
    		if (productId.equals(b.getProduct()))
    		{
    			System.out.println("Available Stock for Product "+productId+" is :"+b.getNoOfItemsInStock());
    		}
    	}
		
	}
	private static void SubmitDummyOrder(String category, String product, int quantity) {
		// TODO Auto-generated method stub
    	for (Item b : items) {
            if (product.equals(b.getProduct()))
            {
            	if (b.getNoOfItemsInStock()-quantity >= 0)
            	{	b.setNoOfItemsInStock(b.getNoOfItemsInStock()-quantity);
            		System.out.println("Dummy Order Placed");
            	}
            	else 
            		System.out.println("Can't place dummy Order");
            }
        }
    	
		
	}
	//1. I want to get the list of all the products across all the categories
    private static void productListByCategory(List<Item> items)
    {
    	System.out.println("1. list of all the products across all the categories:");
    	
    	for (Item b : items) {
            System.out.println(b.getCategory()+"-->"+b.getProduct());
        }
    }
    // 2. list of all the products by category or of a price less than or greater than a specified price for that product
    private static void productbycategoryPricelessgreater(List<Item> items, int specifiedPrice) 
    {
    	System.out.println("2. Products by Category");
    	System.out.println("Price Greater Than Specified Price:"+specifiedPrice);
    	for (Item b : items) {
            
    		if (b.getPrice() >= specifiedPrice)
    		{
    			System.out.println(b.getCategory()+"-->"+b.getProduct()+"; Price: "+b.getPrice() );
    		}
    	}
    	System.out.println("Price Less Than Specified Price:"+specifiedPrice);
    	for (Item b : items) {
    		if (b.getPrice() < specifiedPrice)
    		{ 		
    			System.out.println(b.getCategory()+"-->"+b.getProduct()+"; Price: "+b.getPrice() );
    		}
    	}
    }
    // 3. list of all the products by company or of a price less than or greater than a specified price for that company
    private static void productbycompanyPricelessgreater(List<Item> items, int specifiedPrice) 
    {
    	System.out.println("3. Products by Company");
    	System.out.println("Price Greater Than Specified Price:"+specifiedPrice);
    	for (Item b : items) {
            
    		if (b.getPrice() >= specifiedPrice)
    		{
    			System.out.println(b.getCompany()+"-->"+b.getProduct()+"; Price: "+b.getPrice() );
    		}
    	}
    	System.out.println("Price Less Than Specified Price:"+specifiedPrice);
    	for (Item b : items) {
    		if (b.getPrice() < specifiedPrice)
    		{ 		
    			System.out.println(b.getCompany()+"-->"+b.getProduct()+"; Price: "+b.getPrice() );
    		}
    	}
    }
    // 4. discounted price of all the products by category
    private static void discountedPricebyCategory(List<Item> items) 
    {
    	System.out.println("4. Discounted Price by Category");
    	for (Item b : items) {
    		System.out.println(b.getCategory() + "-->" + b.getProduct() + " Discounted Price: "+ (b.getPrice()-(b.getPrice()*b.getDiscount()/100)));
    	}
    }
    
    
    
    
    
    private static List<Item> readBooksFromCSV(String fileName)
    {
        List<Item> items = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        int skip_line = 0;
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII))
        { // read the first line from the text file
            String line = br.readLine();
            // loop until all lines are read
            
            while (line != null) {
                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                if (skip_line == 0)
                {
                	skip_line++;
                	line = br.readLine();
                	continue;
                }
                String[] attributes = line.split(",");
                Item item = createItem(attributes);
                // adding book into ArrayList
                items.add(item);
                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        return items;
    }

    private static Item createItem(String[] metadata)
    {
        
        String category = metadata[0];
        String company = metadata[1];
        String product = metadata[2];
        String color = metadata[3];
        String description = metadata[4];
        int price = Integer.parseInt(metadata[5]);
        int discount = Integer.parseInt(metadata[6]);
        int noOfItemsInStock = Integer.parseInt(metadata[7]);
        // create and return book of this metadata
        return new Item(category, company, product, color, description, price, discount, noOfItemsInStock);
    }
    
    
    
    
}

class Item {
    
    private String category;
    private String company;
    private String product;
    private String color;
    private String description;
    private int price;
    private int discount;
    private int noOfItemsInStock;
    public Item(String category, String company , String product,String color,String description, int price,int discount,int noOfItemsInStock ) {
        
        this.category = category;
        this.company = company;
        this.product = product;
        this.color = color;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.noOfItemsInStock = noOfItemsInStock;
        
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
     public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
     public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
     public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    } 
    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
    } 
    public int getNoOfItemsInStock() {
        return noOfItemsInStock;
    }
    public void setNoOfItemsInStock(int noOfItemsInStock) {
        this.noOfItemsInStock = noOfItemsInStock;
    }
    
    @Override
    public String toString() {
        return "Data [category=" + category + ", company=" + company + ", product=" + product + ",color=" + color + ",description=" + description + ",price=" + price + ",discount=" + discount + ",noOfItemsInStock= " + noOfItemsInStock +"]";
        

    }
}