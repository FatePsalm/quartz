package test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	public static void main(String[] args) {
		Timer timer = new Timer();
		// 5s后执行
		// timer.schedule(new MyTask(), 5000);
		// 马上执行任务,每隔1000执行一次
		timer.scheduleAtFixedRate(new MyTask(), new Date(), 10000);
	}

}

// 定时任务,这是一个线程
class MyTask extends TimerTask {
	@Override
	public void run() {
		System.out.println("task execute ");
	}
}