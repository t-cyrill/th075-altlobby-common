package jp.dip.th075altlobby.imo.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UTFDataFormatException;

import jp.dip.th075altlobby.imo.Data.communication.DataSplitter;
import jp.dip.th075altlobby.imo.Data.communication.DataWrapper;
import jp.dip.th075altlobby.imo.Data.communication.InstantMessage;
import jp.dip.th075altlobby.imo.Data.communication.UserInfo;
import jp.dip.th075altlobby.imo.Data.communication.VersionResponse;

import org.junit.Test;

public class DataSplitterTest {

    @Test
    public void testToInteger() {
        try {
            byte[] byteData = DataWrapper.wrapInteger(0);
            assertEquals(0, DataSplitter.toInteger(byteData).intValue());

            byteData = DataWrapper.wrapInteger(Integer.MAX_VALUE);
            assertEquals(Integer.MAX_VALUE, DataSplitter.toInteger(byteData)
                    .intValue());
        } catch (IOException e) {
            e.printStackTrace();
            fail("IO例外が発生しました。");
        }
    }

    @Test
    public void testToByte() {
        try {
            byte[] byteData = DataWrapper.wrapByte((byte) 0);
            assertEquals(0, DataSplitter.toByte(byteData).byteValue());

            byteData = DataWrapper.wrapByte(Byte.MAX_VALUE);
            assertEquals(Byte.MAX_VALUE, DataSplitter.toByte(byteData)
                    .byteValue());
        } catch (IOException e) {
            e.printStackTrace();
            fail("IO例外が発生しました。");
        }
    }

    @Test
    public void testToPostedInstantMessage() {
        try {
            String UID = "testUID";
            String Message = "testMessage";
            String MyUID = "dummy";
            byte color = 0;

            byte[] byteData = DataWrapper.wrapPostInstantMessage(UID, Message,
                    color);

            InstantMessage im = DataSplitter.toPostedInstantMessage(byteData,
                    MyUID);
            assertEquals(UID, im.getTo());
            assertEquals(MyUID, im.getFrom());
            assertEquals(Message, im.getMessage());
            assertEquals(color, im.getColor().byteValue());

            byteData = DataWrapper.wrapInteger(Integer.MAX_VALUE);
            assertEquals(Integer.MAX_VALUE, DataSplitter.toInteger(byteData)
                    .intValue());
        } catch (IOException e) {
            e.printStackTrace();
            fail("IO例外が発生しました。");
        }
    }

    @Test(expected = UTFDataFormatException.class)
    public void testToPostedInstantMessageException()
            throws UTFDataFormatException, IOException {
        StringBuffer toUID = new StringBuffer();
        for (int i = 0; i < 5000; i++) {
            toUID.append("abcdefghijklmn");
        }

        String message = "a";
        byte color = 0;
        DataWrapper.wrapPostInstantMessage(toUID.toString(), message, color);
    }

    @Test
    public void testToVersionResponse() {
        try {
            byte[] byteData = DataWrapper.wrapResponseVersion((short) 0,
                    (short) 0);
            VersionResponse res = DataSplitter.toVersionResponse(byteData);
            assertEquals(0, res.getProtocolVersion());
            assertEquals(0, res.getServerVersion());

            byteData = DataWrapper.wrapResponseVersion(Short.MAX_VALUE,
                    Short.MAX_VALUE);
            res = DataSplitter.toVersionResponse(byteData);
            assertEquals(Short.MAX_VALUE, res.getProtocolVersion());
            assertEquals(Short.MAX_VALUE, res.getServerVersion());
        } catch (IOException e) {
            e.printStackTrace();
            fail("IO例外が発生しました。");
        }
    }

    @Test
    public void testToUserInfo() {
        try {
            String publicName = "Name";
            String UID = "testUID";
            String publicMessage = "testMessage";
            String publicShortMessage = "testShortMessage";
            byte[] myIP = { (byte) 192, (byte) 168, 0, 1 };
            byte IMPolicy = 0;
            byte IPHide = 0;

            byte[] byteData = DataWrapper.wrapNewConnect(publicName,
                    publicMessage, publicShortMessage, (short) 7555, IMPolicy,
                    IPHide);
            UserInfo res = DataSplitter.toUserInfo(byteData, myIP, UID);
            assertEquals(publicName, res.getPublicName());
            assertEquals(UID, res.getUID());
            assertEquals(publicMessage, res.getPublicMessage());
            assertEquals(publicShortMessage, res.getPublicShortMessage());
            assertEquals(myIP[0], res.getIpAddress()[0]);
            assertEquals(myIP[1], res.getIpAddress()[1]);
            assertEquals(myIP[2], res.getIpAddress()[2]);
            assertEquals(myIP[3], res.getIpAddress()[3]);
            assertEquals(IMPolicy, res.getIMPolicy().byteValue());

        } catch (IOException e) {
            e.printStackTrace();
            fail("IO例外が発生しました。");
        }
    }

    @Test
    public void testToNewConnectedUserInfo() {
        try {
            String publicName = "Name";
            String UID = "testUID";
            String publicMessage = "testMessage";
            String publicShortMessage = "testShortMessage";
            byte[] myIP = { (byte) 192, (byte) 168, 0, 1 };
            byte IMPolicy = 0;
            byte IPHide = 0;
            byte state = 0;

            UserInfo info = new UserInfo(publicName, UID, myIP, (short) 7500,
                    state, IMPolicy, IPHide, publicMessage, publicShortMessage);
            byte[] byteData = DataWrapper.wrapNewConnected(info);
            UserInfo res = DataSplitter.toNewConnectedUserInfo(byteData);
            assertEquals(publicName, res.getPublicName());
            assertEquals(UID, res.getUID());
            assertEquals(publicMessage, res.getPublicMessage());
            assertEquals(publicShortMessage, res.getPublicShortMessage());
            assertEquals(myIP[0], res.getIpAddress()[0]);
            assertEquals(myIP[1], res.getIpAddress()[1]);
            assertEquals(myIP[2], res.getIpAddress()[2]);
            assertEquals(myIP[3], res.getIpAddress()[3]);
            assertEquals(state, res.getState().byteValue());
            assertEquals(IMPolicy, res.getIMPolicy().byteValue());

        } catch (IOException e) {
            e.printStackTrace();
            fail("IO例外が発生しました。");
        }
    }

}
