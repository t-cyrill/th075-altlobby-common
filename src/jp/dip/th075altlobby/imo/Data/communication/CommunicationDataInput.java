package jp.dip.th075altlobby.imo.Data.communication;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <h1>CommunicationDataInput</h1> <h2>InputStreamから抽象的なデータの読み取りを行う機能を実装する</h2>
 * <p>
 * CommunicationDataOutputから送られてくるデータを読み取り、WrappedDataとして返します。
 * </p>
 * 
 * @author Cyrill
 */
public class CommunicationDataInput {
    private final DataInputStream in;

    /**
     * 入力ストリームを指定して、CommunicationDataInputを構成する。
     * InputStreamは内部でBufferedInputStreamおよびDataInputStreamによりラップされます。
     * recieve()は内部バッファリングを行います。
     * 
     * @param in
     *            入力の対象となるInputStream
     */
    public CommunicationDataInput(InputStream in) {
        this.in = new DataInputStream(new BufferedInputStream(in));
    }

    /**
     * <h1>recieve</h1> <h2>データの到着を待つ</h2>
     * <p>
     * CommunicationDataOutputのsendメソッドにより送信されたデータの到着を待ちます。
     * </p>
     * <p>
     * このメソッドはまず4byteを読み込み、実データのサイズを決定します。 次に1byteを読み込み、コマンドとして格納します。
     * 最後に、実データサイズ分だけデータの読み取りを試みます。
     * </p>
     * <p>
     * データが到達するまで、このメソッドはブロックされます。
     * </p>
     * 
     * @return 到着したデータ
     * @throws IOException
     *             通信中にSocketが閉じられたまたはリセットされた場合。その他の入出力エラーが発生した場合。
     */
    public WrappedData recieve() throws IOException {
        Integer length;
        Byte command;
        byte[] rawData;

        length = in.readInt();
        command = in.readByte();

        rawData = new byte[length];

        in.readFully(rawData);

        WrappedData recieved = new WrappedData(length, command, rawData);
        return recieved;
    }

    /**
     * 入力ストリームを取得する
     * 
     * @return
     */
    protected DataInputStream getIn() {
        return in;
    }

    /**
     * このインスタンスが保持しているすべてのストリームを閉じる
     * 
     * @throws IOException
     *             入出力例外が発生した場合
     * 
     */
    public void close() throws IOException {
        this.in.close();
    }
}
