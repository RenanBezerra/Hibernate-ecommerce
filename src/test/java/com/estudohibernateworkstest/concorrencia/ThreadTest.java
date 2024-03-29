package com.estudohibernateworkstest.concorrencia;

import org.junit.Test;

public class ThreadTest {

	private static void log(Object obj, Object... args) {
		System.out.println(
				String.format("[LOG "+ System.currentTimeMillis()+ "] "+ obj, args)
				);
		
	}
	
	private static void esperar(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void entenderThreads() {
		log("Encerrando metodo de teste0");
		Runnable runnable1 = () -> {
			log("Runnable 01 vai esperar 3 segundos. ");
			esperar(3);
			log("Runnable 01 concluido.");
		};
		
		Runnable runnable2 = () -> {
			log("Runnable 02 vai esperar 1 segundo.");
			esperar(1);
			log("Runnable 02 concluido");
			
		};
		
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		log("Encerrando metodo de teste");
	}
}
