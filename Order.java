package bighw;
import java.time.LocalDateTime;
import java.util.*;

public class Order<T> {
	private static int idGenerator=0;//订单编号生成器，每次生成订单时自动递增
	private final int id;//订单编号
	private int customerId;//顾客编号
	private final String name;
	private final String phone;//订餐人电话
	private static List<DishList> dishList;//菜品列表
	private double totalPrice;//合计价格
	private final LocalDateTime orderTime;//下单时间
	private LocalDateTime receiveTime;//确认收货时间
	private boolean isConfirmed;//是否确认收货
	private boolean Fulfilled;
	private List<Order> orderList=new LinkedList<>();
	DishList DishId;
	DishList DishName;
	DishList DishPrice;
	DishList DishQuantity;
	
	public Order(String name, String phone, DishList selectedDish, int quantity) {
        this.id = idGenerator++;
        this.name = name;
        this.phone = phone;
        this.dishList = new LinkedList<>(); // Initialize the dish list
        this.dishList.add(selectedDish); // Add the selected dish to the list
        this.orderTime = LocalDateTime.now();
        this.totalPrice = selectedDish.getDishPrice() * quantity;
        this.isConfirmed = false;
        this.Fulfilled = false;
    }

    // Add a new constructor overload to accept a list of dishes
    public Order(String name, String phone, List<DishList> dishList) {
        this.id = idGenerator++;
        this.name = name;
        this.phone = phone;
        this.dishList = dishList;
        this.orderTime = LocalDateTime.now();
        this.totalPrice = 0;
        for (DishList dish : dishList) {
            this.totalPrice += dish.getDishPrice() * dish.getDishQuantity();
        }
        this.isConfirmed = false;
        this.Fulfilled = false;
    }

	public boolean getisConfirm() {
		return isConfirmed;
	}

	//确认收货
	public void confirmReceive() {
		this.receiveTime=LocalDateTime.now();	
		this.isConfirmed=true;
	}
	
	//取消订单，将菜品数量还原
	public void cancelOrder() {
		for(DishList dish:dishList) {
			((DishList) dish).setDishQuantity(0);
		}
	}
	
	//获取订单编号
	public int getId(){
		return id;
	}
	
	//获取顾客编号
	public int getCustomerId() {
		return customerId;
	}
	
	//设置顾客编号
	public void setCustomerId(int customerId) {
		this.customerId=customerId;
	}
	
	//获取订餐人姓名
	public String getName() {
		return name;
	}
	
	//获取订餐人电话
	public String getPhone() {
		return phone;
	}
	
	//获取菜品列表
	public List<DishList> getDishList() {
		return dishList;
	}
	
	//获取合计价格
	public double getTotalPrice() {
		return totalPrice;
	}
	
	//获取下单时间
	public LocalDateTime getOrderTime() {
		return orderTime;
	}
	
	//获取确认收货时间
	public LocalDateTime getReceiveTime() {
		return receiveTime;
	}
	
	//添加订单到订单列表中
	public void add(Order order) {
		orderList.add(order);
	}
	
	public void modifyDishQuantity(String dishName, int quantity) {
        for (DishList food : dishList) {
            if (food.getDishName().equals(dishName)) {
                food.setDishQuantity(quantity);
                break;
            }
        }
        // 重新计算总价
        double total = 0.0;
        for (DishList food : dishList) {
            total += food.getDishPrice() * food.getDishQuantity();
        }
        this.totalPrice = total;
    }
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append("订单编号：").append(id).append("\n");
	    sb.append("顾客编号：").append(customerId).append("\n");
	    sb.append("订餐人姓名：").append(name).append("\n");
	    sb.append("订餐人电话：").append(phone).append("\n");
	    sb.append("下单时间：").append(orderTime).append("\n");
	    sb.append("菜品列表：\n");
	    for (DishList dish : dishList) {
	        sb.append("\t").append(dish.getDishName()).append(" × ").append(dish.getDishQuantity());
	        sb.append("，单价 ").append(dish.getDishPrice()).append(" 元，小计：");
	        sb.append(dish.getDishPrice() * dish.getDishQuantity()).append(" 元\n");
	    }
	    sb.append("合计：").append(totalPrice).append(" 元\n");
	    if (receiveTime != null) {
	        sb.append("确认收货时间：").append(receiveTime).append("\n");
	    }
	    return sb.toString();
    }

	//打印订单信息
	public void printOrder() {
		System.out.println("订单编号："+id);
		System.out.println("顾客编号"+customerId);
		System.out.println("订餐人姓名："+name);
		System.out.println("订餐人电话："+phone);
		System.out.println("下单时间："+orderTime);
		System.out.println("菜品列表：");
		for(DishList dish:dishList) {
			System.out.println("\t"+((DishList) dish).getDishName()+"×"+((DishList) dish).getDishQuantity()+"，单价"+((DishList) dish).getDishPrice()+"元，小计："+((DishList) dish).getDishPrice()*((DishList) dish).getDishQuantity()+"元");
		}
		System.out.println("合计："+totalPrice+"元");
		if(receiveTime!=null) {
			System.out.println("确认收货时间：");
		}
	}
	public void setFulfilled(boolean b) {
		Fulfilled=b;
		
	}
}
