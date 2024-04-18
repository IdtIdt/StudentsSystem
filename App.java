package StudentSystem;

import StudentSystem.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("欢迎来到学生管理系统");
            System.out.println("请选择操作：1.登录 2.注册 3.忘记密码 4.退出");
            String choose = sc.next();
            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> forgetPassword(list);
                case "4" -> {
                    System.out.println("谢谢使用，再见");
                    System.exit(0);
                }

                default -> System.out.println("没有这个选项");
            }
        }
    }

    private static void forgetPassword(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名");
        String username = sc.next();
        boolean flag = contains(list, username);
        if(!flag){
            System.out.println("当前用户"+username+"未注册，请先注册");
            return;
        }

        //键盘录入身份证号码和手机号码
        System.out.println("请输入身份证号码");
        String personID = sc.next();
        System.out.println("请输入手机号码");
        String phoneNumber = sc.next();

        //比较用户对象的手机号码和身份证号码是否相同
        int index = findIndex(list,username);
        User user = list.get(index);
        //将用户对象通过索引先获取出来
        if(!(user.getPersonID().equalsIgnoreCase(personID) && user.getPhoneNumber().equalsIgnoreCase(phoneNumber))){
            System.out.println("输入有误，不能修改密码");
            return;
        }
        //当代码执行到这里的时候，表示所有的数据全部验证成功
        String password;
        while (true) {
            System.out.println("请输入新的密码");
            password = sc.next();
            System.out.println("请再次输入新的密码");
            String againPassword = sc.next();
            if(password.equals(againPassword)){
                System.out.println("两次输入的密码一致");
                break;
            }else{
                System.out.println("两次输入不一致，请重新输入");
                continue;
            }
        }

        user.setPassword(password);
        System.out.println("密码修改成功");
    }

    private static int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if(user.getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }

    private static void register(ArrayList<User> list) {
        //1.键盘录入用户名
        Scanner sc = new Scanner(System.in);
        String username;
        while (true) {
            System.out.println("请输入用户名");
            username = sc.next();
            //先验证格式是否正确，再验证是否唯一

            boolean flag1 = checkUsername(username);
            if (!flag1) {
                System.out.println("用户名格式名不满足条件，需要重新输入");
                continue;
            }
            //校验用户名唯一
            //username到集合中判断是否与存在
            boolean flag2 = contains(list, username);
            if (flag2) {
                //用户名已存在，那么当前用户名无法注册，需要重新输入
                System.out.println("用户名" + username + "已存在，请重新输入");
            } else {
                //不存在，表示当前用户名可用，可以继续录入下面的其他数据
                System.out.println("用户名" + username + "可用");
                break;
            }
        }
        //2.键盘录入密码
        //输入两次，一致才可以注册
        String password;
        while (true) {
            System.out.println("请输入要注册的密码");
            password = sc.next();
            System.out.println("请再次输入要注册的密码");
            String againPassword = sc.next();
            if (!password.equals(againPassword)) {
                System.out.println("两次密码输入不一致，请重新输入");
                continue;
            } else {
                System.out.println("两次输入一致，请继续输入其他数据");
                break;
            }
        }
        //3.键盘录入身份证号码
        String personID;
        while (true) {
            System.out.println("请输入身份证号码");
            personID = sc.next();
            boolean flag = checkPersonID(personID);
            if (flag) {
                System.out.println("身份证号码满足要求");
                break;
            } else {
                System.out.println("身份证号码格式有误，请重新输入");
                continue;
            }
        }
/*
        4.键盘录入手机号码
        长度11位
        必须都是数字
        不能以0开头
*/
        String phoneNumber;
        while (true) {
            System.out.println("请输入手机号码");
            phoneNumber = sc.next();
            boolean flag = checkPhoneNumber(phoneNumber);
            if (flag) {
                System.out.println("手机号码格式正确");
                break;
            } else {
                System.out.println("手机号码格式有误，请重新输入");
                continue;
            }
        }

        User u = new User(username, password, personID, phoneNumber);
        list.add(u);
        System.out.println("注册成功");

        printList(list);
    }

    private static void printList(ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            System.out.println(user.getUsername() + "," + user.getPassword() + "," + user.getPersonID() + "," + user.getPhoneNumber());
        }
    }

    //用户名，密码，身份证号码，手机号码放到用户对象中

    //把用户信息添加到集合中

    private static boolean checkPhoneNumber(String phoneNumber) {
        /*
          长度11位
          必须都是数字
          不能以0开头
        */
        if (phoneNumber.length() != 11) {
            return false;
        }
        boolean b = phoneNumber.startsWith("0");
        if (b) {
            return false;
        }
        //必须都是数字
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        //当循环结束后，表示每一个字符都在0-9之间
        return true;
    }

    private static boolean checkPersonID(String personID) {
        //长度为18
        if (personID.length() != 18) {
            return false;
        }
        //不能以0开头
        boolean flag = personID.startsWith("0");
        if (flag) {
            return false;
        }
        //前17位必须都是数字
        for (int i = 0; i < personID.length(); i++) {
            char c = personID.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }

        char endChar = personID.charAt(personID.length() - 1);
        if ((endChar >= '0' && endChar <= '9') || (endChar == 'x') || (endChar == 'X')) {
            return true;
        } else {
            return false;
        }

    }

    private static boolean contains(ArrayList<User> list, String username) {
        //循环遍历集合，得到每一个用户对象
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            String rightUsername = user.getUsername();
            if (rightUsername.equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkUsername(String username) {
//        if(username.length() < 3 ||username.length() > 15)    方法调用两次 效率略差
        int len = username.length();
        if (len < 3 || len > 15) {//变量调用两次  方法一次  效率略微提高
            return false;
        }//满足长度了

        //继续校验
        //循环得到username里面的每一个字符，遍历
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                return false;
            }
        }//满足内容了

        int count = 0;
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count++;
            }
        }
        return count > 0;
    }

    private static void login(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        String username = null;
        String password = null;
        for (int i = 0; i < 3; i++) {
            System.out.println("登录");
            username = sc.next();
            //判断用户名是否存在
            boolean flag = contains(list, username);
            if (!flag) {
                System.out.println("用户名" + username + "未注册，请先注册再登录");
                return;
            }
            System.out.println("请输入密码");
            password = sc.next();

            while (true) {
                String rightCode = getCode();
                System.out.println("当前正确的验证码为" + rightCode);
                System.out.println("请输入验证码");
                String code = sc.next();
                if (code.equalsIgnoreCase(rightCode)) {
                    System.out.println("验证码正确");
                    break;
                } else {
                    System.out.println("验证码错误");
                    continue;
                }
            }


            //验证用户名和密码是否正确
            //集合中是否包含当前的用户名和密码
            //定义一个方法验证用户名和密码是否正确


            //封装思想的利用
            //把离散的数据封装成对象
            //以后传递参数的时候只需要传递一个整体，不需要管离散的数据

            User useInfo = new User(username, password, null, null);
            boolean result = checkInfo(list, useInfo);
            if (result) {
                System.out.println("登陆成功，可以开始使用学生管理系统");
                break;
            } else {
                System.out.println("登陆失败，用户名货密码错误");
                if (i == 2) {
                    System.out.println("当前账号" + username + "被锁定，请联系客服：XXX-XXXXXXXX");
                    //锁定后直接结束方法即可
                    return;
                } else {
                    System.out.println("用户名或密码错误，还剩下" + (2 - i) + "次机会");
                }
            }
        }
    }

    private static boolean checkInfo(ArrayList<User> list, User useInfo) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUsername().equals(useInfo.getUsername()) && user.getPassword().equals(useInfo.getPassword())) {
                return true;
            }
        }
        return false;
    }

    //生成一个验证码
    private static String getCode() {
        //1.创建一个集合添加所有的大写和小写字母
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }

        StringBuilder sb = new StringBuilder();
        //2.随机抽取四个字符
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            //把随机字符添加到sb当中
            sb.append(c);
        }

//        3.把随机数字添加到末尾
        int number = r.nextInt(10);
        sb.append(number);
        System.out.println(sb);

        //4.如果我们要修改字符串中的内容，先把字符串变成字符数组，在数组中修改，然后再创建一个新的字符串
        char[] arr = sb.toString().toCharArray();
        int randomIndex = r.nextInt(arr.length);
        char temp = arr[randomIndex];
        arr[randomIndex] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;
        return new String(arr);
    }

}
