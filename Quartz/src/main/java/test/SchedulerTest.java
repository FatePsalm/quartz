package test;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 调用任务的类
 * 
 * @author lhy
 *
 */
public class SchedulerTest {
	public static void main(String[] args) {
		//
		// 通过schedulerFactory获取一个调度器
		SchedulerFactory schedulerfactory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			// 通过schedulerFactory获取一个调度器
			scheduler = schedulerfactory.getScheduler();
			// 创建jobDetail实例，绑定Job实现类
			// 指明job的名称，所在组的名称，以及绑定job类
			JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "jgroup1").build();
			// 定义调度触发规则
			// 使用simpleTrigger规则
			// Trigger trigger=TriggerBuilder.newTrigger().withIdentity("simpleTrigger",
			// "triggerGroup")
			// .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withRepeatCount(8))
			// .startNow().build();
			// 使用cornTrigger规则 每天10点42分
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "triggerGroup")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 14 15 * * ? *")).startNow().build();
			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(job, trigger);
			// 启动调度
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*
 * 1、 CronTrigger时间格式配置说明 CronTrigger配置格式: 格式: [秒] [分] [小时] [日] [月] [周] [年] 序号
 * 说明 是否必填 允许填写的值 允许的通配符 1秒是0-59, - * / 2分是0-59, - * / 3小时是0-23, - * / 4日是1-31,
 * - * ? / L W 5月是1-12 or JAN-DEC, - * / 6周是1-7 or SUN-SAT, - * ? / L # 7年否empty
 * 或 1970-2099, - * / 通配符说明: ：表示所有值. 例如:在分的字段上设置 "*",表示每一分钟都会触发。 ?
 * ：表示不指定值。使用的场景为不需要关心当前设置这个字段的值。例如:要在每月的10号触发一个操作，但不关心是周几，所以需要周位置的那个字段设置为"?"
 * 具体设置为 0 0 0 10 * ? - ：表示区间。例如 在小时上设置 "10-12",表示 10,11,12点都会触发。 ,
 * ：表示指定多个值，例如在周字段上设置 "MON,WED,FRI" 表示周一，周三和周五触发 / ：用于递增触发。如在秒上面设置"5/15"
 * 表示从5秒开始，每增15秒触发(5,20,35,50)。 在月字段上设置'1/3'所示每月1号开始，每隔三天触发一次。 L
 * ：表示最后的意思。在日字段设置上，表示当月的最后一天(依据当前月份，如果是二月还会依据是否是润年[leap]),
 * 在周字段上表示星期六，相当于"7"或"SAT"。如果在"L"前加上数字，则表示该数据的最后一个。
 * 例如在周字段上设置"6L"这样的格式,则表示“本月最后一个星期五" W ：表示离指定日期的最近那个工作日(周一至周五).
 * 例如在日字段上设置"15W"，表示离每月15号最近的那个工作日触发。如果15号正好是周六，则找最近的周五(14号)触发,
 * 如果15号是周未，则找最近的下周一(16号)触发.如果15号正好在工作日(周一至周五)，则就在该天触发。如果指定格式为
 * "1W",它则表示每月1号往后最近的工作日触发。如果1号正是周六，则将在3号下周一触发。(注，"W"前只能设置具体的数字,不允许区间"-"). 'L'和
 * 'W'可以一组合使用。如果在日字段上设置"LW",则表示在本月的最后一个工作日触发
 * 
 * #
 * ：序号(表示每月的第几周星期几)，例如在周字段上设置"6#3"表示在每月的第三个周星期六.注意如果指定"6#5",正好第五周没有星期六，则不会触发该配置(
 * 用在母亲节和父亲节再合适不过了) 周字段的设置，若使用英文字母是不区分大小写的 MON 与mon相同. 常用示例: 格式: [秒] [分] [小时]
 * [日] [月] [周] [年] 0 0 12 * * ? 每天12点触发 0 15 10 ? * * 每天10点15分触发 0 15 10 * * ?
 * 每天10点15分触发 0 15 10 * * ? * 每天10点15分触发 0 15 10 * * ? 2005 2005年每天10点15分触发 0 *
 * 14 * * ? 每天下午的 2点到2点59分每分触发 0 0/5 14 * * ? 每天下午的 2点到2点59分(整点开始，每隔5分触发) 0 0/5
 * 14,18 * * ? 每天下午的 18点到18点59分(整点开始，每隔5分触发) 0 0-5 14 * * ? 每天下午的 2点到2点05分每分触发 0
 * 10,44 14 ? 3 WED 3月分每周三下午的 2点10分和2点44分触发 0 15 10 ? * MON-FRI
 * 从周一到周五每天上午的10点15分触发 0 15 10 15 * ? 每月15号上午10点15分触发 0 15 10 L * ?
 * 每月最后一天的10点15分触发 0 15 10 ? * 6L 每月最后一周的星期五的10点15分触发 0 15 10 ? * 6L 2002-2005
 * 从2002年到2005年每月最后一周的星期五的10点15分触发 0 15 10 ? * 6#3 每月的第三周的星期五开始触发 0 0 12 1/5 * ?
 * 每月的第一个中午开始每隔5天触发一次 0 11 11 11 11 ? 每年的11月11号 11点11分触发(光棍节)
 * 
 * 
 */