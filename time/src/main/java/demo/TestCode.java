package demo;
/**
* @author 作者 Name:CaoGang
* @version 创建时间：2017年11月10日 上午11:00:41
* 类说明
* 年月日转换
*/

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestCode {
	public static void main(String[] args) {
		TestCode testCode = new TestCode();
		//输出...
		//testCode.getWeek();
		testCode.getTimeAdd();
	}
	//时间加减
	public void getTimeAdd() {
		//时间差
		Long Long =(long) (4*(60*60*1000));
		Date date=new Date();
		SimpleDateFormat year=new SimpleDateFormat("yyyy");//年
		SimpleDateFormat months=new SimpleDateFormat("MM");//月
		SimpleDateFormat day=new SimpleDateFormat("dd");//日
		SimpleDateFormat when=new SimpleDateFormat("hh");//时
		SimpleDateFormat subdivision=new SimpleDateFormat("mm");//分
		SimpleDateFormat second=new SimpleDateFormat("ss");//秒
		SimpleDateFormat week=new SimpleDateFormat("EEEE");//星期
		
		System.out.println(year.format(new Date(date.getTime()+Long)));//年
		System.out.println(months.format(new Date(date.getTime()+Long)));//月
		System.out.println(day.format(new Date(date.getTime()+Long)));//日
		System.out.println(when.format(new Date(date.getTime()+Long)));//时
		System.out.println(subdivision.format(new Date(date.getTime()+Long)));//分
		System.out.println(second.format(new Date(date.getTime()+Long)));//秒
		System.out.println(week.format(new Date(date.getTime()+Long)));//星期
	}
	// 根据日期取得星期几
	public String getWeek() {
		Date date = new Date();
		// 年
		SimpleDateFormat a = new SimpleDateFormat("yyyy");
		// 月
		SimpleDateFormat b = new SimpleDateFormat("MMMM");
		// 日
		SimpleDateFormat c = new SimpleDateFormat("dd");
		// 日期
		SimpleDateFormat d = new SimpleDateFormat("EEEE");
		//年
		String aa = a.format(date);
		//月
		String bb = b.format(date);
		//日
		String cc = c.format(date);
		//星期
		String dd = d.format(date);
		//年
		System.out.println(aa);
		//月
		System.out.println(bb);
		//日
		System.out.println(cc);
		//星期
		System.out.println(dd);
		
		return aa;
	}
}