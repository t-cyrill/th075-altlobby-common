package jp.dip.th075altlobby.imo.Data.communication;

import java.io.ByteArrayInputStream;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class DataSplitter {
    /**
     * byte配列をIntegerとみなして変換します。
     * 
     * @param b
     *            対象となるbyte配列
     * @return 変換されたInteger値
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static Integer toInteger(byte[] b) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        Integer i = dis.readInt();
        dis.close();
        return i;
    }

    /**
     * <h2>byte配列をByteとみなして変換します。</h2>
     * <p>
     * byte配列をByteとみなして変換します。
     * </p>
     * <p>
     * このメソッドは内部でDataInputStream.readByteを呼び出しbyte配列をByteに変換します。
     * </p>
     * 
     * @param b
     *            対象となるbyte配列
     * @return 変換されたByte値
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static Byte toByte(byte[] b) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        Byte bb = dis.readByte();
        dis.close();
        return bb;
    }

    /**
     * <h1>toPostedInstantMessage</h1> <h2>
     * PostInstantMessageで送られたデータとみなしてInstantMessageに変換する</h2>
     * <p>
     * byte配列をPostInstantMessageで送られたデータとみなしてInstantMessageに変換します。
     * </p>
     * <p>
     * このメソッドは、DataInputStream.readUTF, DataInputStream.readByteを呼び出し、 送信先UID,
     * 送信するメッセージ, IMの色を読み取ります。
     * </p>
     * <p>
     * 送信元UIDはmyUIDに、時間はSystem.currentTimeMillisにセットされます。
     * </p>
     * 
     * @param b
     *            対象となるbyte配列
     * @param myUID
     *            自分のUID
     * @return InstantMessageのインスタンス
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static InstantMessage toPostedInstantMessage(byte[] b, String myUID)
            throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        String toUID = dis.readUTF();
        String message = dis.readUTF();
        Byte color = dis.readByte();

        dis.close();

        return new InstantMessage(myUID, toUID, message,
                System.currentTimeMillis(), color);
    }

    /**
     * byte配列をwrapResponseVersionでラップされたデータとみなして変換する。
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @return 変換された結果のVersionResponseのインスタンス
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static VersionResponse toVersionResponse(byte[] b)
            throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        Short protocol_ver = dis.readShort();
        Short server_ver = dis.readShort();
        dis.close();

        return new VersionResponse(protocol_ver, server_ver);
    }

    /**
     * <h1>byte配列をwrapNewConnectでラップされたデータとみなして変換する</h1>
     * <p>
     * byte配列には、IPアドレスとUIDに関する情報は含まれていないためパラメータを与えてインスタンスを生成します。
     * </p>
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @param myIP
     *            IPアドレスのInteger表現
     * @param myUID
     *            ユニークID
     * @return ユーザーの情報が格納されたUserInfo
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static UserInfo toUserInfo(byte[] b, byte[] myIP, String myUID)
            throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        String publicName = dis.readUTF();
        String publicMessage = dis.readUTF();
        String publicShortMessage = dis.readUTF();
        Short port = dis.readShort();
        Byte IMPolicy = dis.readByte();
        Byte IPHideFlag = dis.readByte();
        Byte state = 0;
        dis.close();

        return new UserInfo(publicName, myUID, myIP, port, state, IMPolicy,
                IPHideFlag, publicMessage, publicShortMessage);
    }

    /**
     * <h1>toNewConnectedUserInfo</h1> <h2>
     * byte配列をwrapNewConnectedでラップされたデータとみなして変換する</h2>
     * <p>
     * byte配列をwrapNewConnectedでラップされたデータとみなして変換します。
     * </p>
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @return 接続されたユーザーの情報
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static UserInfo toNewConnectedUserInfo(byte[] b) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        byte[] ipAddress = new byte[4];
        String publicName = dis.readUTF();
        String UID = dis.readUTF();
        dis.read(ipAddress);
        Short port = dis.readShort();
        Byte IMPolicy = dis.readByte();
        Byte IPHideFlag = dis.readByte();
        String publicMessage = dis.readUTF();
        String publicShortMessage = dis.readUTF();
        Byte state = 0;

        dis.close();

        return new UserInfo(publicName, UID, ipAddress, port, state, IMPolicy,
                IPHideFlag, publicMessage, publicShortMessage);
    }

    /**
     * <h1>toConnectedUsersInfo</h1> <h2>
     * byte配列をwrapResponseUserlistでラップされたデータとみなして変換する</h2>
     * <p>
     * byte配列をwrapResponseUserlistでラップされたデータとみなして変換します。
     * </p>
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @return 接続されたユーザーの情報配列
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static UserInfo[] toConnectedUsersInfo(byte[] b) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        Integer counter = dis.readInt();

        LinkedList<UserInfo> connectedUsersInfo = new LinkedList<UserInfo>();
        for (int i = 0; i < counter; i++) {
            String publicName = dis.readUTF();
            String UID = dis.readUTF();
            byte[] ipAddress = new byte[4];
            int readSize = dis.read(ipAddress, 0, 4);

            if (readSize != 4)
                System.out.println("ERROR SIZE");

            Short port = dis.readShort();
            Byte state = dis.readByte();
            Byte IMPolicy = dis.readByte();
            Byte IPHideFlag = dis.readByte();
            String publicMessage = dis.readUTF();
            String publicShortMessage = dis.readUTF();

            connectedUsersInfo.add(new UserInfo(publicName, UID, ipAddress,
                    port, state, IMPolicy, IPHideFlag, publicMessage,
                    publicShortMessage));
        }

        return connectedUsersInfo.toArray(new UserInfo[connectedUsersInfo
                .size()]);
    }

    /**
     * <h1>toUserState</h1> <h2>
     * byte配列をDataWrapper.wrapModifyUserStateでラップされたデータとみなして変換する</h2>
     * <p>
     * byte配列をDataWrapper.wrapModifyUserStateでラップされたデータとみなして変換します。
     * </p>
     * <p>
     * このメソッドは汎用メソッドtoByteのエイリアスです。
     * </p>
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @return 接続されたユーザーの情報配列
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static Byte toUserState(byte[] b) throws IOException {
        return toByte(b);
    }

    /**
     * <h1>toStateModifiedInfo</h1> <h2>
     * byte配列をDataWrapper.wrapStateModifiedInfoでラップされたデータとみなして変換する</h2>
     * <p>
     * byte配列をDataWrapper.wrapStateModifiedInfoでラップされたデータとみなして変換します。
     * </p>
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @return 更新されたユーザーの情報と新しいステート
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static StateModifiedInfo toStateModifiedInfo(byte[] b)
            throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        String UID = dis.readUTF();
        Byte state = dis.readByte();

        dis.close();

        return new StateModifiedInfo(UID, state);
    }

    /**
     * <h1>toDistributedIM</h1> <h2>
     * byte配列をDataWrapper.wrapDistributedIMでラップされたデータとみなして変換する</h2>
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @return 配信されたインスタントメッセージ
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static InstantMessage toDistributedIM(byte[] b) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        String from = dis.readUTF();
        String to = dis.readUTF();
        String message = dis.readUTF();
        Long time = dis.readLong();
        Byte color = dis.readByte();

        dis.close();

        return new InstantMessage(from, to, message, time, color);
    }

    /**
     * <h1>toResponseStoredInstantMessages</h1> <h2>
     * byte配列をDataWrapper.wrapResponseStoredInstantMessagesでラップされたデータとみなして変換する</h2>
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @return サーバーに保存されていたインスタントメッセージ
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static ResponseStoredInstantMessages toResponseStoredInstantMessages(
            byte[] b) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        byte responseCode = dis.readByte();
        byte size = dis.readByte();

        ArrayDeque<InstantMessage> ims = new ArrayDeque<InstantMessage>();

        if (size > 0) {
            // データが存在する場合imsに格納していく
            for (int i = 0; i < size; i++) {
                String from = dis.readUTF();
                String to = dis.readUTF();
                String message = dis.readUTF();
                Long time = dis.readLong();
                Byte color = dis.readByte();
                ims.add(new InstantMessage(from, to, message, time, color));
            }
        }
        dis.close();

        return new ResponseStoredInstantMessages(responseCode, ims);
    }

    /**
     * <h1>toPostedClientToClientCommand</h1> <h2>
     * byte配列をクライアントから送信されたクライアント間コマンドとみなして変換する</h2>
     * <p>
     * byte配列をクライアントから送信されたクライアント間コマンドとみなして変換します。
     * </p>
     * <p>
     * このメソッドはDataWrapper.wrapPostClientToClientCommandによりラップされたデータを対象とします。
     * DataInputStream
     * .readUTFを用いてtoUIDを、DataInputStream.readByteを用いてcommandを読み取ります。
     * </p>
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @return クライアント間のコマンド
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static ClientToClientCommand toPostedClientToClientCommand(byte[] b,
            String fromUID) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        String toUID = dis.readUTF();
        Byte command = dis.readByte();

        dis.close();

        return new ClientToClientCommand(fromUID, toUID, command);
    }

    /**
     * <h1>toDistributedClientToClientCommand</h1> <h2>
     * byte配列をサーバーから転送されたクライアント間コマンドとみなして変換する</h2>
     * <p>
     * byte配列をサーバーから転送されたクライアント間コマンドとみなして変換します。
     * </p>
     * <p>
     * このメソッドはDataWrapper.wrapPostClientToClientCommandによりラップされたデータを対象とします。
     * DataInputStream
     * .readUTFを用いてfromUIDとtoUIDを、DataInputStream.readByteを用いてcommandを読み取ります。
     * </p>
     * 
     * @param b
     *            変換の対象となるbyte配列
     * @return クライアント間のコマンド
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static ClientToClientCommand toDistributedClientToClientCommand(
            byte[] b) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        String fromUID = dis.readUTF();
        String toUID = dis.readUTF();
        Byte command = dis.readByte();

        dis.close();

        return new ClientToClientCommand(fromUID, toUID, command);
    }

    /**
     * <h1>toString</h1> <h2>byte配列を{@link DataWrapper#wrapString(String)}
     * でwrapされたデータとみなして変換する</h2>
     * <p>
     * byte配列を{@link DataWrapper#wrapString(String)}でwrapされたデータとみなして変換します。
     * </p>
     * 
     * @param rawData
     *            変換の対象となるbyte配列
     * @return 文字列
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static String toString(byte[] rawData) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
                rawData));
        String str = dis.readUTF();
        dis.close();

        return str;
    }
}
