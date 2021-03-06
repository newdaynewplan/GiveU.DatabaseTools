package image;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

public class qwqe2 extends JFrame implements ActionListener {
// 定义组件	
private JPanel pan = new JPanel();
private JScrollPane scpDemo;
private JTableHeader jth;
private JTable tabDemo;
private JButton btnShow;
private JTextField conttext;
String driver = "oracle.jdbc.driver.OracleDriver";
String url  = "jdbc:oracle:thin:@idcdbtest.dafycredit.com:1521:dbtest01";
String name = "dafy_sales";
String passwd = "Ju$2017";
// 构造方法
public qwqe2(){
// 窗体的相关属性的定义
Font font = new Font("宋体",Font.BOLD,12);
super.setTitle("欢迎登录本系统");
pan.setLayout(null);
this.setSize(835,750);
this.setLocation(200,100);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//this.setSize(1200, 400);  //这是大小
//this.setLocation(400, 400);  //这是位置
//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭结束进程
//this.setResizable(true);  //尺寸是否可变
//this.setVisible(true);  //显示与隐藏

//设置背景  
JPanel bj = new JPanel() {
  protected void paintComponent(Graphics g) {  
      Image bg;  
      try {  
          bg = ImageIO.read(new File("src/image/星空.jpg"));  
          g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);  
      } catch (IOException e) {  
          e.printStackTrace();  
      }  
  }  
};  
//super.setContentPane(bj); 

// 创建组件
this.scpDemo = new JScrollPane();
this.scpDemo.setBounds(10,30,800,550);
this.btnShow = new JButton("查询合同详情");
this.btnShow.setBounds(220,600,120,30);
this.conttext = new JTextField();
this.conttext.setBounds(350,600,300,30);

//// 给按钮注册监听
//this.btnShow.addActionListener(new ActionListener(){
//public void actionPerformed(ActionEvent ae){
//btnShow_ActionPerformed(null);
//}
//});

// 将组件加入到窗体中
add(this.scpDemo);
add(this.btnShow);
add(this.conttext);

super.add(pan);
btnShow.setFont(font);
btnShow.addActionListener(this);

// 显示窗体
this.setVisible(true);
}

public static void main(String[] args){
	
    new qwqe2();
}

@Override
public void actionPerformed(ActionEvent arg0) {
if (arg0.getSource()==btnShow){
	btnShow();
 }
}
//查询合同按钮的事件处理函数
public void btnShow(){
 String contract_no = conttext.getText();
  if(compare(contract_no)){
    JOptionPane.showMessageDialog(null,"查询成功！");
      super.setVisible(true);
  }
  btnShow_ActionPerformed(contract_no);
} 

//对比合同号是否匹配
public boolean compare(String contract_no){
boolean m = false;
Connection c = null;
Statement stmt = null;
String sql = "select CONTRACT_NO from dafy_sales.cs_credit where contract_no="+ contract_no +"";
try{
	  Class.forName(driver);
    c = DriverManager.getConnection(url, name, passwd);  
    c.setAutoCommit(false);
    stmt = c.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    if(rs.next()){
        String pa = rs.getString(1);
        System.out.println(pa+" " +contract_no);
        if(pa.equals(contract_no)){
            m = true;
        }else {
            JOptionPane.showMessageDialog(null, "合同号和数据库不匹配！");
            this.setVisible(false);
        }
    }else {
        JOptionPane.showMessageDialog(null, "合同号不存在123！");
        this.setVisible(true);
    }
    rs.close();
    c.close();
    stmt.close();
     
}catch(SQLException | ClassNotFoundException e){
    e.printStackTrace();
}
return m;
}

//查询合同详情的事件处理
public void btnShow_ActionPerformed(String contract_no){
	Connection c = null;
    Statement stmt = null;
 
 String sql = "select ID, ID_PRODUCT, CREDIT_AMOUNT, INIT_PAY, PRICE, ID_SELLERPLACE, ID_SA, INTER_CODE,ID_PERSON from dafy_sales.cs_credit where contract_no="+ contract_no +"";
 
 try {
 	Class.forName(driver);
     c = DriverManager.getConnection(url, name, passwd);  
     c.setAutoCommit(false);
     System.out.println("Opened database successfully");
     stmt = c.createStatement();
     ResultSet rs = stmt.executeQuery(sql);
//计算有多少条记录
int count = 0;
while(rs.next()){
count++;
}
rs = stmt.executeQuery(sql);
//将查询获得的记录数据，转换成适合生成JTable的数据形式
Object[][] info = new Object[count][9];
count = 0;
while(rs.next()){
info[count][0] = Integer.valueOf(rs.getInt("ID"));
info[count][1] = rs.getString("ID_PRODUCT");
info[count][2] = Integer.valueOf( rs.getInt("CREDIT_AMOUNT"));
info[count][3] = rs.getString("INIT_PAY");
info[count][4] = rs.getString("PRICE");
info[count][5] = rs.getString("ID_SELLERPLACE");
info[count][6] = rs.getString("ID_SA");
info[count][7] = rs.getString("INTER_CODE");
info[count][8] = rs.getString("ID_PERSON");
count++;
}
//定义表头
String[] title = {"学号","姓名","年龄","性别","性别","性别","性别","性别","性别"};
//创建JTable
this.tabDemo = new JTable(info,title);
//显示表头
this.jth = this.tabDemo.getTableHeader();
//将JTable加入到带滚动条的面板中
this.scpDemo.getViewport().add(tabDemo); 
rs.close();
c.close();
stmt.close();
}catch (SQLException | ClassNotFoundException e) {
	JOptionPane.showMessageDialog(null,"合同号不存在222！");
 e.printStackTrace();
 System.err.println(e.getClass().getName() + ": " + e.getMessage());
 System.exit(0);
}
}
}
