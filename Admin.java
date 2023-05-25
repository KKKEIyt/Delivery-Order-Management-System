package bighw;
import java.util.*;

public class Admin<T> {
	private String username;
	private String password;
	private static List<DishList> dishes;//存储菜品信息的链表
	private static List<Order> orderList;
	Scanner scanner=new Scanner(System.in);
	public Admin(String username,String password) {
		this.username=username;
		this.password=password;
		this.dishes=new LinkedList<>();
	}
	
	public void setOrderList(List<Order> orderList) {
		this.orderList=orderList;
	}
	
	//添加菜品信息
	public void addDish(String dishInfo) {
	    String[] dishInfoArr = dishInfo.split(",");
	    int dishId = Integer.parseInt(dishInfoArr[0].trim());
	    String dishName = dishInfoArr[1].trim();
	    double dishPrice = Double.parseDouble(dishInfoArr[2].trim());
	    int dishQuantity = Integer.parseInt(dishInfoArr[3].trim());
	    String dishDescription = dishInfoArr[4].trim();

	    DishList dish = new DishList(dishId, dishName, dishPrice, dishQuantity, new ArrayList<>());
	    dish.getDishDescription().add(dishDescription);
	    dishes.add(dish);

	    System.out.println("菜品添加成功！");
	    System.out.println(dish.toString());
	}
	
	//修改菜品信息
	public void modifyDish(int DishId,String DishName,double DishPrice,int DishQuantity){ 
		for(DishList dish:dishes) {
			if(dish.getDishId()==DishId) {
				dish.setDishName(DishName);
				dish.setDishPrice(DishPrice);
				dish.setDishQuantity(DishQuantity);
				System.out.println("菜品修改成功！");
				System.out.println(dish.toString());
			}
		}
		System.out.println("菜品不存在，无法修改！");
	}
	
	//删除菜品信息
	public void deleteDish(int DishId) {
		Iterator<DishList> it=dishes.iterator();
		while(it.hasNext()){
			DishList dish=it.next();
			if(dish.getDishId()==DishId) {
				it.remove();
				System.out.println("菜品删除成功！");
				System.out.println(dishes.toString());
				return;
			}
		}
		System.out.println("菜品不存在，无法删除！");
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
		System.out.println("菜单信息如下：");
		for(DishList dish:dishes) {
			System.out.println(dish);
		}
	}
	
	public void addOrder(Order order) {
		if (order != null) {
	        orderList.add(order);
	    }
	}
	
	//查看所有订单
	public void viewAllOrders() {
		if (orderList==null||orderList.isEmpty() ) {
	        System.out.println("没有订单信息");
	        return;
	    }
	    System.out.println("所有订单信息如下：");
	    for (Order order : orderList) {
	        if (order != null) {
	            System.out.println(order.toString());
	        }
	    }
	}
	
	//按下单的时间顺序查看没有出单的订单
	public void viewUnfulfilledOrders() {
	    List<Order> unfulfilledOrders = new ArrayList<>();
	    for (Order order : orderList) {
	        if (order.getisConfirm()==false) {
	            unfulfilledOrders.add(order);
	        }
	    }
	    Collections.sort(unfulfilledOrders, new Comparator<Order>() {
	        public int compare(Order order1, Order order2) {
	            return order1.getOrderTime().compareTo(order2.getOrderTime());
	        }
	    });
	    System.out.println("未出单订单列表如下：");
	    if (unfulfilledOrders.isEmpty()) {
	        System.out.println("暂无未出单订单");
	    } else {
	        for (Order order : unfulfilledOrders) {
	            System.out.println(order.toString());
	        }
	    }
	}
	
	//对订单进行出单操作
	public void fulfillOrder(int orderId) {
	    for (Order order : orderList) {
	        if (order.getId() == orderId) {
	            order.setFulfilled(true);
	            System.out.println("订单 " + orderId + " 已出单");
	            return;
	        }
	    }
	    System.out.println("订单 " + orderId + " 不存在");
	}

	public boolean login(String adminUsername, String adminPassword) {
		if(this.username.equals(adminUsername)&&this.password.equals(adminPassword)){
			return true;
		}
		return false;
	}

	//查看菜品的评价
	public void displayDishReviews() {
	    System.out.println("请输入菜品编号：");
	    int dishId = scanner.nextInt();

	    DishList dish = findDishById(dishId);

	    if (dish != null) {
	        int rating = dish.getRating(); // Assuming you have a method to retrieve the rating for the dish

	        if (rating == 0) {
	            System.out.println("该菜品暂无评价！");
	        } else {
	            System.out.println("菜品评价：");
	            System.out.println("评分：" + rating);
	        }
	    } else {
	        System.out.println("未找到该菜品！");
	    }
	}
	
	//通过菜品ID返回菜品
	private static DishList findDishById(int dishId) {
		for(DishList dish:dishes) {
			if(dish.getDishId()==dishId) {
				return dish;
			}
		}
		return null;
	}
	
	public List<DishList> getDishList() {
        return dishes;
    }

	
	public void createOrder(String name, String phone, List<Order> orderList, int dishId, int quantity) {
	    List<DishList> dishes = getDishList(); // Get the dish list from the customer
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
}
