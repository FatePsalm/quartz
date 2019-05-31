package timeout;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/***
 * 
 * @author cancer 静态内部类生成时间定时器
 */
public class SchedulerFactoryUtil {
	private static class results {
		private static final SchedulerFactory INSTANCE = new StdSchedulerFactory();
	}
	//私有化构造方法
	private SchedulerFactoryUtil() {
	}
	//返回实咧
	public static final SchedulerFactory getInstance() {
		return results.INSTANCE;
	}
}