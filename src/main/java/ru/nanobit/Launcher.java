package ru.nanobit;

import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Launcher {
    final static Logger logger = LoggerFactory.getLogger(Launcher.class);
    
    public static void main(String[] args) {
        DefaultSmppClient client = new DefaultSmppClient();

        SmppSessionConfiguration sessionConfig = new SmppSessionConfiguration();

        sessionConfig.setType(SmppBindType.TRANSCEIVER);
        sessionConfig.setHost("10.61.40.91");
        sessionConfig.setPort(2000);
        sessionConfig.setSystemId("10100");
        sessionConfig.setPassword("12345678");

        try {
            SmppSession session = client.bind(sessionConfig);
            SubmitSm sm = createSubmitSm("000031", "79282120058", "Yyyhaaaaa", "UCS-2");
            System.out.println("Try to send message");
            session.submit(sm, TimeUnit.SECONDS.toMillis(60));
            System.out.println("Message sent");
            System.out.println("Wait 10 seconds");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Destroy session");
            session.close();
            session.destroy();
            System.out.println("Destroy client");
            client.destroy();
            System.out.println("Bye!");
        } catch (SmppTimeoutException ex) {
            logger.debug(ex.getMessage());
        } catch (SmppChannelException ex) {
            logger.debug(ex.getMessage());
        } catch (SmppBindException ex) {
            logger.debug(ex.getMessage());
        } catch (UnrecoverablePduException ex) {
            logger.debug(ex.getMessage());
        } catch (InterruptedException ex) {
            logger.debug(ex.getMessage());
        } catch (RecoverablePduException ex) {
            logger.debug(ex.getMessage());
        }
    }

    public static SubmitSm createSubmitSm(String src, String dst, String text, String charset) throws SmppInvalidArgumentException {
        SubmitSm sm = new SubmitSm();
        // For alpha numeric will use
        // TON=5
        // NPI=0
        sm.setSourceAddress(new Address((byte) 0, (byte) 1, src));
        // For national numbers will use
        // TON=1
        // NPI=1
        sm.setDestAddress(new Address((byte) 1, (byte) 1, dst));
        // Set datacoding to UCS-2
        sm.setDataCoding((byte) 8);
        // Encode text
        sm.setShortMessage(CharsetUtil.encode(text, charset));
        return sm;
    }
}
