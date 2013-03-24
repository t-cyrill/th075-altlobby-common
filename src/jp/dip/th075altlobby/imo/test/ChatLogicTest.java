package jp.dip.th075altlobby.imo.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatLogicTest {
	static class Server {
		public void run() throws IOException {
			System.out.println("クライアントの接続を待ちます。");
			ServerSocket sSock = new ServerSocket(7555, 256);
			Socket s = sSock.accept();
			Thread t1 = new Thread(new InputThread(s.getInputStream()));
			Thread t2 = new Thread(new OutputThread(s.getOutputStream()));
			t1.start();
			t2.start();
		}

		class OutputThread implements Runnable {
			private DataOutputStream out;
			private LinkedBlockingQueue<String> queue;
			public OutputThread(OutputStream out) {
				this.out = new DataOutputStream(new BufferedOutputStream(out));
				this.queue = new LinkedBlockingQueue<String>();
			}

			@Override
			public void run() {
				try {
					while(true) {
						String s = queue.take();
							out.writeUTF(s);
					}
				} catch (IOException e) {
					try {
						out.close();
					} catch (IOException e1) {
						// TODO 自動生成された catch ブロック
						e1.printStackTrace();
					}
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}

			public void putString(String s){
				queue.add(s);
			}
		}

		class InputThread implements Runnable {
			private DataInputStream in;
			public InputThread(InputStream in) {
				this.in = new DataInputStream(new BufferedInputStream(in));
			}
			@Override
			public void run() {
				try {
					while(true){
						String s = in.readUTF();
						System.out.println(s);
					}
				} catch (IOException e) {
					e.printStackTrace();
					try {
						in.close();
					} catch (IOException e1) { }
				}
			}
		}
	}

	static class Client {
		public void run(){
			System.out.println("サーバーへ接続します。");
			System.out.println("5秒後にメッセージを送信します。");
			try {
				Socket s = new Socket("localhost", 7555);
				final OutputThread outTh = new OutputThread(s.getOutputStream());
				Thread t1 = new Thread(new InputThread(s.getInputStream()));
				Thread t2 = new Thread(outTh);
				t1.start();
				t2.start();

				ScheduledExecutorService exs = Executors.newScheduledThreadPool(4);
				exs.scheduleWithFixedDelay(new Runnable(){
					@Override
					public void run() {
						outTh.putString("テスト");
					}
				}, 5, 5, TimeUnit.SECONDS);
			} catch (UnknownHostException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		class OutputThread implements Runnable {
			private DataOutputStream out;
			private LinkedBlockingQueue<String> queue;
			public OutputThread(OutputStream out) {
				this.out = new DataOutputStream(new BufferedOutputStream(out));
				this.queue = new LinkedBlockingQueue<String>();
			}

			@Override
			public void run() {
				try {
					while(true) {
						String s = queue.take();
						out.writeUTF(s);
						out.flush();
						System.out.println("文字列 " + s + "を送信しました");
					}
				} catch (IOException e) {
					try {
						out.close();
					} catch (IOException e1) {
						// TODO 自動生成された catch ブロック
						e1.printStackTrace();
					}
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}

			public void putString(String s) {
				queue.add(s);
			}
		}

		class InputThread implements Runnable {
			private DataInputStream in;
			public InputThread(InputStream in) {
				this.in = new DataInputStream(new BufferedInputStream(in));
			}
			@Override
			public void run() {
				try {
					while(true){
						String s = in.readUTF();
						System.out.println(s);
					}
				} catch (IOException e) {
					try {
						in.close();
					} catch (IOException e1) { }
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("0 サーバーモード 1 クライアントモード");
		if(System.in.read() == '0'){
			new Server().run();
		} else {
			new Client().run();
		}
	}
}
