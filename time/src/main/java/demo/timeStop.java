package demo;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 作者 Name:CaoGang
 * @version 创建时间：2017年11月10日 下午3:41:49 类说明
 */
public class timeStop {
	// 上班时间..
	private final static Integer OfficeHours = 9;
	// 下班时间
	private final Integer TimeFromWork = 18;

	public static void main(String[] args) {
		// 2017年11月10日TODO
		timeStop timeStop = new timeStop();
		// System.out.println(timeStop.resultTimeL(24, 2, 1));
		// System.out.println(timeStop.getYear(timeStop.getSim("N"), new Date(),
		// timeStop.resultTimeL(20, 0, 0)));
		// System.out.println(timeStop.getYear(timeStop.getSim("y"), new Date(),
		// timeStop.resultTimeL(20, 0, 0)));
		// System.out.println(timeStop.getYear(timeStop.getSim("r"), new Date(),
		// timeStop.resultTimeL(20, 0, 0)));
		// System.out.println(timeStop.getYear(timeStop.getSim("h"), new Date(),
		// timeStop.resultTimeL(20, 0, 0)));
		// System.out.println(timeStop.getYear(timeStop.getSim("f"), new Date(),
		// timeStop.resultTimeL(20, 0, 0)));
		// System.out.println(timeStop.getYear(timeStop.getSim("s"), new Date(),
		// timeStop.resultTimeL(20, 0, 0)));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		// calendar.set(Calendar.HOUR_OF_DAY, OfficeHours);
		//calendar.add(Calendar.HOUR_OF_DAY, 1);
		// System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
		// System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
		// System.out.println(calendar.getTime());
		System.out.println(calendar.getTime());
		System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println(timeStop.getTimeout(calendar));
	}
	/**
	 * 
	 * @param calendar
	 * @return 根据时间判断是否是节假日
	 */
	public Date getTimeout(Calendar calendar) {
		// 获取时间
		if (calendar.get(Calendar.HOUR_OF_DAY) >= TimeFromWork) {
			System.out.println("我超时了!");
			// 天
			calendar.add(Calendar.DATE, 1);
			System.out.println(calendar.getTime());
			// 如果是星期天在加一天
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
				System.out.println("我是星期天");
				calendar.add(Calendar.DATE, 1);
			}
			// 时
			calendar.set(Calendar.HOUR_OF_DAY, OfficeHours + (calendar.get(Calendar.HOUR_OF_DAY) - TimeFromWork));
			// 分
			calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
			// 秒
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
		}
		System.out.println(calendar.getTimeInMillis());
		return calendar.getTime();
	}

	/**
	 * 
	 * @param hours
	 *            时
	 * @param subdivision分
	 * @param second秒
	 * @return long
	 */
	public Long resultTimeL(Integer hours, Integer subdivision, Integer second) {
		return ((hours * 3600000L) + (subdivision * 60000L) + (second * 1000L));
	}

	/**
	 * 
	 * @param date
	 * @param 时间
	 * @return 年月日
	 */
	public String getYear(SimpleDateFormat simpleDateFormat, Date date, Long time) {
		return simpleDateFormat.format(new Date(date.getTime() + time));
	}

	/**
	 * 
	 * @param time
	 * @return SimpleDateFormat 获取SimpleDateFormat时间格式
	 */
	public SimpleDateFormat getSim(String time) {
		if (time.toUpperCase().equals("N")) {
			time = "yyyy";
		} else if (time.toUpperCase().equals("Y")) {
			time = "MM";
		} else if (time.toUpperCase().equals("R")) {
			time = "dd";
		} else if (time.toUpperCase().equals("H")) {
			time = "hh";
		} else if (time.toUpperCase().equals("F")) {
			time = "mm";
		} else if (time.toUpperCase().equals("S")) {
			time = "ss";
		} else if (time.toUpperCase().equals("W")) {
			time = "EEEE";
		}
		return new SimpleDateFormat(time);
	}
}