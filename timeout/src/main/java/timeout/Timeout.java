package timeout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import redis.clients.jedis.Jedis;

/**
 * @author 作者 Name:CaoGang
 * @version 创建时间：2017年11月13日 上午10:58:19 类说明 结合redis执行超时任务
 */
public class Timeout {
	// 上班时间..
	private final static Integer OfficeHours = 9;
	// 下班时间
	private final static Integer TimeFromWork = 18;
	// 超时时间
	private final static Integer OUTTIME = 0;

	public static void main(String[] args) {
		Timeout timeout = new Timeout();
		// Calendar calendar = Calendar.getInstance();
		// for (int i = 0; i < 3; i++) {
		// calendar.add(Calendar.MINUTE, 1);
		// timeout.orderTime(new Date(calendar.getTime().getTime()), i + "");
		// }
		// timeout.getOrderOutTime();
		// SchedulerFactory a = SchedulerFactoryUtil.getInstance();
		// SchedulerFactory b = SchedulerFactoryUtil.getInstance();
		// SchedulerFactory c = SchedulerFactoryUtil.getInstance();
		// System.out.println(a==b&&b==c);
		timeout.getQuartz(new Date());
	}

	/**
	 * 
	 * @param date
	 * @param order
	 * @return
	 */
	public void orderTime(Date date, String order) {
		Calendar calendar = Calendar.getInstance();
		// 获取线程池
		Jedis jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, OUTTIME);
		Date outDate = getTimeout(calendar);
		System.out.println("outDate" + outDate.getTime() + "=====");
		try {
			jedis.zadd("orderOutTime", outDate.getTime(), order);
		} finally {
			// 关闭Redis
			JedisPoolUtil.release(JedisPoolUtil.getJedisPoolInstance(), jedis);
		}

	}

	/**
	 * 
	 * @param date
	 *            启动定时器
	 */
	public void getQuartz(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, 5);
		System.out.println(calendar.get(Calendar.YEAR));// 年
		System.out.println(calendar.get(Calendar.MONTH) + 1);// 月
		System.out.println(calendar.get(Calendar.DATE));// 日
		System.out.println(calendar.get(Calendar.HOUR_OF_DAY));// 时
		System.out.println(calendar.get(Calendar.MINUTE));// 分
		System.out.println(calendar.get(Calendar.SECOND));// 秒
		// 格式: [秒] [分] [小时] [日] [月] [周] [年]
		//String string="0 0 0 0 0 ? 2017/2017";
		 String string = "" + calendar.get(Calendar.SECOND) + " " + calendar.get(Calendar.MINUTE) + " "
				+ calendar.get(Calendar.HOUR_OF_DAY) + " " + calendar.get(Calendar.DATE) + " "
				+ (calendar.get(Calendar.MONTH) + 1) + " ? " + calendar.get(Calendar.YEAR) + "";
		System.out.println(string);
		// "0 14 15 * * ? *"
		// 通过schedulerFactory获取一个调度器
		SchedulerFactory schedulerfactory = SchedulerFactoryUtil.getInstance();
		Scheduler scheduler = null;
		try {
			// 通过schedulerFactory获取一个调度器
			scheduler = schedulerfactory.getScheduler();
			// 创建jobDetail实例，绑定Job实现类
			// 指明job的名称，所在组的名称，以及绑定job类
			JobDetail job = JobBuilder.newJob(Myjob.class).withIdentity("job1", "jgroup1").build();
			// 定义调度触发规则
			// 使用simpleTrigger规则
			// Trigger trigger=TriggerBuilder.newTrigger().withIdentity("simpleTrigger",
			// "triggerGroup")
			// .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withRepeatCount(8))
			// .startNow().build();
			// 使用cornTrigger规则 每天10点42分
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule(string)).startNow().build();
			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(job, trigger);
			// 启动调度
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取redis队列中需要执行的任务时间和订单
	 */
	public List<String> getOrderOutTime() {
		List<String> list = new ArrayList<String>();
		// 获取线程池
		Jedis jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
		try {
			// 去队列前面的第一个元素
			Set<String> set = jedis.zrange("orderOutTime", 0, 0);
			System.out.println(set);
			// 如果返回为0则没有数据,有数据则去除第一个数据的执行时间
			if (set.size() != 0) {
				for (String e : set) {
					// 获取第一个元素时间
					Long time = jedis.zscore("orderOutTime", e).longValue();
					// 关闭定时器
					try {
						SchedulerFactoryUtil.getInstance().getScheduler().shutdown();
					} catch (SchedulerException p) {
						p.printStackTrace();
					}
					// 启动定时器
					getQuartz(new Date(time));
					// 删除队列第一个元素
					// jedis.zrem("orderOutTime", e);
					list.add(e);
					list.add(time + "");
				}
			} else {
				// 关闭定时器
				try {
					SchedulerFactoryUtil.getInstance().getScheduler().shutdown();
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
				System.out.println("队列中数据为空!");
			}
		} finally {
			// 关闭Redis
			JedisPoolUtil.release(JedisPoolUtil.getJedisPoolInstance(), jedis);
		}
		return list;
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
		System.out.println("现在时间是====" + calendar.getTime());
		return calendar.getTime();
	}

}
