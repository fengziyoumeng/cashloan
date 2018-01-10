package com.rongdu.cashloan.cl.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并行执行多个任务
 * @author djh
 *
 */
public class SingleThreadPool {

	private static ExecutorService threadPool = Executors.newCachedThreadPool();

	public static ExecutorService getThreadPool() {
		return threadPool;
	}


/*
	private static SingleThreadPool threadPool;
	private ExecutorService pool;
	private final static int threadNum = 1;
	private SingleThreadPool(){}//禁止创建多个池对象

	public static LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
	*//**
	 * 获取线程池
	 * @return
	 *//*
	public static SingleThreadPool getThreadPool() {
		if(threadPool==null){
			synchronized(threadPool){
				threadPool = new SingleThreadPool();
				threadPool.pool = Executors.newFixedThreadPool(threadNum);
			}
		}
		return threadPool;
	}


	*//**
	 * 将任务添加到线程池执行
	 * 如果调用了futue.get()方法，会造成主线程阻塞，直到线程池执行完成
	 * @param task
	 * @return
	 *//*
	public <T> Future<T> addTask(Callable<T> task) {
		return pool.submit(task);
	}
	*//**
	 * 将任务添加到线程池执行
	 * 如果调用了futue.get()方法，会造成主线程阻塞，直到线程池执行完成
	 * @param task
	 * @return
	 *//*
	public void addTask(Runnable task) {
		pool.execute(task);
	}

	*//**
	 * 获取执行器
	 * @return
	 *//*
	public ExecutorService getPool(){
		return pool;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

//		int a = 1;
//		for(int i=0;i<100;i++){
//			SingleThreadPool.getThreadPool().addTask(new Runnable() {
//				@Override
//				public void run() {
//					System.out.println(Thread.currentThread());
//					try {
//						Thread.sleep(10000);
//						System.out.println(new Date());
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					//count.decrementAndGet();
//				}
//			});
//		}
		System.out.println(System.currentTimeMillis());
	}
	*/
}
