package jp.dip.th075altlobby.imo.Data.IPConverter;

import java.nio.ByteBuffer;

public class IPConverter {
    public static Integer toInt(byte[] b) {
        if (b.length != 4)
            throw new NumberFormatException("バイト列の長さは4バイトである必要があります。");
        ByteBuffer bb = ByteBuffer.wrap(b);
        return bb.getInt();
    }

    public static int[] toIntArray(int ip) {
        byte[] b = new byte[4];
        ByteBuffer bb = ByteBuffer.wrap(b);
        bb.putInt(0, ip);
        bb.get(b);

        return toIntArray(b);
    }

    public static int[] toIntArray(byte[] ip) {
        int[] ret = new int[4];

        int geta = 0;
        for (int i = 0; i < 4; i++) {
            geta = ip[i];
            if (geta < 0)
                geta += 0x100;
            ret[i] = geta;
        }

        return ret;
    }

    public static byte[] toByteArray(String ip) {
        String[] splited = ip.split("\\.");
        byte[] ret = new byte[4];
        for (int i = 0; i < 4; i++)
            ret[i] = (byte) Short.parseShort(splited[i]);
        return ret;
    }

    public static String toString(int ip) {
        return toString(toIntArray(ip));
    }

    /**
     * <h1>toString</h1> <h2>渡されたバイト列の文字列表現を返します</h2>
     * <p>
     * 渡されたバイト列の文字列表現を返します。値が負の場合、0x100だけ加算されます。
     * </p>
     * 
     * @param b
     *            バイト列
     * @return 引数で渡したバイト列の文字列表現
     */
    public static String toString(byte[] b) {
        return toString(toIntArray(b));
    }

    public static String toString(int[] i) {
        return i[0] + "." + i[1] + "." + i[2] + "." + i[3];
    }
}
