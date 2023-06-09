package bighw;
import java.time.LocalDateTime;
import java.util.*;

public class Customer<T>{
	private String name;//顾客姓名
	private String password;//顾客密码
	private String phone;//顾客电话
	private List<DishList> dishes;//菜品链表
	private List<Order> orderList;//订单链表
	static List<Customer> customerList=new ArrayList<>();
	DishList DishName;
	DishList DishQuantity;
	
	//构造函数
	public Customer() {
		this.dishes=new LinkedList<>();
		this.orderList=new LinkedList<>();
	}
	
	//设置顾客姓名
	public void setUsername(String name) {
		this.name=name;
	}
	
	//设置顾客密码
	public void setPassword(String password) {
		this.password=password;
	}
	
	//设置顾客电话
	public void setUserphone(String phone) {
		this.phone=phone;
	}
	
	//获取顾客姓名
	public String getName() {
		return name;
	}
	
	//获取顾客电话
	public String getPhone() {
		return phone;
	}
	
	//获取菜品列表
	public List<DishList> getDishes(){
		return dishes;
	}
	
	//获取订单列表
	public List<Order> getOrderList(){
		return orderList;
	}
	
	//获取顾客密码
	public String getPassword() {
		return password;
	}
	
	public static Customer getCustomerByUsernameAndPassword(String username,String password) {
		for(Customer customer:customerList) {
			if(customer.getName().equals(username)&&customer.getPassword().equals(password)){
				return customer;
			}
		}
		return null;
	}
	
	//按菜品名称模糊查询
	public List<DishList> queryDishByName(String DishName) {
		List<DishList> result=new ArrayList<DishList>();
		for(DishList dish:dishes) {
			if(dish.getDishName().contains(DishName)) {
				result.add(dish);
			}
		}
		System.out.println("查询结果如下：");
		if(result.isEmpty()) {
			System.out.println("查询结果为空");
		} else {
			for(DishList dish:result) {
				System.out.println(dish.toString());
			}
		}
		return result;
	}
		
	//按价格范围查询
	public List<DishList> queryDishByPrice(double minPrice,double maxPrice){
		List<DishList> result=new ArrayList<DishList>();
		for(DishList dish:dishes) {
			if(dish.getDishPrice()>=minPrice&&dish.getDishPrice()<=maxPrice) {
				result.add(dish);
			}
		}
		System.out.println("查询结果如下：");
		if(result.isEmpty()) {
			System.out.println("查询结果为空");
		} else {
			for(DishList dish:result) {
				System.out.println(dish.toString());
			}
		}
		return result;
	}
		
	//按价格的升序或降序排列显示
	public void displayDishByPrice(boolean ascending) {
		List<DishList> sortedDishes=new ArrayList<DishList>(dishes);
		Collections.sort(sortedDishes,new Comparator<DishList>() {
			public int compare(DishList dish1,DishList dish2) {
				return Double.compare(dish1.getDishPrice(),dish2.getDishPrice());
			}
		});
		if(!ascending) {
			Collections.reverse(sortedDishes);
		}
		System.out.println("按价格排序后结果如下：");
		for(DishList dish:sortedDishes) {
			System.out.println(dish);
		}
	}
		
	//显示所有菜品信息
	public void displayAllDishes() {
		List<DishList> dishes = DishList.getDishes();  // 获取菜品列表
		System.out.println("菜单信息如下：");
		for(DishList dish:dishes) {
			System.out.println("菜品编号：" + dish.getDishId());
	        System.out.println("菜品名称：" + dish.getDishName());
	        System.out.println("菜品价格：" + dish.getDishPrice());
	        System.out.println("菜品数量：" + dish.getDishQuantity());
	        System.out.println("---------------------------");
		}
	}
		
	//添加菜品到菜品列表
	public void cusAddDish(DishList dish) {
		dishes.add(dish);
	}
	
	//从菜品列表中删除菜品
	public void cusRemoveDish(DishList dish) {
		dishes.remove(dish);
	}
	
