package timeout;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import redis.clients.jedis.Jedis;

/**
* @author 作者 Name:CaoGang
* @version 创建时间：2017年11月13日 下午5:05:04
* 类说明
*/
public class Myjob implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("111111");
//		Timeout timeout=new Timeout();
//		// 获取线程池
//		Jedis jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
//		List<String> list=timeout.getOrderOutTime();
//		if(list!=null&&list.size()!=0) {
//			for(String e:list) {
//				jedis.zrem("orderOutTime", e);
//				System.out.println("我是任务！！！"+e);
//			}
//			
//		}else {
//			System.out.println("任务结束！！");
//		}
//		try {
//			SchedulerFactoryUtil.getInstance().getScheduler().shutdown();
//		} catch (SchedulerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		};

	}

}
