package databasetools.util;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import databasetools.ui.frame.HomePageWindow;


public class StringUtil_1920x1080 {

	private static JTable tabDemo;
	@SuppressWarnings("unused")
	private static JTableHeader jth;
	static Connection con = null;
	static Statement statement = null;
	static ResultSet res = null;
	
	static String Fdriver = "oracle.jdbc.driver.OracleDriver";
	static String Furl  = "jdbc:oracle:thin:@IDCDB70-DG0.dafycredit.com:1521:idcdb011";
	static String Fname = "dafy_user";
	static String Fpasswd = "Dfuser$20150701";
	
	static String Tdriver = "oracle.jdbc.driver.OracleDriver";
	static String Turl  = "jdbc:oracle:thin:@idcdbtest.dafycredit.com:1521:dbtest01";
	static String Tname = "dafy_sales";
	static String Tpasswd = "Ju$2017";
	
	static String Ddriver = "oracle.jdbc.driver.OracleDriver";
	static String Durl  = "jdbc:oracle:thin:@devdb01.dafycredit.com:1521:devdb01";
	static String Dname = "dafy_sales";
	static String Dpasswd = "Ju$2017";
	
	/** 主页窗口 */
	private static HomePageWindow HomePage;
	
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}
	
	public static boolean isEqual(String str1, String str2) {
		if (str1 == str2 || str1.equals(str2)) {
			return true;
		}
		return false;
	}
	
	/** 主页窗口判断 */
	public static HomePageWindow getHomePage() {
		return HomePage != null ? HomePage : null;
	}

	//根据数据版本连接对应数据库
	public static boolean logincompare(String DataVersion, String username){
	    boolean m = false;
	    String sql = "select id from dafy_sales.sys_user_list where id="+ username +"";
	    try{
	    	if("正式版本".equals(DataVersion)){
	    	    Class.forName(Fdriver);
	    	    con = DriverManager.getConnection(Furl, Fname, Fpasswd); 
	    	    statement = con.createStatement();
	    	}
	    	if("测试版本".equals(DataVersion)){
		    	Class.forName(Tdriver); 
		    	con = DriverManager.getConnection(Turl, Tname, Tpasswd); 
		    	statement = con.createStatement();
		    }
	    	if("开发版本".equals(DataVersion)){
		    	Class.forName(Ddriver);
		    	con = DriverManager.getConnection(Durl, Dname, Dpasswd); 
		    	statement = con.createStatement();
		    }
	        ResultSet res = statement.executeQuery(sql);
	        if(res.next()){
	            String pa = res.getString(1);
	            System.out.println(pa + " " + username);
	            if(pa.equals(username)){
	                m = true;
	            }else {
	                   JOptionPane.showMessageDialog(null, "密码错误！");
	            }
	        }else {
	               JOptionPane.showMessageDialog(null, "用户名不存在！");
	        }
	        res.close();
	        con.close();
	        statement.close();    
	    }catch(SQLException | ClassNotFoundException e){
	        e.printStackTrace();
	    }   
	    return m;
	}

	//对比用户名和密码是不匹配
	public static boolean logincompare1(String username, String password){
	    boolean m = false;
	    String sql = "select PASSWORD from dafy_sales.sys_user_list where id="+ username +"";
	    try{
	    	Class.forName(Tdriver);
	    	con = DriverManager.getConnection(Turl, Tname, Tpasswd); 
	    	statement = con.createStatement();
	        ResultSet res = statement.executeQuery(sql);
	        if(res.next()){
	            String pa = res.getString(1);
	            System.out.println(pa + " " + password);
	            if(pa.equals(password)){
	                m = true;
	            }else {
	                   JOptionPane.showMessageDialog(null, "密码错误！");
	            }
	        }else {
	               JOptionPane.showMessageDialog(null, "用户名不存在！");
	        }
	        res.close();
	        con.close();
	        statement.close();    
	    }catch(SQLException | ClassNotFoundException e){
	        e.printStackTrace();
	    }
	    return m;
	}
	
	//对比合同号是否匹配,判断合同是否存在
	public static boolean contractcompare(String DataVersion,String contract_no){
	    boolean m = false;
	    String sql = "select CONTRACT_NO from dafy_sales.cs_credit where contract_no="+ contract_no +"";
	    try{
	    	if("正式版本".equals(DataVersion)){
	    	    Class.forName(Fdriver);
	    	    con = DriverManager.getConnection(Furl, Fname, Fpasswd); 
	    	    statement = con.createStatement();
	    	}
	    	if("测试版本".equals(DataVersion)){
		    	Class.forName(Tdriver); 
		    	con = DriverManager.getConnection(Turl, Tname, Tpasswd); 
		    	statement = con.createStatement();
		    }
	    	if("开发版本".equals(DataVersion)){
		    	Class.forName(Ddriver);
		    	con = DriverManager.getConnection(Durl, Dname, Dpasswd); 
		    	statement = con.createStatement();
		    }
	         ResultSet res = statement.executeQuery(sql);
	         if(res.next()){
	              String pa = res.getString(1);
	              System.out.println(pa+" " +contract_no);
	            if(pa.equals(contract_no)){
	            m = true;
	            }else {
	                   JOptionPane.showMessageDialog(null, "合同号和数据库不匹配！");
	            }
	            }else {
	                   JOptionPane.showMessageDialog(null, "合同号不存在！");
	            }
	            res.close();
	            con.close();
	            statement.close();
	      }catch(SQLException | ClassNotFoundException e){
	            e.printStackTrace();
	      }
	      return m;
	}

	//查询对应合同的合同详情信息
	public static boolean btnShow_ActionPerformed(String DataVersion,String contract_no){
   
	 String sql = "select c.contract_no as 合同号,c.credit_model as 合同资金模式,f.credit_amount as f贷款金额,c.credit_amount as c贷款金额,g.goods_credit_amount as g贷款金额,c.init_pay c首付,g.goods_init_pay as g首付,c.price as c商品价格,g.goods_price as g商品价格,(c.credit_amount+c.init_pay) as s商品价格,c.deduct_date as c首次扣款日,e.fst_date_pay as e首次扣款日,(round((f.credit_amount*p.month_ratio_eir)/100,0))+(f.insurance_fee)+(f.power_fee)+(f.treasure_box_fee) as s每月总还款额,i.type_instalment as i本金1,i.value_instalment as i本金费用,f.principal as f第一期本金,ROUND((f.credit_amount*(p.month_ir/100)*power(1+(p.month_ir/100),0))/(power(1+(p.month_ir/100),p.payment_num)-1),3) as s第一期本金,i.type_instalment as i利息2,i.value_instalment as i利息费用,f.interest as f第一期利息,ROUND((f.credit_amount*(p.month_ir/100)*power(1+(p.month_ir/100),p.payment_num))/(power(1+(p.month_ir/100),p.payment_num)-1)-(f.credit_amount*(p.month_ir/100)*power(1+(p.month_ir/100),0))/(power(1+(p.month_ir/100),p.payment_num)-1),4) as s第一期利息,(ROUND((f.credit_amount*(p.month_ir/100)*power(1+(p.month_ir/100),0))/(power(1+(p.month_ir/100),p.payment_num)-1),3))+(ROUND((f.credit_amount*(p.month_ir/100)*power(1+(p.month_ir/100),p.payment_num))/(power(1+(p.month_ir/100),p.payment_num)-1)-(f.credit_amount*(p.month_ir/100)*power(1+(p.month_ir/100),0))/(power(1+(p.month_ir/100),p.payment_num)-1),3))as s每月应还本息,(round((f.credit_amount*p.month_ratio_eir)/100,0))-((ROUND((f.credit_amount*(p.month_ir/100)*power(1+(p.month_ir/100),0))/(power(1+(p.month_ir/100),p.payment_num)-1),3))+(ROUND((f.credit_amount*(p.month_ir/100)*power(1+(p.month_ir/100),p.payment_num))/(power(1+(p.month_ir/100),p.payment_num)-1)-(f.credit_amount*(p.month_ir/100)*power(1+(p.month_ir/100),0))/(power(1+(p.month_ir/100),p.payment_num)-1),3))) as s每月应付费用,f.cs_fee as f月服务费,(f.annuity - f.principal - f.interest - f.account_fee) as s月服务费,f.cs_fee_rate as f月服务费率,round(((f.annuity - f.principal - f.interest - f.account_fee) / f.credit_amount * 100),3) as s月服务费率,c.repay_style as c还款方式,f.annuity as f纯月还款额,c.annuity as c纯月还款额,round((f.credit_amount*p.month_ratio_eir)/100,0) as s纯月还款额,c.power_flag as c权益包购买状态,f.power_fee as f权益包费用,c.power_fee as c权益包费用,e.is_ssi as e是否购买保险,g.insurance_supplier as 保险类型,i.type_instalment as i保险70,i.value_instalment as i保险费,f.insurance_fee as f保险费,c.insurance_fee as c保险费,e.insurance_fee as e保险费,FLOOR(f.credit_amount*0.008) as s保险费,f.discount_amount as f每月优惠金额,round((f.credit_amount*p.month_ratio_bir)/100)-round((f.credit_amount*p.month_ratio_eir)/100) s每月优惠金额,f.lead_amount as f前置咨询费,f.month_ir as f月贷款利率,p.month_ir as p月贷款利率,f.account_fee_rate as f月咨询费用率,p.account_fee as p月咨询费率,f.coupon_amount as f买立减优惠金额,e.coupon_amount as e买立减优惠金额,round(f.credit_amount*p.overpay_year*(1-0.9)/12/100,0) as s买立减优惠金额,f.credit_type as f合同类别,f.account_fee as f月咨询费,round((f.credit_amount*f.account_fee_rate/100),3) as s月咨询费,f.seller_service_fee as f前置商家服务费,(f.credit_amount*p.seller_bonus) as s前置商家服务费,f.customer_service_fee as f前置客户服务费,f.interest_coupon as f每期利息减免,f.finance_coupon as f每期财务费减免,f.stamp_amount as f印花税,(f.credit_amount*0.00005) as s印花税,f.seller_return_fee as f商家返点费用,(f.credit_amount * p.seller_bonus / 100) as s商家返点费用,f.sa_bonus_fee as f销售提成费用,(f.credit_amount * p.sa_bonus / 100) as s销售提成费用,f.cs_fee2 as f月咨询费用2,f.cs_fee_rate2 as f月咨询费率2,f.giveu_month_ir as f即有年化率,p.month_ir as p即有年化率,f.cs_coupon as f每期咨询费减免,f.accident_insurance_fee as f手Q意外保险费用,f.coupon_sales as f抵用券售价,(f.credit_amount*0.1) as s抵用券售价,f.voucher_amount as f抵用券面值,ceil((f.credit_amount*0.1)/100)*100 as s抵用券面值,f.voucher_count as f抵用券数量,(f.voucher_amount/100) as s抵用券数量,i.type_instalment as i百宝箱92,i.value_instalment as i百宝箱费用,g.broken_screen_type as 碎屏零未购买二已购买,g.stag_insurance_type as 碎屏零未购买二已购买,f.broken_screen_service as f碎屏,g.BROKEN_SCREEN_SERVICE as g碎屏,f.stag_insurance_service as f延保,g.STAG_INSURANCE_SERVICE as g延保,y.service_category as 一延保二碎屏,y.goods_category as 零通用一普通二苹果,y.payment_num_price as s碎屏延保费用"
	 		+ " from dafy_sales.cs_credit c inner join dafy_sales.product p on p.ID = c.id_product inner join dafy_sales.cs_credit_fee f on c.id=f.id_credit inner join dafy_sales.cs_experience e on c.id=e.id_credit inner join dafy_sales.cs_goods g on c.id=g.id_credit inner join dafy_sales.product_added_services y on y.payment_num=p.payment_num inner join dafy_sales.instalment i on i.id_credit=c.id "
	 		+ "where c.contract_no="+ contract_no +""
	 		+ "and rownum<=40"
	 		+ "order by i.type_instalment";
	 
	 try {
		 if("正式版本".equals(DataVersion)){
	    	    Class.forName(Fdriver);
	    	    con = DriverManager.getConnection(Furl, Fname, Fpasswd); 
	    	    statement = con.createStatement();
	    	}
	    	if("测试版本".equals(DataVersion)){
		    	Class.forName(Tdriver); 
		    	con = DriverManager.getConnection(Turl, Tname, Tpasswd); 
		    	statement = con.createStatement();
		    }
	    	if("开发版本".equals(DataVersion)){
		    	Class.forName(Ddriver);
		    	con = DriverManager.getConnection(Durl, Dname, Dpasswd); 
		    	statement = con.createStatement();
		    }
	      ResultSet res = statement.executeQuery(sql);
	      
	      //计算有多少条记录
	      int count = 0;
	      while(res.next()){
	      count++;
	      }
	      res = statement.executeQuery(sql);
	      //将查询获得的记录数据，转换成适合生成JTable的数据形式
	      Object[][] info = new Object[count][92];
	      count = 0;
	      while(res.next()){
	    	  info[count][0] = res.getString("合同号");
	    	  info[count][1] = res.getString("合同资金模式");
	    	  info[count][2] = res.getString("F贷款金额");
	    	  info[count][3] = res.getString("C贷款金额");
	    	  info[count][4] = res.getString("G贷款金额");
	    	  info[count][5] = res.getString("C首付");
	    	  info[count][6] = res.getString("G首付");
	    	  info[count][7] = res.getString("C商品价格");
	    	  info[count][8] = res.getString("G商品价格");
	    	  info[count][9] = res.getString("S商品价格");
	    	  info[count][10] = res.getString("C首次扣款日");
	    	  info[count][11] = res.getString("E首次扣款日");
	    	  info[count][12] = res.getString("S每月总还款额");
	    	  info[count][13] = res.getString("I本金1");
	    	  info[count][14] = res.getString("I本金费用");
	    	  info[count][15] = res.getString("F第一期本金");
	    	  info[count][16] = res.getString("S第一期本金");
	    	  info[count][17] = res.getString("I利息2");
	    	  info[count][18] = res.getString("I利息费用");
	    	  info[count][19] = res.getString("F第一期利息");
	    	  info[count][20] = res.getString("S第一期利息");
	    	  info[count][21] = res.getString("S每月应还本息");
	    	  info[count][22] = res.getString("S每月应付费用");
	    	  info[count][23] = res.getString("F月服务费");
	    	  info[count][24] = res.getString("S月服务费");
	    	  info[count][25] = res.getString("F月服务费率");
	    	  info[count][26] = res.getString("S月服务费率");
	    	  info[count][27] = res.getString("C还款方式");
	    	  info[count][28] = res.getString("F纯月还款额");
	    	  info[count][29] = res.getString("C纯月还款额");
	    	  info[count][30] = res.getString("S纯月还款额");
	    	  info[count][31] = res.getString("C权益包购买状态");
	    	  info[count][32] = res.getString("F权益包费用");
	    	  info[count][33] = res.getString("C权益包费用");
	    	  info[count][34] = res.getString("E是否购买保险");
	    	  info[count][35] = res.getString("保险类型");
	    	  info[count][36] = res.getString("I保险70");
	    	  info[count][37] = res.getString("I保险费");
	    	  info[count][38] = res.getString("F保险费");
	    	  info[count][39] = res.getString("C保险费");
	    	  info[count][40] = res.getString("E保险费");
	    	  info[count][41] = res.getString("S保险费");
	    	  info[count][42] = res.getString("F每月优惠金额");
	    	  info[count][43] = res.getString("S每月优惠金额");
	    	  info[count][44] = res.getString("F前置咨询费");
	    	  info[count][45] = res.getString("F月贷款利率");
	    	  info[count][46] = res.getString("P月贷款利率");
	    	  info[count][47] = res.getString("F月咨询费用率");
	    	  info[count][48] = res.getString("P月咨询费率");
	    	  info[count][49] = res.getString("F买立减优惠金额");
	    	  info[count][50] = res.getString("E买立减优惠金额");
	    	  info[count][51] = res.getString("S买立减优惠金额");
	    	  info[count][52] = res.getString("F合同类别");
	    	  info[count][53] = res.getString("F月咨询费");
	    	  info[count][54] = res.getString("S月咨询费");
	    	  info[count][55] = res.getString("F前置商家服务费");
	    	  info[count][56] = res.getString("S前置商家服务费");
	    	  info[count][57] = res.getString("F前置客户服务费");
	    	  info[count][58] = res.getString("F每期利息减免");
	    	  info[count][59] = res.getString("F每期财务费减免");
	    	  info[count][60] = res.getString("F印花税");
	    	  info[count][61] = res.getString("S印花税");
	    	  info[count][62] = res.getString("F商家返点费用");
	    	  info[count][63] = res.getString("S商家返点费用");
	    	  info[count][64] = res.getString("F销售提成费用");
	    	  info[count][65] = res.getString("S销售提成费用");
	    	  info[count][66] = res.getString("F月咨询费用2");
	    	  info[count][67] = res.getString("F月咨询费率2");
	    	  info[count][68] = res.getString("F即有年化率");
	    	  info[count][69] = res.getString("P即有年化率");
	    	  info[count][70] = res.getString("F每期咨询费减免");
	    	  info[count][71] = res.getString("F手Q意外保险费用");
	    	  info[count][72] = res.getString("F抵用券售价");
	    	  info[count][73] = res.getString("S抵用券售价");
	    	  info[count][74] = res.getString("F抵用券面值");
	    	  info[count][75] = res.getString("S抵用券面值");
	    	  info[count][76] = res.getString("F抵用券数量");
	    	  info[count][77] = res.getString("S抵用券数量");
	    	  info[count][78] = res.getString("I百宝箱92");
	    	  info[count][79] = res.getString("I百宝箱费用");
//	    	  info[count][80] = res.getString("F百宝箱费用");
//	    	  info[count][81] = res.getString("S百宝箱费用");
//	    	  info[count][82] = res.getString("F百宝箱是否购买");
	    	  info[count][83] = res.getString("碎屏零未购买二已购买");
	    	  info[count][84] = res.getString("碎屏零未购买二已购买");
	    	  info[count][85] = res.getString("F碎屏");
	    	  info[count][86] = res.getString("G碎屏");
	    	  info[count][87] = res.getString("F延保");
	    	  info[count][88] = res.getString("G延保");
	    	  info[count][89] = res.getString("一延保二碎屏");
	    	  info[count][90] = res.getString("零通用一普通二苹果");
	    	  info[count][91] = res.getString("S碎屏延保费用");

	       count++;
	       }
	
	       //定义表头
	       String[] title = {
	    		   "合同号",
	    		   "合同资金模式",
	    		   "F贷款金额",
	    		   "C贷款金额",
	    		   "G贷款金额",
	    		   "C首付",
	    		   "G首付",
	    		   "C商品价格",
	    		   "G商品价格",
	    		   "S商品价格",
	    		   "C首次扣款日",
	    		   "E首次扣款日",
	    		   "S每月总还款额",
	    		   "I本金1",
	    		   "I本金费用",
	    		   "F第一期本金",
	    		   "S第一期本金",
	    		   "I利息2",
	    		   "I利息费用",
	    		   "F第一期利息",
	    		   "S第一期利息",
	    		   "S每月应还本息",
	    		   "S每月应付费用",
	    		   "F月服务费",
	    		   "S月服务费",
	    		   "F月服务费率",
	    		   "S月服务费率",
	    		   "C还款方式",
	    		   "F纯月还款额",
	    		   "C纯月还款额",
	    		   "S纯月还款额",
	    		   "C权益包购买状态",
	    		   "F权益包费用",
	    		   "C权益包费用",
	    		   "E是否购买保险",
	    		   "保险类型",
	    		   "I保险70",
	    		   "I保险费",
	    		   "F保险费",
	    		   "C保险费",
	    		   "E保险费",
	    		   "S保险费",
	    		   "F每月优惠金额",
	    		   "S每月优惠金额",
	    		   "F前置咨询费",
	    		   "F月贷款利率",
	    		   "P月贷款利率",
	    		   "F月咨询费用率",
	    		   "P月咨询费率",
	    		   "F买立减优惠金额",
	    		   "E买立减优惠金额",
	    		   "S买立减优惠金额",
	    		   "F合同类别",
	    		   "F月咨询费",
	    		   "S月咨询费",
	    		   "F前置商家服务费",
	    		   "S前置商家服务费",
	    		   "F前置客户服务费",
	    		   "F每期利息减免",
	    		   "F每期财务费减免",
	    		   "F印花税",
	    		   "S印花税",
	    		   "F商家返点费用",
	    		   "S商家返点费用",
	    		   "F销售提成费用",
	    		   "S销售提成费用",
	    		   "F月咨询费用2",
	    		   "F月咨询费率2",
	    		   "F即有年化率",
	    		   "P即有年化率",
	    		   "F每期咨询费减免",
	    		   "F手Q意外保险费用",
	    		   "F抵用券售价",
	    		   "S抵用券售价",
	    		   "F抵用券面值",
	    		   "S抵用券面值",
	    		   "F抵用券数量",
	    		   "S抵用券数量",
	    		   "I百宝箱92",
	    		   "I百宝箱费用",
	    		   "F百宝箱费用",
	    		   "S百宝箱费用",
	    		   "F百宝箱是否购买",
	    		   "碎屏零未购买二已购买",
	    		   "碎屏零未购买二已购买",
	    		   "F碎屏",
	    		   "G碎屏",
	    		   "F延保",
	    		   "G延保",
	    		   "一延保二碎屏",
	    		   "零通用一普通二苹果",
	    		   "S碎屏延保费用",
			 };
	       
	         //创建JTable
	         tabDemo = new JTable(info,title);
	         //显示表头
	         jth = tabDemo.getTableHeader();
	         //通过点击表头来排序列中数据resort data by clicking table header
	         RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabDemo.getModel());
	         tabDemo.setRowSorter(sorter);
	         //设置数据与单元格边框的眉边距
	         tabDemo.setIntercellSpacing(new Dimension(50,5)); 
	         //设置数据与单元格边框是否居中等
	         setTableColumnCenter(tabDemo);
	         //固定表格的列宽
	         //setFixColumnWidth(tabDemo); 
	         //根据单元内的数据内容自动调整列宽resize column width accordng to content of cell automatically
	         fitTableColumns(tabDemo);
	         //设置每一列的高度
		     tabDemo.setRowHeight(22);
	         //将JTable加入到带纵向滚动条的面板中
	         databasetools.ui.frame.HomePageWindow_1920x1080.DataDisplayList.getViewport().add(tabDemo); 
	         //横向滚动条
	         tabDemo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	         tabDemo.setColumnSelectionAllowed(true);
	         
	         res.close();
	         con.close();
	         statement.close();
	         }catch (SQLException | ClassNotFoundException e) {
		              JOptionPane.showMessageDialog(null,"SQL定义信息有误！");
	                  e.printStackTrace();
	                  System.err.println(e.getClass().getName() + ": " + e.getMessage());
//	                  System.exit(0);
	         }
	return false;
	}
	
	//查询对应SQL的SQL详情信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean btnSQLButton(String DataVersion,String Sql){
   
	 String sql = Sql; 