	//创建订单
	public void createOrder(String name, String phone, List<Order> orderList, int dishId, int quantity) {
	    List<DishList> dishes = getDishes(); // Get the dish list from the customer
	    DishList selectedDish = null;
	    
	    // Find the selected dish by dishId
	    for (DishList dish : dishes) {
	        if (dish.getDishId() == dishId) {
	            selectedDish = dish;
	            break;
	        }
	    }
	    
	    if (selectedDish != null) {
	        Order order = new Order(name, phone, selectedDish, quantity);
	        order.setCustomerId(this.hashCode()); // Set the customer's unique identifier
	        orderList.add(order);
	        System.out.println("创建订单成功！");
	        order.printOrder();
	    } else {
	        System.out.println("菜品编号无效！");
	    }
	}
	
	// 添加订单到订单列表中
	public void add(Order order) {
		if(order!=null) {
        orderList.add(order);
		}
    }
	
	//修改订单
	public void modifyOrder(int id,String name,int quantity) {
	    Boolean bool = false; 
	    this.orderList.forEach(x->{
	        if(x.getId()==id) {
	            List<DishList> dishList=x.getDishList();
	            int count =0;
	            for(int i =0;i<dishList.size();i++) {
	            	if(dishList.get(i).getDishName().equals(name)) {
	            		dishList.get(i).setDishQuantity(quantity);
	            		count++;
	            	}
	            }
	            if(count>0) {
	    	        System.out.println("修改订单成功！");
	    	    } else {
	    	        System.out.println("修改订单失败！");
	    	    } 
	        }
	       x.printOrder();            
	    });   
	}
	
	// 删除订单
	public void deleteOrder(int id) {
		Iterator<Order> it = orderList.iterator();
	    while (it.hasNext()) {
	        Order order = it.next();
	        if (order.getId() == id) {
	            it.remove();
	            System.out.println("删除订单成功！");
	            order.printOrder();
	            return;
	        }
	    }
	    System.out.println("删除订单失败，找不到指定的订单！");
	}
	
	public boolean isOrderDeleted(int orderId) {
	    for (Order order : orderList) {
	        if (order.getId() == orderId) {
	            return false;
	        }
	    }
	    return true;
	}
	
	//确认收货
	public void confirmReceipt(int receiptOrderId) {
	    Order order = null;
	    // Find the order with the specified ID
	    for (Order o : orderList) {
	        if (o.getId() == receiptOrderId) {
	            order = o;
	            break;
	        }
	    }

	    if (order != null) {
	        order.confirmReceive();
	        System.out.println("订单确认收货成功！");
	    } else {
	        System.out.println("订单编号无效！");
	    }
	}

	// 打印顾客信息
	public void printCustomerInfo() {
	    System.out.println("顾客姓名：" + name);
	    System.out.println("顾客电话：" + phone);
	    System.out.println("菜品列表：");
	    for (DishList dish : dishes) {
	        System.out.println(dish.getDishName() + " " + dish.getDishPrice());
	    }
	    System.out.println("订单列表：");
	    for (Order order : orderList) {
	        order.printOrder();
	    }
	}

	public static void addCustomer(Customer customer) {
		customerList.add(customer);
	}
	
	
	//进行菜品评价
	public void evaluateDishes(int reviewOrderId) {
	    Order order = null;
	    // Find the order with the specified ID
	    for (Order o : orderList) {
	        if (o.getId() == reviewOrderId) {
	            order = o;
	            break;
	        }
	    }

	    if (order != null) {
	        // Display the ordered dishes for evaluation
	    	Scanner scanner=new Scanner(System.in);
	        List<DishList> dishList = order.getDishList();
	        System.out.println("订单编号：" + order.getId());
	        System.out.println("订单菜品评价：");
	        for (DishList dish : dishList) {
	            System.out.println("菜品名称：" + dish.getDishName());
	            System.out.println("菜品评分（1-5）：");
	            int rating = scanner.nextInt();
	            dish.setRating(rating);
	        }
	        System.out.println("订单菜品评价成功！");
	    } else {
	        System.out.println("订单编号无效！");
	    }
	}
	    
}
