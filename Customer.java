package bighw;
import java.time.LocalDateTime;
import java.util.*;

public class Customer<T>{
	private String name;//顾客姓名
	private String password;//顾客密码
	private String phone;//顾客电话
	private static List<DishList> dishes;//菜品列表
	private List<Order> orderList;//订单列表
	static List<Customer> customerList=new ArrayList<>();
	DishList DishName;
	DishList DishQuantity;
	
	//构造函数
	public Customer() {
		this.dishes=new ArrayList<>();
		this.orderList=new ArrayList<>();
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
	public List<DishList> getDishList(){
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
		DishList dishList = new DishList();  // 创建菜品列表对象
		List<DishList> dishes = dishList.getDishes();  // 获取菜品列表
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
	public void createOrder(String name,String phone,List<Order> orderList) {
	Order order=new Order(name,phone,dishes,DishName,DishQuantity);
	order.setCustomerId(this.hashCode()); // 设置顾客的唯一标识符
	orderList.add(order);
	System.out.println("创建订单成功！");
	order.printOrder();
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
	public void cunfirmReceipt(Order order) {
		order.confirmReceive();
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
	    
}