//			    + " and rownum<10000";
	 
	 try {
		 if("正式版本".equals(DataVersion)){
	    	    Class.forName(Fdriver);
	    	    con = DriverManager.getConnection(Furl, Fname, Fpasswd); 
	    	    statement = con.createStatement();
	    	}
	    	if("测试版本".equals(DataVersion)){
		    	Class.forName(Tdriver); 
		    	con = DriverManager.getConnection(Turl, Tname, Tpasswd); 
		    	statement = con.createStatement();
		    }
	    	if("开发版本".equals(DataVersion)){
		    	Class.forName(Ddriver);
		    	con = DriverManager.getConnection(Durl, Dname, Dpasswd); 
		    	statement = con.createStatement();
		    }
	      ResultSet res = statement.executeQuery(sql);
		  System.out.println(sql);
	      boolean moreRecords = res.next(); // 定位到达第一条记录
	      if (!moreRecords) {
	            JOptionPane.showMessageDialog(null, "未查询到数据！");
	      }
	      Vector rows = new Vector();
	      Vector columnHeads = new Vector();
	      ResultSetMetaData rsmd = res.getMetaData(); // 获得rs结果集中列属性信息
	           for (int i = 1; i <= rsmd.getColumnCount(); ++i)
	                columnHeads.addElement(rsmd.getColumnName(i)); // 获得列名(将列名存放至向量columnHeads)

	            do {
	                rows.addElement(getNextRow(res, rsmd));
	            } 
	            while (res.next()); // 利用循环获得所有记录
	            tabDemo = new JTable(rows, columnHeads); // 将获得的行列数据信息作为参数重新构造表格视图
	            jth = tabDemo.getTableHeader();
	            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabDemo.getModel());
		        tabDemo.setRowSorter(sorter);
		        //设置数据与单元格边框的眉边距
		        tabDemo.setIntercellSpacing(new Dimension(50,5)); 
		        //设置数据与单元格边框是否居中等
		        setTableColumnCenter(tabDemo);
		        //设置每一列的高度
		        tabDemo.setRowHeight(22);
		        //固定表格的列宽
		        //setFixColumnWidth(tabDemo); 
		        //根据单元内的数据内容自动调整列宽resize column width accordng to content of cell automatically
		        fitTableColumns(tabDemo);
		        //将JTable加入到带纵向滚动条的面板中
		        databasetools.ui.frame.HomePageWindow_1920x1080.DataDisplayList.getViewport().add(tabDemo); 
		        
		        //横向滚动条
		        tabDemo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		        tabDemo.setColumnSelectionAllowed(true);    
	            res.close();
	            con.close();
	            statement.close();
				JOptionPane.showMessageDialog(null,"查询成功！");
	            }catch (SQLException | ClassNotFoundException e) {
		              JOptionPane.showMessageDialog(null,"SQL定义信息有误！");
	                  e.printStackTrace();
	                  System.err.println(e.getClass().getName() + ": " + e.getMessage());
	         }
	return false;
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static Vector getNextRow(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        Vector currentRow = new Vector(); // 定义一个向量,用于存放记录
        for (int i = 1; i <= rsmd.getColumnCount(); ++i)
            currentRow.addElement(rs.getString(i)); // 获取记录
        return currentRow; // 返回记录
    }
    
	public static void fitTableColumns(JTable myTable){
	     myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	     JTableHeader header = myTable.getTableHeader();
	     int rowCount = myTable.getRowCount();
	     Enumeration<?> columns = myTable.getColumnModel().getColumns();
	     while(columns.hasMoreElements())
	     {
	         TableColumn column = (TableColumn)columns.nextElement();
	         int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
	         int width = (int)header.getDefaultRenderer().getTableCellRendererComponent
	         (myTable, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
	         for(int row = 0; row < rowCount; row++)
	         {
	             int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent
	             (myTable, myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
	             width = Math.max(width, preferedWidth);
	         }
	         header.setResizingColumn(column); //此行很重要
	         column.setWidth(width+myTable.getIntercellSpacing().width);
	     }
	}

    /** 
     * 表格数据居中 
     * @param table 
     */  
    public static void setTableColumnCenter(JTable table){  
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();     
        r.setHorizontalAlignment(JLabel.CENTER);     
        table.setDefaultRenderer(Object.class, r);  
    }  
    

}
