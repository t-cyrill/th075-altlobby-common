package jp.dip.th075altlobby.imo.Data.communication;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>AbstractInputThread</h1> <h2>入力スレッドのインターフェイス</h2>
 * <p>
 * InputStream監視スレッド用のRunnableです。
 * </p>
 * <p>
 * 具体的な処理内容はrunメソッドを実装し記述します。
 * </p>
 * 
 * @author Cyrill
 * 
 */
public abstract class AbstractInputThread implements Runnable {
    private final Socket _socket;
    private final CommunicationDataInput _in;
    private final AbstractOutputThread _out;
    private final AtomicBoolean _running;
    protected final BlockingDeque<WrappedData> queue;
    private final static Logger logger = Logger
            .getLogger("jp.dip.th075altlobby.imo.Application");

    public AbstractInputThread(Socket s, InputStream in,
            AbstractOutputThread out) {
        _socket = s;
        _in = new RefinedCommunicationDataInput(in);
        _out = out;
        _running = new AtomicBoolean(true);
        queue = new LinkedBlockingDeque<WrappedData>();
        ExecutorService exs = Executors.newCachedThreadPool();
        exs.execute(new RecieveThread());
        exs.shutdown();
    }

    protected Socket getSocket() {
        return _socket;
    }

    protected CommunicationDataInput getDataIn() {
        return _in;
    }

    protected AbstractOutputThread getOutputThread() {
        return _out;
    }

    /**
     * 入力スレッドを安全な形で停止する.
     * 
     * 入力スレッドに終了用ダミーデータを割り込ませ、入力スレッドを安全な形で停止させます。
     */
    public void interruptExit() {
        try {
            WrappedData dummy = new WrappedData(WrappedData.EXIT_DUMMY,
                    DataWrapper.wrapByte((byte) 0));
            queue.putFirst(dummy);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "interruptExitでIO例外をキャッチしました。", e);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "interruptExitでInterruptされました。", e);
        }

        _running.set(false);
    }

    protected abstract void callBye();

    class RecieveThread implements Runnable {
        public void run() {
            try {
                while (_running.get()) {
                    WrappedData data = getDataIn().recieve();
                    queue.put(data);
                    if (data.getCommand() == WrappedData.BYE) {
                        logger.info("終了シグナルを受けとりました。ループを抜けます。");
                        break;
                    }
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "RecieveThreadでIO例外をキャッチしました。", e);
                callBye();
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "RecieveThreadがInterruptされました。", e);
                callBye();
            }

            logger.info("RecieveThreadを終了します。");
        }
    }
}
