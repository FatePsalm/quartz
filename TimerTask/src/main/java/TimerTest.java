import java.util.Timer;
import java.util.TimerTask;



/**
* @author 作者 Name:CaoGang
* @version 创建时间：2018年1月16日 下午4:52:03
* 类说明
*/
public class TimerTest extends TimerTask{

	private String jobName = "";

	public TimerTest(String jobName) {
	this.jobName = jobName;
	}

	public void run() {
	System.out.println("execute " + jobName);
	}

	public static void main(String[] args) {
	Timer timer = new Timer();
	long delay1 = 1 * 1000;
	long period1 = 1000;
	// 从现在开始 1 秒钟之后，每隔 1 秒钟执行一次 job1
	timer.schedule(new TimerTest("job1"), 0, period1);
	long delay2 = 2 * 1000;
	long period2 = 2000;
	// 从现在开始 2 秒钟之后，每隔 2 秒钟执行一次 job2
	timer.schedule(new TimerTest("job2"), 0, period2);
	}
}
