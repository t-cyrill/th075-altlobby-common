package jp.dip.th075altlobby.imo.Data.communication;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * InputStreamから抽象的なデータの送信を行う機能を実装する。
 * WrappedDataを分解し、CommunicationDataInputが受信可能なデータを送る機能を提供します。
 * 
 * @author Cyrill
 */
public class CommunicationDataOutput {
    private final DataOutputStream out;

    /**
     * 出力ストリームを指定して、CommunicationDataOutputを構成する。
     * OutputStreamは内部でBufferedOutputStreamおよびDataOutputStreamによりラップされます。
     * send()は内部バッファリングを行います。
     * 
     * @param out
     *            出力の対象となるOutputStream
     */
    public CommunicationDataOutput(OutputStream out) {
        this.out = new DataOutputStream(new BufferedOutputStream(out));
    }

    /**
     * データを送信する
     * WrappedDataをCommunicationDataInputのrecieveメソッドが解析可能なデータに分解してOutputStreamに流します
     * 。 このメソッドはWrappedData.length, WrappedData.command, WrappedData.rawDataを
     * DataOutputStream.writeInt(), DataOutputStream.writeByte(),
     * DataOutputStream.write()によって送信します。
     * 
     * @param data
     *            送信されるデータ
     * @throws IOException
     *             通信中にSocketが閉じられたまたはリセットされた場合。その他の入出力エラーが発生した場合。
     */
    public void send(WrappedData data) throws IOException {
        out.writeInt(data.getLength());
        out.writeByte(data.getCommand());
        out.write(data.getRawData(), 0, data.getLength());
        out.flush();
    }

    /**
     * 内部で保持しているすべてのストリームを閉じます。
     * 
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public void close() throws IOException {
        this.out.close();
    }
}
