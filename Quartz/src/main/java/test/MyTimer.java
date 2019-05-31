package test;

import java.util.Date;

/**
 * 自定义一个定时器
 * 
 * @author lw
 */
public class MyTimer extends Thread {

	private Long time;

	public MyTimer(Long time) {
		this.time = time;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			send();
		}
	}

	/**
	 * 开始执行任务
	 */
	public void execute() {
		this.start();
	}

	/**
	 * 定时任务
	 */
	private void send() {
		System.out.println("任务执行了:" + new Date());
	}

	public static void main(String[] args) {
		MyTimer myTimer = new MyTimer(2000L);
		myTimer.execute();
	}

}