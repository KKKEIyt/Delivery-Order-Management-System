package bighw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static Customer customerList;
	private static Admin yu;
	public static void main(String[] args) {
		//初始化管理员账号和密码
		yu=new Admin("yu","admin");
		//初始化顾客列表
		customerList=new Customer();
		Scanner scanner=new Scanner(System.in);
		boolean exit=false;
		while(!exit) {
			//显示菜单
			System.out.println("请选择功能：");
			System.out.println("1.餐厅管理员登陆");
			System.out.println("2.顾客注册");
			System.out.println("3.顾客登陆");
			System.out.println("4.退出");
			int choice=scanner.nextInt();
			switch(choice) {
			case 1:
				//餐厅管理员登陆
				System.out.println("请输入用户名：");
				String adminUsername=scanner.next();
				System.out.println("请输入密码：");
				String adminPassword=scanner.next();
				if(yu.login(adminUsername,adminPassword)) {
					System.out.println("登陆成功！");
					//进入管理员操作界面
					adminMenu();
				}else {
					System.out.println("用户名或密码错误！");
				}
				break;
			case 2:
				//顾客注册
				Customer customer=new Customer();
				System.out.println("请输入用户名：");
				String name=scanner.next();
				customer.setUsername(name);
				System.out.println("请输入密码：");
				String password=scanner.next();
				customer.setPassword(password);
				Customer.addCustomer(customer);
				System.out.println("注册成功！");
				break;
			case 3:
				//顾客登陆
				System.out.println("请输入用户名：");
				String customerUsername=scanner.next();
				System.out.println("请输入密码：");
				String customerPassword=scanner.next();
				Customer customerLogin=customerList.getCustomerByUsernameAndPassword(customerUsername, customerPassword);
				if(customerLogin!=null) {
					System.out.println("登陆成功！");
					//进入顾客操作界面
					customerMenu(customerLogin);
				}else {
					System.out.println("用户名或密码错误！");
				}
				break;
			case 4:
				//退出
				exit=true;
				break;
			default:
				System.out.println("输入有误，请重新选择！");
				break;
			}
		}
	}
	private static void adminMenu() {
		Scanner scanner=new Scanner(System.in);
		boolean exit=false;
		while(!exit) {
			//显示管理员操作菜单
			System.out.println("管理员操作菜单：");
			System.out.println("1.添加菜品");
			System.out.println("2.修改菜品");
			System.out.println("3.删除菜品");
			System.out.println("4.按菜品名称查询");
			System.out.println("5.按价格范围查询");
			System.out.println("6.按价格升序排序");
			System.out.println("7.按价格降序排序");
			System.out.println("8.查看订单");
			System.out.println("9.查看菜品评价");
			System.out.println("10.返回上级菜单");
			int choice=scanner.nextInt();
			switch(choice) {
			case 1:
				//添加菜品
				System.out.println("请输入菜品信息：");
				scanner.nextLine();
				String dishInfo=scanner.nextLine();
				yu.addDish(dishInfo);
				break;
			case 2:
				//修改菜品
				System.out.println("请输入要修改的菜品编号：");
				int dishId=scanner.nextInt();
				System.out.println("请输入你要修改的菜品名称：");
				String dishName=scanner.nextLine();
				scanner.nextLine();  // 消耗换行符
				System.out.println("请输入你要修改的菜品价格：");
				double dishPrice=scanner.nextDouble();
				scanner.nextLine();  // 消耗换行符
				System.out.println("请输入你要修改的菜品数量：");
				int dishQuantity=scanner.nextInt();
				scanner.nextLine();  // 消耗换行符
				yu.modifyDish(dishId,dishName,dishPrice,dishQuantity);
				break;
			case 3:
				//删除菜品
				System.out.println("请输入要删除的菜品编号：");
				int dishIdToDelete=scanner.nextInt();
				yu.deleteDish(dishIdToDelete);
				break;
			case 4:
				//按菜品名称查询
				System.out.println("请输入菜品名称关键字：");
				String keyword=scanner.nextLine();
				scanner.nextLine();  // 消耗换行符
				yu.queryDishByName(keyword);
				break;
			case 5:
				//按价格范围查询
				System.out.println("请输入价格范围（最低价格和最高价格）：");
				double minPrice=scanner.nextDouble();
				double maxPrice=scanner.nextDouble();
				yu.queryDishByPrice(minPrice, maxPrice);
				break;
			case 6:
				//按价格升序排序
				yu.displayDishByPrice(true);
				break;
			case 7:
				//按价格降序排序
				yu.displayDishByPrice(false);
				break;
			case 8:
				//查看订单
				yu.viewAllOrders();
				break;
			case 9:
				//查看菜品评价
				yu.displayDishReviews();
				break;
			case 10:
				//返回上级菜单
				exit=true;
				break;
			default:
				System.out.println("输入有误，请重新选择！");
				break;
			}
		}
	}
	private static void customerMenu(Customer customerLogin) {
		Scanner scanner=new Scanner(System.in);
		boolean exit=false;
		while(!exit) {
			//显示顾客操作菜单
			System.out.println("顾客操作菜单：");
			System.out.println("1. 查看菜品信息");
            System.out.println("2. 按菜品名称查询");
            System.out.println("3. 按价格范围查询");
            System.out.println("4. 按价格升序排序");
            System.out.println("5. 按价格降序排序");
            System.out.println("6. 下单");
            System.out.println("7. 查看订单");
            System.out.println("8. 取消订单");
            System.out.println("9. 确认收货");
            System.out.println("10. 对订单菜品进行评价");
            System.out.println("11. 返回上级菜单");
            int choice=scanner.nextInt();
            switch(choice) {
            case 1:
            	//查看菜品信息
            	customerList.displayAllDishes();
            	break;
            case 2:
            	//按菜品名称查询
            	System.out.println("请输入菜品名称关键词：");
            	String keyword=scanner.nextLine();
            	customerList.queryDishByName(keyword);
            	break;
            case 3:
            	//按价格范围查询
            	System.out.println("请输入价格范围（最低价格和最高价格）：");
            	double minPrice=scanner.nextDouble();
            	double maxPrice=scanner.nextDouble();
            	customerList.queryDishByPrice(minPrice, maxPrice);
            	break;
            case 4:
            	//按价格升序排序
            	customerList.displayDishByPrice(true);
            	break;
            case 5:
            	//按价格降序排序
            	customerList.displayDishByPrice(false);
            	break;
            case 6:
            	//下单
            	System.out.println("请输入订餐人姓名：");
                String name = scanner.nextLine();
                scanner.nextLine();  // 消耗换行符
                System.out.println("请输入订餐人电话：");
                String phone = scanner.nextLine();
                System.out.println("请输入要下单的菜品编号：");
                int dishId = scanner.nextInt();
                scanner.nextLine();  // 消耗换行符
                System.out.println("请输入要下单的数量：");
                int quantity = scanner.nextInt();                
                List<Order> orderList = new ArrayList<>(); // 创建订单列表对象
                yu.createOrder(name, phone, customerList.getOrderList(), dishId, quantity);                
                break;
            case 7:
            	//查看订单
                if (customerList.getOrderList().isEmpty()) {
                    System.out.println("订单列表为空");
                } else {
                    System.out.println("订单列表如下：");
                    for (Object order : customerList.getOrderList()) {
                        System.out.println(order.toString());
                    }
                }
                break;
            case 8:
            	//取消订单
            	System.out.println("请输入要删除的订单编号：");
            	int dishID=scanner.nextInt();
            	customerList.deleteOrder(dishID);
            	break;
            case 9:
            	//确认收货
            	System.out.println("请输入要进行收货的订单编号：");
            	int receiptOrderId=scanner.nextInt();
            	customerList.confirmReceipt(receiptOrderId);
            	break;
            case 10:
            	//对订单菜品进行评价
            	System.out.println("请输入订单编号：");
            	int reviewOrderId=scanner.nextInt();
            	customerList.evaluateDishes(reviewOrderId);
                break;
            case 11:
                //返回上级菜单
                exit = true;
                break;
            default:
                System.out.println("输入有误，请重新选择！");
                break;
            }
		}
	}
}
